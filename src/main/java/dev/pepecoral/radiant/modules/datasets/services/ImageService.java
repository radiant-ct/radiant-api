package dev.pepecoral.radiant.modules.datasets.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import dev.pepecoral.radiant.modules.common.exceptions.ResourceNotFoundException;
import dev.pepecoral.radiant.modules.datasets.dtos.ImageFilter;
import dev.pepecoral.radiant.modules.datasets.entities.Dataset;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import dev.pepecoral.radiant.modules.datasets.exceptions.DatasetSetInImageCreationException;
import dev.pepecoral.radiant.modules.datasets.repositories.ImageRepository;
import dev.pepecoral.radiant.modules.datasets.specifications.ImageSpecification;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class ImageService {

    private static final String[] RANGE_LIMIT_FIELDS = {
            "ageYearsMin", "ageYearsMax",
            "studyDateMin", "studyDateMax",
            "kvpMin", "kvpMax",
            "exposureMasMin", "exposureMasMax",
            "sliceThicknessMmMin", "sliceThicknessMmMax",
            "pixelSpacingMmMin", "pixelSpacingMmMax",
            "rowsMin", "rowsMax",
            "columnsMin", "columnsMax",
            "nSlicesMin", "nSlicesMax",
            "shapeMeshVolumeMin", "shapeMeshVolumeMax",
            "shapeVoxelVolumeMin", "shapeVoxelVolumeMax",
            "shapeSurfaceAreaMin", "shapeSurfaceAreaMax",
            "shapeSphericityMin", "shapeSphericityMax",
            "shapeCompactness1Min", "shapeCompactness1Max",
            "shapeCompactness2Min", "shapeCompactness2Max",
            "shapeMaximum3dDiameterMin", "shapeMaximum3dDiameterMax",
            "shapeMajorAxisLengthMin", "shapeMajorAxisLengthMax",
            "shapeMinorAxisLengthMin", "shapeMinorAxisLengthMax",
            "shapeLeastAxisLengthMin", "shapeLeastAxisLengthMax",
            "shapeElongationMin", "shapeElongationMax",
            "shapeFlatnessMin", "shapeFlatnessMax",
            "firstEnergyMin", "firstEnergyMax",
            "firstTotalEnergyMin", "firstTotalEnergyMax",
            "firstEntropyMin", "firstEntropyMax",
            "firstMinimumMin", "firstMinimumMax",
            "first10thPercentileMin", "first10thPercentileMax",
            "first90thPercentileMin", "first90thPercentileMax",
            "firstMaximumMin", "firstMaximumMax",
            "firstMeanMin", "firstMeanMax",
            "firstMedianMin", "firstMedianMax",
            "firstInterquartileRangeMin", "firstInterquartileRangeMax",
            "firstRangeMin", "firstRangeMax",
            "firstMeanAbsoluteDeviationMin", "firstMeanAbsoluteDeviationMax",
            "firstRobustMeanAbsoluteDeviationMin", "firstRobustMeanAbsoluteDeviationMax",
            "firstRootMeanSquaredMin", "firstRootMeanSquaredMax",
            "firstSkewnessMin", "firstSkewnessMax",
            "firstKurtosisMin", "firstKurtosisMax",
            "firstVarianceMin", "firstVarianceMax",
            "firstUniformityMin", "firstUniformityMax",
            "glcmAutocorrelationMin", "glcmAutocorrelationMax",
            "glcmClusterProminenceMin", "glcmClusterProminenceMax",
            "glcmClusterShadeMin", "glcmClusterShadeMax",
            "glcmClusterTendencyMin", "glcmClusterTendencyMax",
            "glcmContrastMin", "glcmContrastMax",
            "glcmCorrelationMin", "glcmCorrelationMax",
            "glcmDifferenceAverageMin", "glcmDifferenceAverageMax",
            "glcmDifferenceEntropyMin", "glcmDifferenceEntropyMax",
            "glcmDifferenceVarianceMin", "glcmDifferenceVarianceMax",
            "glcmIdMin", "glcmIdMax",
            "glcmIdmMin", "glcmIdmMax",
            "glcmIdmnMin", "glcmIdmnMax",
            "glcmIdnMin", "glcmIdnMax",
            "glcmImc1Min", "glcmImc1Max",
            "glcmImc2Min", "glcmImc2Max",
            "glcmInverseVarianceMin", "glcmInverseVarianceMax",
            "glcmJointAverageMin", "glcmJointAverageMax",
            "glcmJointEnergyMin", "glcmJointEnergyMax",
            "glcmJointEntropyMin", "glcmJointEntropyMax",
            "glcmMaxProbabilitiesMin", "glcmMaxProbabilitiesMax",
            "glcmSumAverageMin", "glcmSumAverageMax",
            "glcmSumEntropyMin", "glcmSumEntropyMax",
            "glcmSumSquaresMin", "glcmSumSquaresMax",
            "glrlmGrayLevelNonUniformityMin", "glrlmGrayLevelNonUniformityMax",
            "glrlmGrayLevelNonUniformityNormalizedMin", "glrlmGrayLevelNonUniformityNormalizedMax",
            "glrlmGrayLevelVarianceMin", "glrlmGrayLevelVarianceMax",
            "glrlmHighGrayLevelRunEmphasisMin", "glrlmHighGrayLevelRunEmphasisMax",
            "glrlmLongRunEmphasisMin", "glrlmLongRunEmphasisMax",
            "glrlmLongRunHighGrayLevelEmphasisMin", "glrlmLongRunHighGrayLevelEmphasisMax",
            "glrlmLongRunLowGrayLevelEmphasisMin", "glrlmLongRunLowGrayLevelEmphasisMax",
            "glrlmLowGrayLevelRunEmphasisMin", "glrlmLowGrayLevelRunEmphasisMax",
            "glrlmRunEntropyMin", "glrlmRunEntropyMax",
            "glrlmRunLengthNonUniformityMin", "glrlmRunLengthNonUniformityMax",
            "glrlmRunLengthNonUniformityNormalizedMin", "glrlmRunLengthNonUniformityNormalizedMax",
            "glrlmRunPercentageMin", "glrlmRunPercentageMax",
            "glrlmRunVarianceMin", "glrlmRunVarianceMax",
            "glrlmShortRunEmphasisMin", "glrlmShortRunEmphasisMax",
            "glrlmShortRunHighGrayLevelEmphasisMin", "glrlmShortRunHighGrayLevelEmphasisMax",
            "glrlmShortRunLowGrayLevelEmphasisMin", "glrlmShortRunLowGrayLevelEmphasisMax",
            "glszmGrayLevelNonUniformityMin", "glszmGrayLevelNonUniformityMax",
            "glszmGrayLevelNonUniformityNormalizedMin", "glszmGrayLevelNonUniformityNormalizedMax",
            "glszmGrayLevelVarianceMin", "glszmGrayLevelVarianceMax",
            "glszmHighGrayLevelZoneEmphasisMin", "glszmHighGrayLevelZoneEmphasisMax",
            "glszmLargeAreaEmphasisMin", "glszmLargeAreaEmphasisMax",
            "glszmLargeAreaHighGrayLevelEmphasisMin", "glszmLargeAreaHighGrayLevelEmphasisMax",
            "glszmLargeAreaLowGrayLevelEmphasisMin", "glszmLargeAreaLowGrayLevelEmphasisMax",
            "glszmLowGrayLevelZoneEmphasisMin", "glszmLowGrayLevelZoneEmphasisMax",
            "glszmSizeZoneNonUniformityMin", "glszmSizeZoneNonUniformityMax",
            "glszmSizeZoneNonUniformityNormalizedMin", "glszmSizeZoneNonUniformityNormalizedMax",
            "glszmSmallAreaEmphasisMin", "glszmSmallAreaEmphasisMax",
            "glszmSmallAreaHighGrayLevelEmphasisMin", "glszmSmallAreaHighGrayLevelEmphasisMax",
            "glszmSmallAreaLowGrayLevelEmphasisMin", "glszmSmallAreaLowGrayLevelEmphasisMax",
            "glszmZoneEntropyMin", "glszmZoneEntropyMax",
            "glszmZonePercentageMin", "glszmZonePercentageMax",
            "glszmZoneVarianceMin", "glszmZoneVarianceMax",
            "ngtdmBusynessMin", "ngtdmBusynessMax",
            "ngtdmCoarsenessMin", "ngtdmCoarsenessMax",
            "ngtdmComplexityMin", "ngtdmComplexityMax",
            "ngtdmContrastMin", "ngtdmContrastMax",
            "ngtdmStrengthMin", "ngtdmStrengthMax",
            "gldmDependenceEntropyMin", "gldmDependenceEntropyMax",
            "gldmDependenceNonUniformityMin", "gldmDependenceNonUniformityMax",
            "gldmDependenceNonUniformityNormalizedMin", "gldmDependenceNonUniformityNormalizedMax",
            "gldmDependenceVarianceMin", "gldmDependenceVarianceMax",
            "gldmGrayLevelNonUniformityMin", "gldmGrayLevelNonUniformityMax",
            "gldmGrayLevelVarianceMin", "gldmGrayLevelVarianceMax",
            "gldmHighGrayLevelEmphasisMin", "gldmHighGrayLevelEmphasisMax",
            "gldmLargeDependenceEmphasisMin", "gldmLargeDependenceEmphasisMax",
            "gldmLargeDependenceHighGrayLevelEmphasisMin", "gldmLargeDependenceHighGrayLevelEmphasisMax",
            "gldmLargeDependenceLowGrayLevelEmphasisMin", "gldmLargeDependenceLowGrayLevelEmphasisMax",
            "gldmLowGrayLevelEmphasisMin", "gldmLowGrayLevelEmphasisMax",
            "gldmSmallDependenceEmphasisMin", "gldmSmallDependenceEmphasisMax",
            "gldmSmallDependenceHighGrayLevelEmphasisMin", "gldmSmallDependenceHighGrayLevelEmphasisMax",
            "gldmSmallDependenceLowGrayLevelEmphasisMin", "gldmSmallDependenceLowGrayLevelEmphasisMax",
    };

    private final ImageRepository imageRepository;
    private final DatasetService datasetService;

    public Image create(@Valid Image image, @NotNull Dataset dataset) {

        if (image.getDataset() != null) {
            throw new DatasetSetInImageCreationException();
        }

        Dataset gotDataset = datasetService.findById(dataset.getId());
        image.setDataset(gotDataset);
        return imageRepository.save(image);
    }

    public Image findById(@NotNull UUID imageId) {

        return imageRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<Image> findByDataset(@NotNull Dataset dataset) {

        Dataset foundDataset = datasetService.findById(dataset.getId());

        return imageRepository.findAllByDataset(foundDataset);
    }

    public List<Image> search(ImageFilter filter) {
        return imageRepository.findAll(ImageSpecification.from(filter));
    }

    public Map<String, Object> getRangeLimits() {
        List<Object[]> rows = imageRepository.getRangeLimitsRaw();
        if (rows.isEmpty()) return Map.of();
        Object[] raw = rows.get(0);
        Map<String, Object> limits = new LinkedHashMap<>();
        for (int i = 0; i < RANGE_LIMIT_FIELDS.length; i++) {
            limits.put(RANGE_LIMIT_FIELDS[i], raw[i]);
        }
        return limits;
    }

    public Map<String, List<String>> getDistinctValues() {
        Map<String, List<String>> distinct = new LinkedHashMap<>();
        distinct.put("sex", imageRepository.findDistinctSex());
        distinct.put("convolutionKernel", imageRepository.findDistinctConvolutionKernel());
        distinct.put("manufacturer", imageRepository.findDistinctManufacturer());
        distinct.put("manufacturerModel", imageRepository.findDistinctManufacturerModel());
        distinct.put("softwareVersion", imageRepository.findDistinctSoftwareVersion());
        return distinct;
    }
}
