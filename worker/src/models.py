from __future__ import annotations

from dataclasses import dataclass, field
from datetime import date


@dataclass
class DicomMetadata:
    sex: str | None
    age_years: int | None
    study_date: date | None
    kvp: float | None
    exposure_mas: float | None
    convolution_kernel: str | None
    slice_thickness_mm: float | None
    pixel_spacing_mm: float | None
    manufacturer: str | None
    manufacturer_model: str | None
    software_version: str | None
    rows: int | None
    columns: int | None
    nslices: int | None
    has_segmentation: bool = False


@dataclass
class RadiomicsFeatures:
    # Shape
    shape_mesh_volume: float | None = None
    shape_voxel_volume: float | None = None
    shape_surface_area: float | None = None
    shape_sphericity: float | None = None
    shape_compactness1: float | None = None
    shape_compactness2: float | None = None
    shape_maximum_3d_diameter: float | None = None
    shape_major_axis_length: float | None = None
    shape_minor_axis_length: float | None = None
    shape_least_axis_length: float | None = None
    shape_elongation: float | None = None
    shape_flatness: float | None = None

    # First order
    first_energy: float | None = None
    first_total_energy: float | None = None
    first_entropy: float | None = None
    first_minimum: float | None = None
    first_10th_percentile: float | None = None
    first_90th_percentile: float | None = None
    first_maximum: float | None = None
    first_mean: float | None = None
    first_median: float | None = None
    first_interquartile_range: float | None = None
    first_range: float | None = None
    first_mean_absolute_deviation: float | None = None
    first_robust_mean_absolute_deviation: float | None = None
    first_root_mean_squared: float | None = None
    first_skewness: float | None = None
    first_kurtosis: float | None = None
    first_variance: float | None = None
    first_uniformity: float | None = None

    # GLCM
    glcm_autocorrelation: float | None = None
    glcm_cluster_prominence: float | None = None
    glcm_cluster_shade: float | None = None
    glcm_cluster_tendency: float | None = None
    glcm_contrast: float | None = None
    glcm_correlation: float | None = None
    glcm_difference_average: float | None = None
    glcm_difference_entropy: float | None = None
    glcm_difference_variance: float | None = None
    glcm_id: float | None = None
    glcm_idm: float | None = None
    glcm_idmn: float | None = None
    glcm_idn: float | None = None
    glcm_imc1: float | None = None
    glcm_imc2: float | None = None
    glcm_inverse_variance: float | None = None
    glcm_joint_average: float | None = None
    glcm_joint_energy: float | None = None
    glcm_joint_entropy: float | None = None
    glcm_max_probabilities: float | None = None
    glcm_sum_average: float | None = None
    glcm_sum_entropy: float | None = None
    glcm_sum_squares: float | None = None

    # GLRLM
    glrlm_gray_level_non_uniformity: float | None = None
    glrlm_gray_level_non_uniformity_normalized: float | None = None
    glrlm_gray_level_variance: float | None = None
    glrlm_high_gray_level_run_emphasis: float | None = None
    glrlm_long_run_emphasis: float | None = None
    glrlm_long_run_high_gray_level_emphasis: float | None = None
    glrlm_long_run_low_gray_level_emphasis: float | None = None
    glrlm_low_gray_level_run_emphasis: float | None = None
    glrlm_run_entropy: float | None = None
    glrlm_run_length_non_uniformity: float | None = None
    glrlm_run_length_non_uniformity_normalized: float | None = None
    glrlm_run_percentage: float | None = None
    glrlm_run_variance: float | None = None
    glrlm_short_run_emphasis: float | None = None
    glrlm_short_run_high_gray_level_emphasis: float | None = None
    glrlm_short_run_low_gray_level_emphasis: float | None = None

    # GLSZM
    glszm_gray_level_non_uniformity: float | None = None
    glszm_gray_level_non_uniformity_normalized: float | None = None
    glszm_gray_level_variance: float | None = None
    glszm_high_gray_level_zone_emphasis: float | None = None
    glszm_large_area_emphasis: float | None = None
    glszm_large_area_high_gray_level_emphasis: float | None = None
    glszm_large_area_low_gray_level_emphasis: float | None = None
    glszm_low_gray_level_zone_emphasis: float | None = None
    glszm_size_zone_non_uniformity: float | None = None
    glszm_size_zone_non_uniformity_normalized: float | None = None
    glszm_small_area_emphasis: float | None = None
    glszm_small_area_high_gray_level_emphasis: float | None = None
    glszm_small_area_low_gray_level_emphasis: float | None = None
    glszm_zone_entropy: float | None = None
    glszm_zone_percentage: float | None = None
    glszm_zone_variance: float | None = None

    # NGTDM
    ngtdm_busyness: float | None = None
    ngtdm_coarseness: float | None = None
    ngtdm_complexity: float | None = None
    ngtdm_contrast: float | None = None
    ngtdm_strength: float | None = None

    # GLDM
    gldm_dependence_entropy: float | None = None
    gldm_dependence_non_uniformity: float | None = None
    gldm_dependence_non_uniformity_normalized: float | None = None
    gldm_dependence_variance: float | None = None
    gldm_gray_level_non_uniformity: float | None = None
    gldm_gray_level_variance: float | None = None
    gldm_high_gray_level_emphasis: float | None = None
    gldm_large_dependence_emphasis: float | None = None
    gldm_large_dependence_high_gray_level_emphasis: float | None = None
    gldm_large_dependence_low_gray_level_emphasis: float | None = None
    gldm_low_gray_level_emphasis: float | None = None
    gldm_small_dependence_emphasis: float | None = None
    gldm_small_dependence_high_gray_level_emphasis: float | None = None
    gldm_small_dependence_low_gray_level_emphasis: float | None = None


@dataclass
class CaseResult:
    case_id: str
    case_path: str
    metadata: DicomMetadata
    radiomics: RadiomicsFeatures | None   # None when no SEG present
    radiomics_error: str | None = None
    image_id: str | None = None
    archived_path: str | None = None


@dataclass
class DatasetResult:
    dataset_id: str
    cases: list[CaseResult] = field(default_factory=list)
    failed_cases: list[tuple[str, str]] = field(default_factory=list)

    @property
    def n_total(self) -> int:
        return len(self.cases) + len(self.failed_cases)

    @property
    def n_with_radiomics(self) -> int:
        return sum(1 for c in self.cases if c.radiomics is not None)

    @property
    def n_skipped_radiomics(self) -> int:
        return sum(1 for c in self.cases if c.radiomics is None and c.radiomics_error is None)