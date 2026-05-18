package dev.pepecoral.radiant.modules.datasets.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record ImageFilter(
        // Identity
        UUID datasetId,

        // Categorical
        String sex,
        String convolutionKernel,
        String manufacturer,
        String manufacturerModel,
        String softwareVersion,

        // Boolean
        Boolean hasSegmentation,

        // Numeric ranges
        Integer ageYearsMin, Integer ageYearsMax,
        LocalDate studyDateMin, LocalDate studyDateMax,
        Double kvpMin, Double kvpMax,
        Double exposureMasMin, Double exposureMasMax,
        Double sliceThicknessMmMin, Double sliceThicknessMmMax,
        Double pixelSpacingMmMin, Double pixelSpacingMmMax,
        Integer rowsMin, Integer rowsMax,
        Integer columnsMin, Integer columnsMax,
        Integer nSlicesMin, Integer nSlicesMax,

        // Shape
        Double shapeMeshVolumeMin, Double shapeMeshVolumeMax,
        Double shapeVoxelVolumeMin, Double shapeVoxelVolumeMax,
        Double shapeSurfaceAreaMin, Double shapeSurfaceAreaMax,
        Double shapeSphericityMin, Double shapeSphericityMax,
        Double shapeCompactness1Min, Double shapeCompactness1Max,
        Double shapeCompactness2Min, Double shapeCompactness2Max,
        Double shapeMaximum3dDiameterMin, Double shapeMaximum3dDiameterMax,
        Double shapeMajorAxisLengthMin, Double shapeMajorAxisLengthMax,
        Double shapeMinorAxisLengthMin, Double shapeMinorAxisLengthMax,
        Double shapeLeastAxisLengthMin, Double shapeLeastAxisLengthMax,
        Double shapeElongationMin, Double shapeElongationMax,
        Double shapeFlatnessMin, Double shapeFlatnessMax,

        // First order
        Double firstEnergyMin, Double firstEnergyMax,
        Double firstTotalEnergyMin, Double firstTotalEnergyMax,
        Double firstEntropyMin, Double firstEntropyMax,
        Double firstMinimumMin, Double firstMinimumMax,
        Double first10thPercentileMin, Double first10thPercentileMax,
        Double first90thPercentileMin, Double first90thPercentileMax,
        Double firstMaximumMin, Double firstMaximumMax,
        Double firstMeanMin, Double firstMeanMax,
        Double firstMedianMin, Double firstMedianMax,
        Double firstInterquartileRangeMin, Double firstInterquartileRangeMax,
        Double firstRangeMin, Double firstRangeMax,
        Double firstMeanAbsoluteDeviationMin, Double firstMeanAbsoluteDeviationMax,
        Double firstRobustMeanAbsoluteDeviationMin, Double firstRobustMeanAbsoluteDeviationMax,
        Double firstRootMeanSquaredMin, Double firstRootMeanSquaredMax,
        Double firstSkewnessMin, Double firstSkewnessMax,
        Double firstKurtosisMin, Double firstKurtosisMax,
        Double firstVarianceMin, Double firstVarianceMax,
        Double firstUniformityMin, Double firstUniformityMax,

        // GLCM
        Double glcmAutocorrelationMin, Double glcmAutocorrelationMax,
        Double glcmClusterProminenceMin, Double glcmClusterProminenceMax,
        Double glcmClusterShadeMin, Double glcmClusterShadeMax,
        Double glcmClusterTendencyMin, Double glcmClusterTendencyMax,
        Double glcmContrastMin, Double glcmContrastMax,
        Double glcmCorrelationMin, Double glcmCorrelationMax,
        Double glcmDifferenceAverageMin, Double glcmDifferenceAverageMax,
        Double glcmDifferenceEntropyMin, Double glcmDifferenceEntropyMax,
        Double glcmDifferenceVarianceMin, Double glcmDifferenceVarianceMax,
        Double glcmIdMin, Double glcmIdMax,
        Double glcmIdmMin, Double glcmIdmMax,
        Double glcmIdmnMin, Double glcmIdmnMax,
        Double glcmIdnMin, Double glcmIdnMax,
        Double glcmImc1Min, Double glcmImc1Max,
        Double glcmImc2Min, Double glcmImc2Max,
        Double glcmInverseVarianceMin, Double glcmInverseVarianceMax,
        Double glcmJointAverageMin, Double glcmJointAverageMax,
        Double glcmJointEnergyMin, Double glcmJointEnergyMax,
        Double glcmJointEntropyMin, Double glcmJointEntropyMax,
        Double glcmMaxProbabilitiesMin, Double glcmMaxProbabilitiesMax,
        Double glcmSumAverageMin, Double glcmSumAverageMax,
        Double glcmSumEntropyMin, Double glcmSumEntropyMax,
        Double glcmSumSquaresMin, Double glcmSumSquaresMax,

        // GLRLM
        Double glrlmGrayLevelNonUniformityMin, Double glrlmGrayLevelNonUniformityMax,
        Double glrlmGrayLevelNonUniformityNormalizedMin, Double glrlmGrayLevelNonUniformityNormalizedMax,
        Double glrlmGrayLevelVarianceMin, Double glrlmGrayLevelVarianceMax,
        Double glrlmHighGrayLevelRunEmphasisMin, Double glrlmHighGrayLevelRunEmphasisMax,
        Double glrlmLongRunEmphasisMin, Double glrlmLongRunEmphasisMax,
        Double glrlmLongRunHighGrayLevelEmphasisMin, Double glrlmLongRunHighGrayLevelEmphasisMax,
        Double glrlmLongRunLowGrayLevelEmphasisMin, Double glrlmLongRunLowGrayLevelEmphasisMax,
        Double glrlmLowGrayLevelRunEmphasisMin, Double glrlmLowGrayLevelRunEmphasisMax,
        Double glrlmRunEntropyMin, Double glrlmRunEntropyMax,
        Double glrlmRunLengthNonUniformityMin, Double glrlmRunLengthNonUniformityMax,
        Double glrlmRunLengthNonUniformityNormalizedMin, Double glrlmRunLengthNonUniformityNormalizedMax,
        Double glrlmRunPercentageMin, Double glrlmRunPercentageMax,
        Double glrlmRunVarianceMin, Double glrlmRunVarianceMax,
        Double glrlmShortRunEmphasisMin, Double glrlmShortRunEmphasisMax,
        Double glrlmShortRunHighGrayLevelEmphasisMin, Double glrlmShortRunHighGrayLevelEmphasisMax,
        Double glrlmShortRunLowGrayLevelEmphasisMin, Double glrlmShortRunLowGrayLevelEmphasisMax,

        // GLSZM
        Double glszmGrayLevelNonUniformityMin, Double glszmGrayLevelNonUniformityMax,
        Double glszmGrayLevelNonUniformityNormalizedMin, Double glszmGrayLevelNonUniformityNormalizedMax,
        Double glszmGrayLevelVarianceMin, Double glszmGrayLevelVarianceMax,
        Double glszmHighGrayLevelZoneEmphasisMin, Double glszmHighGrayLevelZoneEmphasisMax,
        Double glszmLargeAreaEmphasisMin, Double glszmLargeAreaEmphasisMax,
        Double glszmLargeAreaHighGrayLevelEmphasisMin, Double glszmLargeAreaHighGrayLevelEmphasisMax,
        Double glszmLargeAreaLowGrayLevelEmphasisMin, Double glszmLargeAreaLowGrayLevelEmphasisMax,
        Double glszmLowGrayLevelZoneEmphasisMin, Double glszmLowGrayLevelZoneEmphasisMax,
        Double glszmSizeZoneNonUniformityMin, Double glszmSizeZoneNonUniformityMax,
        Double glszmSizeZoneNonUniformityNormalizedMin, Double glszmSizeZoneNonUniformityNormalizedMax,
        Double glszmSmallAreaEmphasisMin, Double glszmSmallAreaEmphasisMax,
        Double glszmSmallAreaHighGrayLevelEmphasisMin, Double glszmSmallAreaHighGrayLevelEmphasisMax,
        Double glszmSmallAreaLowGrayLevelEmphasisMin, Double glszmSmallAreaLowGrayLevelEmphasisMax,
        Double glszmZoneEntropyMin, Double glszmZoneEntropyMax,
        Double glszmZonePercentageMin, Double glszmZonePercentageMax,
        Double glszmZoneVarianceMin, Double glszmZoneVarianceMax,

        // NGTDM
        Double ngtdmBusynessMin, Double ngtdmBusynessMax,
        Double ngtdmCoarsenessMin, Double ngtdmCoarsenessMax,
        Double ngtdmComplexityMin, Double ngtdmComplexityMax,
        Double ngtdmContrastMin, Double ngtdmContrastMax,
        Double ngtdmStrengthMin, Double ngtdmStrengthMax,

        // GLDM
        Double gldmDependenceEntropyMin, Double gldmDependenceEntropyMax,
        Double gldmDependenceNonUniformityMin, Double gldmDependenceNonUniformityMax,
        Double gldmDependenceNonUniformityNormalizedMin, Double gldmDependenceNonUniformityNormalizedMax,
        Double gldmDependenceVarianceMin, Double gldmDependenceVarianceMax,
        Double gldmGrayLevelNonUniformityMin, Double gldmGrayLevelNonUniformityMax,
        Double gldmGrayLevelVarianceMin, Double gldmGrayLevelVarianceMax,
        Double gldmHighGrayLevelEmphasisMin, Double gldmHighGrayLevelEmphasisMax,
        Double gldmLargeDependenceEmphasisMin, Double gldmLargeDependenceEmphasisMax,
        Double gldmLargeDependenceHighGrayLevelEmphasisMin, Double gldmLargeDependenceHighGrayLevelEmphasisMax,
        Double gldmLargeDependenceLowGrayLevelEmphasisMin, Double gldmLargeDependenceLowGrayLevelEmphasisMax,
        Double gldmLowGrayLevelEmphasisMin, Double gldmLowGrayLevelEmphasisMax,
        Double gldmSmallDependenceEmphasisMin, Double gldmSmallDependenceEmphasisMax,
        Double gldmSmallDependenceHighGrayLevelEmphasisMin, Double gldmSmallDependenceHighGrayLevelEmphasisMax,
        Double gldmSmallDependenceLowGrayLevelEmphasisMin, Double gldmSmallDependenceLowGrayLevelEmphasisMax) {
}