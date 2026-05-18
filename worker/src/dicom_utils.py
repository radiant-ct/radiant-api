from __future__ import annotations

import logging
from datetime import date
from pathlib import Path
from typing import Any

import pydicom
from pydicom.errors import InvalidDicomError

from models import DicomMetadata

log = logging.getLogger(__name__)


def _parse_date(value: str | None) -> date | None:
    if not value:
        return None
    try:
        return date(int(value[:4]), int(value[4:6]), int(value[6:8]))
    except Exception:
        return None


def _parse_age(value: str | None) -> int | None:
    """Convert DICOM age string (e.g. "065Y", "012M") to whole years."""
    if not value:
        return None
    try:
        unit = value[-1].upper()
        n = int(value[:-1])
        if unit == "Y":
            return n
        if unit == "M":
            return n // 12
        if unit == "W":
            return n // 52
        if unit == "D":
            return n // 365
    except Exception:
        pass
    return None


def _safe(ds: pydicom.Dataset, keyword: str, cast=None) -> Any:
    try:
        val = getattr(ds, keyword, None)
        if val is None:
            return None
        return cast(val) if cast else val
    except Exception:
        return None


def _find_ct_files(case_path: Path) -> list[Path]:
    ct_files = []
    for dcm in sorted(case_path.rglob("*.dcm")):
        try:
            ds = pydicom.dcmread(str(dcm), stop_before_pixels=True)
            if getattr(ds, "Modality", None) == "CT":
                ct_files.append(dcm)
        except (InvalidDicomError, Exception):
            continue
    return ct_files


def extract_metadata(case_path: Path, has_segmentation: bool) -> DicomMetadata:
    ct_files = _find_ct_files(case_path)

    if not ct_files:
        log.warning("No CT files found in %s — returning empty metadata", case_path)
        return DicomMetadata(
            sex=None, age_years=None, study_date=None, kvp=None,
            exposure_mas=None, convolution_kernel=None, slice_thickness_mm=None,
            pixel_spacing_mm=None, manufacturer=None, manufacturer_model=None,
            software_version=None, rows=None, columns=None,
            nslices=0, has_segmentation=has_segmentation,
        )

    ds = pydicom.dcmread(str(ct_files[0]), stop_before_pixels=True)

    pixel_spacing: float | None = None
    try:
        ps = ds.PixelSpacing
        pixel_spacing = float(ps[0])   # square pixels assumed
    except Exception:
        pass

    return DicomMetadata(
        sex=_safe(ds, "PatientSex"),
        age_years=_parse_age(_safe(ds, "PatientAge")),
        study_date=_parse_date(_safe(ds, "StudyDate")),
        kvp=_safe(ds, "KVP", float),
        exposure_mas=_safe(ds, "Exposure", float),   # tag (0018,1152) mAs
        convolution_kernel=_safe(ds, "ConvolutionKernel"),
        slice_thickness_mm=_safe(ds, "SliceThickness", float),
        pixel_spacing_mm=pixel_spacing,
        manufacturer=_safe(ds, "Manufacturer"),
        manufacturer_model=_safe(ds, "ManufacturerModelName"),
        software_version=_safe(ds, "SoftwareVersions"),
        rows=_safe(ds, "Rows", int),
        columns=_safe(ds, "Columns", int),
        nslices=len(ct_files),
        has_segmentation=has_segmentation,
    )