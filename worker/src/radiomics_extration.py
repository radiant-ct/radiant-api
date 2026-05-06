import os
import csv
import logging
import subprocess
import tempfile

import pydicom
import SimpleITK as sitk
import numpy as np
from radiomics import featureextractor

logging.getLogger("radiomics").setLevel(logging.ERROR)


def _is_seg(path: str) -> bool:
    try:
        return pydicom.dcmread(path, stop_before_pixels=True).Modality == "SEG"
    except Exception:
        return False


def _is_ct(path: str) -> bool:
    try:
        return pydicom.dcmread(path, stop_before_pixels=True).Modality == "CT"
    except Exception:
        return False


def _find_dicom_series(root: str) -> tuple[str, str]:
    subdirs = sorted([
        os.path.join(root, d) for d in os.listdir(root)
        if os.path.isdir(os.path.join(root, d))
    ])

    if len(subdirs) < 2:
        raise FileNotFoundError(f"Expected at least 2 subdirectories in: {root}")

    # CT has many slices, SEG has one
    subdirs_by_size = sorted(subdirs, key=lambda d: len(os.listdir(d)), reverse=True)
    ct_dir  = subdirs_by_size[0]
    seg_dir = subdirs_by_size[1]

    seg_files = [os.path.join(seg_dir, f) for f in os.listdir(seg_dir) if f.endswith(".dcm")]
    if not seg_files:
        raise FileNotFoundError(f"No DICOM files found in SEG folder: {seg_dir}")

    return ct_dir, seg_files[0]

def _convert_ct(ct_dir: str, out_path: str) -> bool:
    try:
        subprocess.run(
            ["plastimatch", "convert",
             "--input", ct_dir,
             "--output-img", out_path,
             "--output-type", "float"],
            check=True, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL,
        )
        return True
    except subprocess.CalledProcessError:
        return False

def _convert_seg(seg_file: str, ct_image: sitk.Image) -> sitk.Image | None:
    try:
        seg = sitk.ReadImage(seg_file)
        seg = sitk.Cast(seg, sitk.sitkUInt8)

        arr = sitk.GetArrayFromImage(seg)
        arr[arr > 0] = 1
        binary = sitk.GetImageFromArray(arr)

        # Keep the SEG's own spatial metadata — do NOT overwrite with CT geometry
        binary.SetOrigin(seg.GetOrigin())
        binary.SetDirection(seg.GetDirection())
        binary.SetSpacing(seg.GetSpacing())

        resampled = sitk.Resample(
            binary, ct_image, sitk.Transform(),
            sitk.sitkNearestNeighbor, 0, sitk.sitkUInt8,
        )

        if np.sum(sitk.GetArrayFromImage(resampled)) == 0:
            return None
        return resampled
    except Exception as e:
        raise RuntimeError(f"SEG conversion failed: {e}")


def extract_radiomic_features(case_dir: str, output_csv: str | None = None) -> dict:
    ct_dir, seg_file = _find_dicom_series(case_dir)

    if not ct_dir:
        raise FileNotFoundError(f"No CT series found under: {case_dir}")
    if not seg_file:
        raise FileNotFoundError(f"No SEG file found under: {case_dir}")

    with tempfile.TemporaryDirectory() as tmp:
        ct_nrrd  = os.path.join(tmp, "ct.nrrd")
        seg_nrrd = os.path.join(tmp, "seg.nrrd")

        if not _convert_ct(ct_dir, ct_nrrd):
            raise RuntimeError("plastimatch failed to convert CT series.")

        ct_image = sitk.ReadImage(ct_nrrd)
        seg_image = _convert_seg(seg_file, ct_image)

        if seg_image is None:
            raise ValueError("SEG mask is empty after resampling — cannot extract features.")

        sitk.WriteImage(seg_image, seg_nrrd)

        extractor = featureextractor.RadiomicsFeatureExtractor()
        raw = extractor.execute(ct_nrrd, seg_nrrd)

    features = {k: v for k, v in raw.items() if "diagnostics" not in k}

    if output_csv:
        with open(output_csv, "w", newline="") as f:
            writer = csv.writer(f)
            writer.writerow(["feature", "value"])
            writer.writerows(features.items())

    return features