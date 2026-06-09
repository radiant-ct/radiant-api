package dev.pepecoral.radiant.modules.datasets.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;

public interface ImageRepository extends JpaRepository<Image, UUID>, JpaSpecificationExecutor<Image> {

    List<Image> findAllByDataset(Dataset dataset);

    @Query("SELECT DISTINCT i.sex FROM Image i WHERE i.sex IS NOT NULL ORDER BY i.sex")
    List<String> findDistinctSex();

    @Query("SELECT DISTINCT i.convolutionKernel FROM Image i WHERE i.convolutionKernel IS NOT NULL ORDER BY i.convolutionKernel")
    List<String> findDistinctConvolutionKernel();

    @Query("SELECT DISTINCT i.manufacturer FROM Image i WHERE i.manufacturer IS NOT NULL ORDER BY i.manufacturer")
    List<String> findDistinctManufacturer();

    @Query("SELECT DISTINCT i.manufacturerModel FROM Image i WHERE i.manufacturerModel IS NOT NULL ORDER BY i.manufacturerModel")
    List<String> findDistinctManufacturerModel();

    @Query("SELECT DISTINCT i.softwareVersion FROM Image i WHERE i.softwareVersion IS NOT NULL ORDER BY i.softwareVersion")
    List<String> findDistinctSoftwareVersion();

    @Query(nativeQuery = true, value = """
            SELECT
                MIN(age_years) AS age_years_min, MAX(age_years) AS age_years_max,
                MIN(study_date) AS study_date_min, MAX(study_date) AS study_date_max,
                MIN(kvp) AS kvp_min, MAX(kvp) AS kvp_max,
                MIN(exposure_mas) AS exposure_mas_min, MAX(exposure_mas) AS exposure_mas_max,
                MIN(slice_thickness_mm) AS slice_thickness_mm_min, MAX(slice_thickness_mm) AS slice_thickness_mm_max,
                MIN(pixel_spacing_mm) AS pixel_spacing_mm_min, MAX(pixel_spacing_mm) AS pixel_spacing_mm_max,
                MIN(rows) AS rows_min, MAX(rows) AS rows_max,
                MIN(columns) AS columns_min, MAX(columns) AS columns_max,
                MIN(n_slices) AS n_slices_min, MAX(n_slices) AS n_slices_max,
                MIN(shape_mesh_volume) AS shape_mesh_volume_min, MAX(shape_mesh_volume) AS shape_mesh_volume_max,
                MIN(shape_voxel_volume) AS shape_voxel_volume_min, MAX(shape_voxel_volume) AS shape_voxel_volume_max,
                MIN(shape_surface_area) AS shape_surface_area_min, MAX(shape_surface_area) AS shape_surface_area_max,
                MIN(shape_sphericity) AS shape_sphericity_min, MAX(shape_sphericity) AS shape_sphericity_max,
                MIN(shape_compactness1) AS shape_compactness1_min, MAX(shape_compactness1) AS shape_compactness1_max,
                MIN(shape_compactness2) AS shape_compactness2_min, MAX(shape_compactness2) AS shape_compactness2_max,
                MIN(shape_maximum3d_diameter) AS shape_maximum3d_diameter_min, MAX(shape_maximum3d_diameter) AS shape_maximum3d_diameter_max,
                MIN(shape_major_axis_length) AS shape_major_axis_length_min, MAX(shape_major_axis_length) AS shape_major_axis_length_max,
                MIN(shape_minor_axis_length) AS shape_minor_axis_length_min, MAX(shape_minor_axis_length) AS shape_minor_axis_length_max,
                MIN(shape_least_axis_length) AS shape_least_axis_length_min, MAX(shape_least_axis_length) AS shape_least_axis_length_max,
                MIN(shape_elongation) AS shape_elongation_min, MAX(shape_elongation) AS shape_elongation_max,
                MIN(shape_flatness) AS shape_flatness_min, MAX(shape_flatness) AS shape_flatness_max,
                MIN(first_energy) AS first_energy_min, MAX(first_energy) AS first_energy_max,
                MIN(first_total_energy) AS first_total_energy_min, MAX(first_total_energy) AS first_total_energy_max,
                MIN(first_entropy) AS first_entropy_min, MAX(first_entropy) AS first_entropy_max,
                MIN(first_minimum) AS first_minimum_min, MAX(first_minimum) AS first_minimum_max,
                MIN(first10th_percentile) AS first10th_percentile_min, MAX(first10th_percentile) AS first10th_percentile_max,
                MIN(first90th_percentile) AS first90th_percentile_min, MAX(first90th_percentile) AS first90th_percentile_max,
                MIN(first_maximum) AS first_maximum_min, MAX(first_maximum) AS first_maximum_max,
                MIN(first_mean) AS first_mean_min, MAX(first_mean) AS first_mean_max,
                MIN(first_median) AS first_median_min, MAX(first_median) AS first_median_max,
                MIN(first_interquartile_range) AS first_interquartile_range_min, MAX(first_interquartile_range) AS first_interquartile_range_max,
                MIN(first_range) AS first_range_min, MAX(first_range) AS first_range_max,
                MIN(first_mean_absolute_deviation) AS first_mean_absolute_deviation_min, MAX(first_mean_absolute_deviation) AS first_mean_absolute_deviation_max,
                MIN(first_robust_mean_absolute_deviation) AS first_robust_mean_absolute_deviation_min, MAX(first_robust_mean_absolute_deviation) AS first_robust_mean_absolute_deviation_max,
                MIN(first_root_mean_squared) AS first_root_mean_squared_min, MAX(first_root_mean_squared) AS first_root_mean_squared_max,
                MIN(first_skewness) AS first_skewness_min, MAX(first_skewness) AS first_skewness_max,
                MIN(first_kurtosis) AS first_kurtosis_min, MAX(first_kurtosis) AS first_kurtosis_max,
                MIN(first_variance) AS first_variance_min, MAX(first_variance) AS first_variance_max,
                MIN(first_uniformity) AS first_uniformity_min, MAX(first_uniformity) AS first_uniformity_max,
                MIN(glcm_autocorrelation) AS glcm_autocorrelation_min, MAX(glcm_autocorrelation) AS glcm_autocorrelation_max,
                MIN(glcm_cluster_prominence) AS glcm_cluster_prominence_min, MAX(glcm_cluster_prominence) AS glcm_cluster_prominence_max,
                MIN(glcm_cluster_shade) AS glcm_cluster_shade_min, MAX(glcm_cluster_shade) AS glcm_cluster_shade_max,
                MIN(glcm_cluster_tendency) AS glcm_cluster_tendency_min, MAX(glcm_cluster_tendency) AS glcm_cluster_tendency_max,
                MIN(glcm_contrast) AS glcm_contrast_min, MAX(glcm_contrast) AS glcm_contrast_max,
                MIN(glcm_correlation) AS glcm_correlation_min, MAX(glcm_correlation) AS glcm_correlation_max,
                MIN(glcm_difference_average) AS glcm_difference_average_min, MAX(glcm_difference_average) AS glcm_difference_average_max,
                MIN(glcm_difference_entropy) AS glcm_difference_entropy_min, MAX(glcm_difference_entropy) AS glcm_difference_entropy_max,
                MIN(glcm_difference_variance) AS glcm_difference_variance_min, MAX(glcm_difference_variance) AS glcm_difference_variance_max,
                MIN(glcm_id) AS glcm_id_min, MAX(glcm_id) AS glcm_id_max,
                MIN(glcm_idm) AS glcm_idm_min, MAX(glcm_idm) AS glcm_idm_max,
                MIN(glcm_idmn) AS glcm_idmn_min, MAX(glcm_idmn) AS glcm_idmn_max,
                MIN(glcm_idn) AS glcm_idn_min, MAX(glcm_idn) AS glcm_idn_max,
                MIN(glcm_imc1) AS glcm_imc1_min, MAX(glcm_imc1) AS glcm_imc1_max,
                MIN(glcm_imc2) AS glcm_imc2_min, MAX(glcm_imc2) AS glcm_imc2_max,
                MIN(glcm_inverse_variance) AS glcm_inverse_variance_min, MAX(glcm_inverse_variance) AS glcm_inverse_variance_max,
                MIN(glcm_joint_average) AS glcm_joint_average_min, MAX(glcm_joint_average) AS glcm_joint_average_max,
                MIN(glcm_joint_energy) AS glcm_joint_energy_min, MAX(glcm_joint_energy) AS glcm_joint_energy_max,
                MIN(glcm_joint_entropy) AS glcm_joint_entropy_min, MAX(glcm_joint_entropy) AS glcm_joint_entropy_max,
                MIN(glcm_max_probabilities) AS glcm_max_probabilities_min, MAX(glcm_max_probabilities) AS glcm_max_probabilities_max,
                MIN(glcm_sum_average) AS glcm_sum_average_min, MAX(glcm_sum_average) AS glcm_sum_average_max,
                MIN(glcm_sum_entropy) AS glcm_sum_entropy_min, MAX(glcm_sum_entropy) AS glcm_sum_entropy_max,
                MIN(glcm_sum_squares) AS glcm_sum_squares_min, MAX(glcm_sum_squares) AS glcm_sum_squares_max,
                MIN(glrlm_gray_level_non_uniformity) AS glrlm_gray_level_non_uniformity_min, MAX(glrlm_gray_level_non_uniformity) AS glrlm_gray_level_non_uniformity_max,
                MIN(glrlm_gray_level_non_uniformity_normalized) AS glrlm_gray_level_non_uniformity_normalized_min, MAX(glrlm_gray_level_non_uniformity_normalized) AS glrlm_gray_level_non_uniformity_normalized_max,
                MIN(glrlm_gray_level_variance) AS glrlm_gray_level_variance_min, MAX(glrlm_gray_level_variance) AS glrlm_gray_level_variance_max,
                MIN(glrlm_high_gray_level_run_emphasis) AS glrlm_high_gray_level_run_emphasis_min, MAX(glrlm_high_gray_level_run_emphasis) AS glrlm_high_gray_level_run_emphasis_max,
                MIN(glrlm_long_run_emphasis) AS glrlm_long_run_emphasis_min, MAX(glrlm_long_run_emphasis) AS glrlm_long_run_emphasis_max,
                MIN(glrlm_long_run_high_gray_level_emphasis) AS glrlm_long_run_high_gray_level_emphasis_min, MAX(glrlm_long_run_high_gray_level_emphasis) AS glrlm_long_run_high_gray_level_emphasis_max,
                MIN(glrlm_long_run_low_gray_level_emphasis) AS glrlm_long_run_low_gray_level_emphasis_min, MAX(glrlm_long_run_low_gray_level_emphasis) AS glrlm_long_run_low_gray_level_emphasis_max,
                MIN(glrlm_low_gray_level_run_emphasis) AS glrlm_low_gray_level_run_emphasis_min, MAX(glrlm_low_gray_level_run_emphasis) AS glrlm_low_gray_level_run_emphasis_max,
                MIN(glrlm_run_entropy) AS glrlm_run_entropy_min, MAX(glrlm_run_entropy) AS glrlm_run_entropy_max,
                MIN(glrlm_run_length_non_uniformity) AS glrlm_run_length_non_uniformity_min, MAX(glrlm_run_length_non_uniformity) AS glrlm_run_length_non_uniformity_max,
                MIN(glrlm_run_length_non_uniformity_normalized) AS glrlm_run_length_non_uniformity_normalized_min, MAX(glrlm_run_length_non_uniformity_normalized) AS glrlm_run_length_non_uniformity_normalized_max,
                MIN(glrlm_run_percentage) AS glrlm_run_percentage_min, MAX(glrlm_run_percentage) AS glrlm_run_percentage_max,
                MIN(glrlm_run_variance) AS glrlm_run_variance_min, MAX(glrlm_run_variance) AS glrlm_run_variance_max,
                MIN(glrlm_short_run_emphasis) AS glrlm_short_run_emphasis_min, MAX(glrlm_short_run_emphasis) AS glrlm_short_run_emphasis_max,
                MIN(glrlm_short_run_high_gray_level_emphasis) AS glrlm_short_run_high_gray_level_emphasis_min, MAX(glrlm_short_run_high_gray_level_emphasis) AS glrlm_short_run_high_gray_level_emphasis_max,
                MIN(glrlm_short_run_low_gray_level_emphasis) AS glrlm_short_run_low_gray_level_emphasis_min, MAX(glrlm_short_run_low_gray_level_emphasis) AS glrlm_short_run_low_gray_level_emphasis_max,
                MIN(glszm_gray_level_non_uniformity) AS glszm_gray_level_non_uniformity_min, MAX(glszm_gray_level_non_uniformity) AS glszm_gray_level_non_uniformity_max,
                MIN(glszm_gray_level_non_uniformity_normalized) AS glszm_gray_level_non_uniformity_normalized_min, MAX(glszm_gray_level_non_uniformity_normalized) AS glszm_gray_level_non_uniformity_normalized_max,
                MIN(glszm_gray_level_variance) AS glszm_gray_level_variance_min, MAX(glszm_gray_level_variance) AS glszm_gray_level_variance_max,
                MIN(glszm_high_gray_level_zone_emphasis) AS glszm_high_gray_level_zone_emphasis_min, MAX(glszm_high_gray_level_zone_emphasis) AS glszm_high_gray_level_zone_emphasis_max,
                MIN(glszm_large_area_emphasis) AS glszm_large_area_emphasis_min, MAX(glszm_large_area_emphasis) AS glszm_large_area_emphasis_max,
                MIN(glszm_large_area_high_gray_level_emphasis) AS glszm_large_area_high_gray_level_emphasis_min, MAX(glszm_large_area_high_gray_level_emphasis) AS glszm_large_area_high_gray_level_emphasis_max,
                MIN(glszm_large_area_low_gray_level_emphasis) AS glszm_large_area_low_gray_level_emphasis_min, MAX(glszm_large_area_low_gray_level_emphasis) AS glszm_large_area_low_gray_level_emphasis_max,
                MIN(glszm_low_gray_level_zone_emphasis) AS glszm_low_gray_level_zone_emphasis_min, MAX(glszm_low_gray_level_zone_emphasis) AS glszm_low_gray_level_zone_emphasis_max,
                MIN(glszm_size_zone_non_uniformity) AS glszm_size_zone_non_uniformity_min, MAX(glszm_size_zone_non_uniformity) AS glszm_size_zone_non_uniformity_max,
                MIN(glszm_size_zone_non_uniformity_normalized) AS glszm_size_zone_non_uniformity_normalized_min, MAX(glszm_size_zone_non_uniformity_normalized) AS glszm_size_zone_non_uniformity_normalized_max,
                MIN(glszm_small_area_emphasis) AS glszm_small_area_emphasis_min, MAX(glszm_small_area_emphasis) AS glszm_small_area_emphasis_max,
                MIN(glszm_small_area_high_gray_level_emphasis) AS glszm_small_area_high_gray_level_emphasis_min, MAX(glszm_small_area_high_gray_level_emphasis) AS glszm_small_area_high_gray_level_emphasis_max,
                MIN(glszm_small_area_low_gray_level_emphasis) AS glszm_small_area_low_gray_level_emphasis_min, MAX(glszm_small_area_low_gray_level_emphasis) AS glszm_small_area_low_gray_level_emphasis_max,
                MIN(glszm_zone_entropy) AS glszm_zone_entropy_min, MAX(glszm_zone_entropy) AS glszm_zone_entropy_max,
                MIN(glszm_zone_percentage) AS glszm_zone_percentage_min, MAX(glszm_zone_percentage) AS glszm_zone_percentage_max,
                MIN(glszm_zone_variance) AS glszm_zone_variance_min, MAX(glszm_zone_variance) AS glszm_zone_variance_max,
                MIN(ngtdm_busyness) AS ngtdm_busyness_min, MAX(ngtdm_busyness) AS ngtdm_busyness_max,
                MIN(ngtdm_coarseness) AS ngtdm_coarseness_min, MAX(ngtdm_coarseness) AS ngtdm_coarseness_max,
                MIN(ngtdm_complexity) AS ngtdm_complexity_min, MAX(ngtdm_complexity) AS ngtdm_complexity_max,
                MIN(ngtdm_contrast) AS ngtdm_contrast_min, MAX(ngtdm_contrast) AS ngtdm_contrast_max,
                MIN(ngtdm_strength) AS ngtdm_strength_min, MAX(ngtdm_strength) AS ngtdm_strength_max,
                MIN(gldm_dependence_entropy) AS gldm_dependence_entropy_min, MAX(gldm_dependence_entropy) AS gldm_dependence_entropy_max,
                MIN(gldm_dependence_non_uniformity) AS gldm_dependence_non_uniformity_min, MAX(gldm_dependence_non_uniformity) AS gldm_dependence_non_uniformity_max,
                MIN(gldm_dependence_non_uniformity_normalized) AS gldm_dependence_non_uniformity_normalized_min, MAX(gldm_dependence_non_uniformity_normalized) AS gldm_dependence_non_uniformity_normalized_max,
                MIN(gldm_dependence_variance) AS gldm_dependence_variance_min, MAX(gldm_dependence_variance) AS gldm_dependence_variance_max,
                MIN(gldm_gray_level_non_uniformity) AS gldm_gray_level_non_uniformity_min, MAX(gldm_gray_level_non_uniformity) AS gldm_gray_level_non_uniformity_max,
                MIN(gldm_gray_level_variance) AS gldm_gray_level_variance_min, MAX(gldm_gray_level_variance) AS gldm_gray_level_variance_max,
                MIN(gldm_high_gray_level_emphasis) AS gldm_high_gray_level_emphasis_min, MAX(gldm_high_gray_level_emphasis) AS gldm_high_gray_level_emphasis_max,
                MIN(gldm_large_dependence_emphasis) AS gldm_large_dependence_emphasis_min, MAX(gldm_large_dependence_emphasis) AS gldm_large_dependence_emphasis_max,
                MIN(gldm_large_dependence_high_gray_level_emphasis) AS gldm_large_dependence_high_gray_level_emphasis_min, MAX(gldm_large_dependence_high_gray_level_emphasis) AS gldm_large_dependence_high_gray_level_emphasis_max,
                MIN(gldm_large_dependence_low_gray_level_emphasis) AS gldm_large_dependence_low_gray_level_emphasis_min, MAX(gldm_large_dependence_low_gray_level_emphasis) AS gldm_large_dependence_low_gray_level_emphasis_max,
                MIN(gldm_low_gray_level_emphasis) AS gldm_low_gray_level_emphasis_min, MAX(gldm_low_gray_level_emphasis) AS gldm_low_gray_level_emphasis_max,
                MIN(gldm_small_dependence_emphasis) AS gldm_small_dependence_emphasis_min, MAX(gldm_small_dependence_emphasis) AS gldm_small_dependence_emphasis_max,
                MIN(gldm_small_dependence_high_gray_level_emphasis) AS gldm_small_dependence_high_gray_level_emphasis_min, MAX(gldm_small_dependence_high_gray_level_emphasis) AS gldm_small_dependence_high_gray_level_emphasis_max,
                MIN(gldm_small_dependence_low_gray_level_emphasis) AS gldm_small_dependence_low_gray_level_emphasis_min, MAX(gldm_small_dependence_low_gray_level_emphasis) AS gldm_small_dependence_low_gray_level_emphasis_max
            FROM images
            """)
    List<Object[]> getRangeLimitsRaw();

}
