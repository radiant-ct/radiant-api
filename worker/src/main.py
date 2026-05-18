from __future__ import annotations

import json
import logging
import os
import shutil
import tarfile
import tempfile
from pathlib import Path

import redis

from dicom_utils import extract_metadata
from models import CaseResult
from poster import post_case
from radiomics_extration import extract_radiomics

logging.basicConfig(level=logging.INFO)
log = logging.getLogger(__name__)

# -----------------------------------------------------------------------------
# CONFIG
# -----------------------------------------------------------------------------

QUEUE_DIR = Path(os.getenv("QUEUE_DIR", "/queue"))
IMAGES_DIR = Path(os.getenv("IMAGES_DIR", "/images"))

REDIS_HOST = os.getenv("REDIS_HOST", "localhost")
REDIS_PORT = int(os.getenv("REDIS_PORT", 6379))
QUEUE_NAME = os.getenv("QUEUE_NAME", "queue")

r = redis.Redis(
    host=REDIS_HOST,
    port=REDIS_PORT,
    decode_responses=True,
)

# -----------------------------------------------------------------------------
# HELPERS
# -----------------------------------------------------------------------------


def extract_dataset_archive(dataset_id: str) -> Path:
    """
    Extract /queue/<dataset_id> tar.gz into temp folder.
    File has no extension but is tar.gz content.
    """
    archive_path = QUEUE_DIR / dataset_id

    if not archive_path.exists():
        raise FileNotFoundError(f"Dataset archive not found: {archive_path}")

    temp_dir = Path(tempfile.mkdtemp(prefix=f"dataset_{dataset_id}_"))

    log.info("Extracting %s -> %s", archive_path, temp_dir)

    with tarfile.open(archive_path, mode="r:gz") as tar:
        tar.extractall(temp_dir)

    return temp_dir


def find_case_dirs(root: Path) -> list[Path]:
    """
    Every first-level directory is considered a case.
    """
    return [p for p in root.iterdir() if p.is_dir()]


def archive_case(case_path: Path, image_id: str) -> Path:
    """
    Compress processed case into:
        /images/<image_id>.tar.gz
    """

    IMAGES_DIR.mkdir(parents=True, exist_ok=True)

    archive_path = IMAGES_DIR / f"{image_id}.tar.gz"

    # overwrite if exists
    if archive_path.exists():
        archive_path.unlink()

    log.info("Compressing %s -> %s", case_path, archive_path)

    with tarfile.open(archive_path, mode="w:gz") as tar:
        tar.add(case_path, arcname=case_path.name)

    log.info("Archived case -> %s", archive_path)

    return archive_path


# -----------------------------------------------------------------------------
# PROCESSING
# -----------------------------------------------------------------------------


def process_case(case_path: Path, dataset_id: str):
    case_id = case_path.name

    log.info("Processing case: %s", case_id)


    # detect segmentation


    has_segmentation = any(
        "seg" in p.name.lower()
        for p in case_path.rglob("*")
    )


    # metadata


    metadata = extract_metadata(
        case_path=case_path,
        has_segmentation=has_segmentation,
    )


    # radiomics


    radiomics = None
    radiomics_error = None

    if has_segmentation:
        try:
            radiomics = extract_radiomics(case_path)
        except Exception as e:
            log.exception("Radiomics failed for %s", case_id)
            radiomics_error = str(e)


    # create result object


    case = CaseResult(
        case_id=case_id,
        case_path=str(case_path),
        metadata=metadata,
        radiomics=radiomics,
        radiomics_error=radiomics_error,
    )


    image_id = post_case(case, dataset_id)

    case.image_id = image_id


    # archive


    archive_case(case_path, image_id)
    shutil.rmtree(case_path, ignore_errors=True)

    return case


def process_dataset(dataset_id: str):
    extracted_root = extract_dataset_archive(dataset_id)

    try:
        case_dirs = find_case_dirs(extracted_root)

        if not case_dirs:
            log.warning("No cases found for dataset %s", dataset_id)
            return

        for case_dir in case_dirs:
            try:
                process_case(case_dir, dataset_id)
            except Exception:
                log.exception("Failed processing case %s", case_dir.name)

    finally:
        shutil.rmtree(extracted_root, ignore_errors=True)

        # remove original archive after success
        archive_path = QUEUE_DIR / dataset_id

        if archive_path.exists():
            archive_path.unlink()


# -----------------------------------------------------------------------------
# WORKER LOOP
# -----------------------------------------------------------------------------


def main():
    log.info("Listening on Redis queue '%s'", QUEUE_NAME)

    while True:
        _, raw = r.brpop(QUEUE_NAME)

        try:
            job = json.loads(raw)

            log.info("Received job: %s", job)

            if job.get("task") != "register_dataset":
                log.warning("Unknown task: %s", job)
                continue

            dataset_id = job["dataset_id"]

            process_dataset(dataset_id)

            log.info("Dataset completed: %s", dataset_id)

        except Exception:
            log.exception("Worker failure while processing job")


if __name__ == "__main__":
    main()