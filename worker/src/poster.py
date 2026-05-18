from __future__ import annotations

import logging
import os
from dataclasses import asdict

import requests

from models import CaseResult, DicomMetadata, RadiomicsFeatures

log = logging.getLogger(__name__)

API_BASE = os.getenv("API_URL", "http://radiant-api:8080")
_SESSION = requests.Session()


def _to_payload(case: CaseResult) -> dict:
    """
    Build the ImageCreationDTO payload.
    Field names must match the OpenAPI spec exactly (camelCase).
    nslices is intentionally lowercase to match the spec anomaly.
    """
    m: DicomMetadata = case.metadata
    r: RadiomicsFeatures | None = case.radiomics

    payload: dict = {
        # DICOM metadata
        "sex":               m.sex,
        "ageYears":          m.age_years,
        "studyDate":         m.study_date.isoformat() if m.study_date else None,
        "kvp":               m.kvp,
        "exposureMas":       m.exposure_mas,
        "convolutionKernel": m.convolution_kernel,
        "sliceThicknessMm":  m.slice_thickness_mm,
        "pixelSpacingMm":    m.pixel_spacing_mm,
        "manufacturer":      m.manufacturer,
        "manufacturerModel": m.manufacturer_model,
        "softwareVersion":   m.software_version,
        "rows":              m.rows,
        "columns":           m.columns,
        "nslices":           m.nslices,
        "hasSegmentation":   m.has_segmentation,
    }

    if r is not None:
        payload.update({
            # Shape
            "shapeMeshVolume":        r.shape_mesh_volume,
            "shapeVoxelVolume":       r.shape_voxel_volume,
            "shapeSurfaceArea":       r.shape_surface_area,
            "shapeSphericity":        r.shape_sphericity,
            "shapeCompactness1":      r.shape_compactness1,
            "shapeCompactness2":      r.shape_compactness2,
            "shapeMaximum3dDiameter": r.shape_maximum_3d_diameter,
            "shapeMajorAxisLength":   r.shape_major_axis_length,
            "shapeMinorAxisLength":   r.shape_minor_axis_length,
            "shapeLeastAxisLength":   r.shape_least_axis_length,
            "shapeElongation":        r.shape_elongation,
            "shapeFlatness":          r.shape_flatness,
            # First order
            "firstEnergy":                      r.first_energy,
            "firstTotalEnergy":                 r.first_total_energy,
            "firstEntropy":                     r.first_entropy,
            "firstMinimum":                     r.first_minimum,
            "first10thPercentile":              r.first_10th_percentile,
            "first90thPercentile":              r.first_90th_percentile,
            "firstMaximum":                     r.first_maximum,
            "firstMean":                        r.first_mean,
            "firstMedian":                      r.first_median,
            "firstInterquartileRange":          r.first_interquartile_range,
            "firstRange":                       r.first_range,
            "firstMeanAbsoluteDeviation":       r.first_mean_absolute_deviation,
            "firstRobustMeanAbsoluteDeviation": r.first_robust_mean_absolute_deviation,
            "firstRootMeanSquared":             r.first_root_mean_squared,
            "firstSkewness":                    r.first_skewness,
            "firstKurtosis":                    r.first_kurtosis,
            "firstVariance":                    r.first_variance,
            "firstUniformity":                  r.first_uniformity,
            # GLCM
            "glcmAutocorrelation":    r.glcm_autocorrelation,
            "glcmClusterProminence":  r.glcm_cluster_prominence,
            "glcmClusterShade":       r.glcm_cluster_shade,
            "glcmClusterTendency":    r.glcm_cluster_tendency,
            "glcmContrast":           r.glcm_contrast,
            "glcmCorrelation":        r.glcm_correlation,
            "glcmDifferenceAverage":  r.glcm_difference_average,
            "glcmDifferenceEntropy":  r.glcm_difference_entropy,
            "glcmDifferenceVariance": r.glcm_difference_variance,
            "glcmId":                 r.glcm_id,
            "glcmIdm":                r.glcm_idm,
            "glcmIdmn":               r.glcm_idmn,
            "glcmIdn":                r.glcm_idn,
            "glcmImc1":               r.glcm_imc1,
            "glcmImc2":               r.glcm_imc2,
            "glcmInverseVariance":    r.glcm_inverse_variance,
            "glcmJointAverage":       r.glcm_joint_average,
            "glcmJointEnergy":        r.glcm_joint_energy,
            "glcmJointEntropy":       r.glcm_joint_entropy,
            "glcmMaxProbabilities":   r.glcm_max_probabilities,
            "glcmSumAverage":         r.glcm_sum_average,
            "glcmSumEntropy":         r.glcm_sum_entropy,
            "glcmSumSquares":         r.glcm_sum_squares,
            # GLRLM
            "glrlmGrayLevelNonUniformity":           r.glrlm_gray_level_non_uniformity,
            "glrlmGrayLevelNonUniformityNormalized": r.glrlm_gray_level_non_uniformity_normalized,
            "glrlmGrayLevelVariance":                r.glrlm_gray_level_variance,
            "glrlmHighGrayLevelRunEmphasis":         r.glrlm_high_gray_level_run_emphasis,
            "glrlmLongRunEmphasis":                  r.glrlm_long_run_emphasis,
            "glrlmLongRunHighGrayLevelEmphasis":     r.glrlm_long_run_high_gray_level_emphasis,
            "glrlmLongRunLowGrayLevelEmphasis":      r.glrlm_long_run_low_gray_level_emphasis,
            "glrlmLowGrayLevelRunEmphasis":          r.glrlm_low_gray_level_run_emphasis,
            "glrlmRunEntropy":                       r.glrlm_run_entropy,
            "glrlmRunLengthNonUniformity":           r.glrlm_run_length_non_uniformity,
            "glrlmRunLengthNonUniformityNormalized": r.glrlm_run_length_non_uniformity_normalized,
            "glrlmRunPercentage":                    r.glrlm_run_percentage,
            "glrlmRunVariance":                      r.glrlm_run_variance,
            "glrlmShortRunEmphasis":                 r.glrlm_short_run_emphasis,
            "glrlmShortRunHighGrayLevelEmphasis":    r.glrlm_short_run_high_gray_level_emphasis,
            "glrlmShortRunLowGrayLevelEmphasis":     r.glrlm_short_run_low_gray_level_emphasis,
            # GLSZM
            "glszmGrayLevelNonUniformity":           r.glszm_gray_level_non_uniformity,
            "glszmGrayLevelNonUniformityNormalized": r.glszm_gray_level_non_uniformity_normalized,
            "glszmGrayLevelVariance":                r.glszm_gray_level_variance,
            "glszmHighGrayLevelZoneEmphasis":        r.glszm_high_gray_level_zone_emphasis,
            "glszmLargeAreaEmphasis":                r.glszm_large_area_emphasis,
            "glszmLargeAreaHighGrayLevelEmphasis":   r.glszm_large_area_high_gray_level_emphasis,
            "glszmLargeAreaLowGrayLevelEmphasis":    r.glszm_large_area_low_gray_level_emphasis,
            "glszmLowGrayLevelZoneEmphasis":         r.glszm_low_gray_level_zone_emphasis,
            "glszmSizeZoneNonUniformity":            r.glszm_size_zone_non_uniformity,
            "glszmSizeZoneNonUniformityNormalized":  r.glszm_size_zone_non_uniformity_normalized,
            "glszmSmallAreaEmphasis":                r.glszm_small_area_emphasis,
            "glszmSmallAreaHighGrayLevelEmphasis":   r.glszm_small_area_high_gray_level_emphasis,
            "glszmSmallAreaLowGrayLevelEmphasis":    r.glszm_small_area_low_gray_level_emphasis,
            "glszmZoneEntropy":                      r.glszm_zone_entropy,
            "glszmZonePercentage":                   r.glszm_zone_percentage,
            "glszmZoneVariance":                     r.glszm_zone_variance,
            # NGTDM
            "ngtdmBusyness":   r.ngtdm_busyness,
            "ngtdmCoarseness": r.ngtdm_coarseness,
            "ngtdmComplexity": r.ngtdm_complexity,
            "ngtdmContrast":   r.ngtdm_contrast,
            "ngtdmStrength":   r.ngtdm_strength,
            # GLDM
            "gldmDependenceEntropy":                    r.gldm_dependence_entropy,
            "gldmDependenceNonUniformity":              r.gldm_dependence_non_uniformity,
            "gldmDependenceNonUniformityNormalized":    r.gldm_dependence_non_uniformity_normalized,
            "gldmDependenceVariance":                   r.gldm_dependence_variance,
            "gldmGrayLevelNonUniformity":               r.gldm_gray_level_non_uniformity,
            "gldmGrayLevelVariance":                    r.gldm_gray_level_variance,
            "gldmHighGrayLevelEmphasis":                r.gldm_high_gray_level_emphasis,
            "gldmLargeDependenceEmphasis":              r.gldm_large_dependence_emphasis,
            "gldmLargeDependenceHighGrayLevelEmphasis": r.gldm_large_dependence_high_gray_level_emphasis,
            "gldmLargeDependenceLowGrayLevelEmphasis":  r.gldm_large_dependence_low_gray_level_emphasis,
            "gldmLowGrayLevelEmphasis":                 r.gldm_low_gray_level_emphasis,
            "gldmSmallDependenceEmphasis":              r.gldm_small_dependence_emphasis,
            "gldmSmallDependenceHighGrayLevelEmphasis": r.gldm_small_dependence_high_gray_level_emphasis,
            "gldmSmallDependenceLowGrayLevelEmphasis":  r.gldm_small_dependence_low_gray_level_emphasis,
        })

    return payload


def post_case(case: CaseResult, dataset_id: str) -> str:
    """
    POST /datasets/{datasetId}/images
    Returns image UUID string from ImageResponseDTO.
    Raises requests.HTTPError on non-2xx response.
    """
    url = f"{API_BASE}/datasets/{dataset_id}/images"
    payload = _to_payload(case)

    response = _SESSION.post(url, json=payload, timeout=30)
    response.raise_for_status()

    image_id: str = response.json()["id"]
    log.info("POSTed case %s → image_id=%s", case.case_id, image_id)
    return image_id