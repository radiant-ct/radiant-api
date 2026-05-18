"""
radiomics_extraction.py
"""

from __future__ import annotations

import logging
import subprocess
import tempfile
from pathlib import Path

import numpy as np
import pydicom
import SimpleITK as sitk
from radiomics import featureextractor

from models import RadiomicsFeatures

logging.getLogger("radiomics").setLevel(logging.ERROR)
log = logging.getLogger(__name__)

# ---------------------------------------------------------------------------
# Pyradiomics key  →  RadiomicsFeatures field
# Keys are the raw strings returned by extractor.execute(), minus the
# "diagnostics_" prefix rows (those are filtered before this mapping).
# ---------------------------------------------------------------------------
_PYRADIOMICS_MAP: dict[str, str] = {
    # Shape
    "original_shape_MeshVolume":         "shape_mesh_volume",
    "original_shape_VoxelVolume":        "shape_voxel_volume",
    "original_shape_SurfaceArea":        "shape_surface_area",
    "original_shape_Sphericity":         "shape_sphericity",
    "original_shape_Compactness1":       "shape_compactness1",
    "original_shape_Compactness2":       "shape_compactness2",
    "original_shape_Maximum3DDiameter":  "shape_maximum_3d_diameter",
    "original_shape_MajorAxisLength":    "shape_major_axis_length",
    "original_shape_MinorAxisLength":    "shape_minor_axis_length",
    "original_shape_LeastAxisLength":    "shape_least_axis_length",
    "original_shape_Elongation":         "shape_elongation",
    "original_shape_Flatness":           "shape_flatness",
    # First order
    "original_firstorder_Energy":                     "first_energy",
    "original_firstorder_TotalEnergy":                "first_total_energy",
    "original_firstorder_Entropy":                    "first_entropy",
    "original_firstorder_Minimum":                    "first_minimum",
    "original_firstorder_10Percentile":               "first_10th_percentile",
    "original_firstorder_90Percentile":               "first_90th_percentile",
    "original_firstorder_Maximum":                    "first_maximum",
    "original_firstorder_Mean":                       "first_mean",
    "original_firstorder_Median":                     "first_median",
    "original_firstorder_InterquartileRange":         "first_interquartile_range",
    "original_firstorder_Range":                      "first_range",
    "original_firstorder_MeanAbsoluteDeviation":      "first_mean_absolute_deviation",
    "original_firstorder_RobustMeanAbsoluteDeviation":"first_robust_mean_absolute_deviation",
    "original_firstorder_RootMeanSquared":            "first_root_mean_squared",
    "original_firstorder_Skewness":                   "first_skewness",
    "original_firstorder_Kurtosis":                   "first_kurtosis",
    "original_firstorder_Variance":                   "first_variance",
    "original_firstorder_Uniformity":                 "first_uniformity",
    # GLCM
    "original_glcm_Autocorrelation":     "glcm_autocorrelation",
    "original_glcm_ClusterProminence":   "glcm_cluster_prominence",
    "original_glcm_ClusterShade":        "glcm_cluster_shade",
    "original_glcm_ClusterTendency":     "glcm_cluster_tendency",
    "original_glcm_Contrast":            "glcm_contrast",
    "original_glcm_Correlation":         "glcm_correlation",
    "original_glcm_DifferenceAverage":   "glcm_difference_average",
    "original_glcm_DifferenceEntropy":   "glcm_difference_entropy",
    "original_glcm_DifferenceVariance":  "glcm_difference_variance",
    "original_glcm_Id":                  "glcm_id",
    "original_glcm_Idm":                 "glcm_idm",
    "original_glcm_Idmn":                "glcm_idmn",
    "original_glcm_Idn":                 "glcm_idn",
    "original_glcm_Imc1":                "glcm_imc1",
    "original_glcm_Imc2":                "glcm_imc2",
    "original_glcm_InverseVariance":     "glcm_inverse_variance",
    "original_glcm_JointAverage":        "glcm_joint_average",
    "original_glcm_JointEnergy":         "glcm_joint_energy",
    "original_glcm_JointEntropy":        "glcm_joint_entropy",
    "original_glcm_MaximumProbability":  "glcm_max_probabilities",
    "original_glcm_SumAverage":          "glcm_sum_average",
    "original_glcm_SumEntropy":          "glcm_sum_entropy",
    "original_glcm_SumSquares":          "glcm_sum_squares",
    # GLRLM
    "original_glrlm_GrayLevelNonUniformity":           "glrlm_gray_level_non_uniformity",
    "original_glrlm_GrayLevelNonUniformityNormalized": "glrlm_gray_level_non_uniformity_normalized",
    "original_glrlm_GrayLevelVariance":                "glrlm_gray_level_variance",
    "original_glrlm_HighGrayLevelRunEmphasis":         "glrlm_high_gray_level_run_emphasis",
    "original_glrlm_LongRunEmphasis":                  "glrlm_long_run_emphasis",
    "original_glrlm_LongRunHighGrayLevelEmphasis":     "glrlm_long_run_high_gray_level_emphasis",
    "original_glrlm_LongRunLowGrayLevelEmphasis":      "glrlm_long_run_low_gray_level_emphasis",
    "original_glrlm_LowGrayLevelRunEmphasis":          "glrlm_low_gray_level_run_emphasis",
    "original_glrlm_RunEntropy":                       "glrlm_run_entropy",
    "original_glrlm_RunLengthNonUniformity":           "glrlm_run_length_non_uniformity",
    "original_glrlm_RunLengthNonUniformityNormalized": "glrlm_run_length_non_uniformity_normalized",
    "original_glrlm_RunPercentage":                    "glrlm_run_percentage",
    "original_glrlm_RunVariance":                      "glrlm_run_variance",
    "original_glrlm_ShortRunEmphasis":                 "glrlm_short_run_emphasis",
    "original_glrlm_ShortRunHighGrayLevelEmphasis":    "glrlm_short_run_high_gray_level_emphasis",
    "original_glrlm_ShortRunLowGrayLevelEmphasis":     "glrlm_short_run_low_gray_level_emphasis",
    # GLSZM
    "original_glszm_GrayLevelNonUniformity":           "glszm_gray_level_non_uniformity",
    "original_glszm_GrayLevelNonUniformityNormalized": "glszm_gray_level_non_uniformity_normalized",
    "original_glszm_GrayLevelVariance":                "glszm_gray_level_variance",
    "original_glszm_HighGrayLevelZoneEmphasis":        "glszm_high_gray_level_zone_emphasis",
    "original_glszm_LargeAreaEmphasis":                "glszm_large_area_emphasis",
    "original_glszm_LargeAreaHighGrayLevelEmphasis":   "glszm_large_area_high_gray_level_emphasis",
    "original_glszm_LargeAreaLowGrayLevelEmphasis":    "glszm_large_area_low_gray_level_emphasis",
    "original_glszm_LowGrayLevelZoneEmphasis":         "glszm_low_gray_level_zone_emphasis",
    "original_glszm_SizeZoneNonUniformity":            "glszm_size_zone_non_uniformity",
    "original_glszm_SizeZoneNonUniformityNormalized":  "glszm_size_zone_non_uniformity_normalized",
    "original_glszm_SmallAreaEmphasis":                "glszm_small_area_emphasis",
    "original_glszm_SmallAreaHighGrayLevelEmphasis":   "glszm_small_area_high_gray_level_emphasis",
    "original_glszm_SmallAreaLowGrayLevelEmphasis":    "glszm_small_area_low_gray_level_emphasis",
    "original_glszm_ZoneEntropy":                      "glszm_zone_entropy",
    "original_glszm_ZonePercentage":                   "glszm_zone_percentage",
    "original_glszm_ZoneVariance":                     "glszm_zone_variance",
    # NGTDM
    "original_ngtdm_Busyness":   "ngtdm_busyness",
    "original_ngtdm_Coarseness": "ngtdm_coarseness",
    "original_ngtdm_Complexity": "ngtdm_complexity",
    "original_ngtdm_Contrast":   "ngtdm_contrast",
    "original_ngtdm_Strength":   "ngtdm_strength",
    # GLDM
    "original_gldm_DependenceEntropy":                    "gldm_dependence_entropy",
    "original_gldm_DependenceNonUniformity":              "gldm_dependence_non_uniformity",
    "original_gldm_DependenceNonUniformityNormalized":    "gldm_dependence_non_uniformity_normalized",
    "original_gldm_DependenceVariance":                   "gldm_dependence_variance",
    "original_gldm_GrayLevelNonUniformity":               "gldm_gray_level_non_uniformity",
    "original_gldm_GrayLevelVariance":                    "gldm_gray_level_variance",
    "original_gldm_HighGrayLevelEmphasis":                "gldm_high_gray_level_emphasis",
    "original_gldm_LargeDependenceEmphasis":              "gldm_large_dependence_emphasis",
    "original_gldm_LargeDependenceHighGrayLevelEmphasis": "gldm_large_dependence_high_gray_level_emphasis",
    "original_gldm_LargeDependenceLowGrayLevelEmphasis":  "gldm_large_dependence_low_gray_level_emphasis",
    "original_gldm_LowGrayLevelEmphasis":                 "gldm_low_gray_level_emphasis",
    "original_gldm_SmallDependenceEmphasis":              "gldm_small_dependence_emphasis",
    "original_gldm_SmallDependenceHighGrayLevelEmphasis": "gldm_small_dependence_high_gray_level_emphasis",
    "original_gldm_SmallDependenceLowGrayLevelEmphasis":  "gldm_small_dependence_low_gray_level_emphasis",
}


def _find_seg_file(case_path: Path) -> Path | None:
    for dcm in case_path.rglob("*.dcm"):
        try:
            ds = pydicom.dcmread(str(dcm), stop_before_pixels=True)
            if getattr(ds, "Modality", None) == "SEG":
                return dcm
        except Exception:
            continue
    return None


def _convert_ct(ct_dir: Path, out_path: Path) -> None:
    subprocess.run(
        ["plastimatch", "convert",
         "--input", str(ct_dir),
         "--output-img", str(out_path),
         "--output-type", "float"],
        check=True,
        stdout=subprocess.DEVNULL,
        stderr=subprocess.DEVNULL,
    )


def _convert_seg(seg_file: Path, ct_image: sitk.Image) -> sitk.Image:
    seg = sitk.ReadImage(str(seg_file))
    seg = sitk.Cast(seg, sitk.sitkUInt8)
    arr = sitk.GetArrayFromImage(seg)
    arr[arr > 0] = 1
    binary = sitk.GetImageFromArray(arr)
    binary.SetOrigin(seg.GetOrigin())
    binary.SetDirection(seg.GetDirection())
    binary.SetSpacing(seg.GetSpacing())
    resampled = sitk.Resample(
        binary, ct_image, sitk.Transform(),
        sitk.sitkNearestNeighbor, 0, sitk.sitkUInt8,
    )
    if np.sum(sitk.GetArrayFromImage(resampled)) == 0:
        raise ValueError("SEG mask is empty after resampling onto CT grid")
    return resampled


def _find_ct_dir(case_path: Path) -> Path:
    """Return the subdir with the most .dcm files (the CT series)."""
    subdirs = [p for p in case_path.rglob("*") if p.is_dir()]
    if not subdirs:
        raise FileNotFoundError(f"No subdirectories under {case_path}")
    return max(subdirs, key=lambda d: len(list(d.glob("*.dcm"))))


def _map_features(raw: dict) -> RadiomicsFeatures:
    features = RadiomicsFeatures()
    unmapped = []
    for key, value in raw.items():
        if "diagnostics" in key:
            continue
        field = _PYRADIOMICS_MAP.get(key)
        if field:
            setattr(features, field, float(value))
        else:
            unmapped.append(key)
    if unmapped:
        log.debug("Unmapped pyradiomics keys: %s", unmapped)
    return features


def extract_radiomics(case_path: Path) -> RadiomicsFeatures | None:
    """
    Returns RadiomicsFeatures if a SEG is found, None otherwise.
    Raises RuntimeError on extraction failure (SEG present but broken).
    """
    seg_file = _find_seg_file(case_path)
    if seg_file is None:
        return None

    ct_dir = _find_ct_dir(case_path)

    with tempfile.TemporaryDirectory(prefix="radiomics_") as tmp:
        tmp = Path(tmp)
        ct_nrrd  = tmp / "ct.nrrd"
        seg_nrrd = tmp / "seg.nrrd"

        _convert_ct(ct_dir, ct_nrrd)
        ct_image = sitk.ReadImage(str(ct_nrrd))

        seg_image = _convert_seg(seg_file, ct_image)
        sitk.WriteImage(seg_image, str(seg_nrrd))

        extractor = featureextractor.RadiomicsFeatureExtractor()
        raw = extractor.execute(str(ct_nrrd), str(seg_nrrd))

    return _map_features(dict(raw))