package dev.pepecoral.radiant.modules.datasets.specifications;

import dev.pepecoral.radiant.modules.datasets.dtos.ImageFilter;
import dev.pepecoral.radiant.modules.datasets.entities.Image;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class ImageSpecification {

    private ImageSpecification() {
    }

    public static Specification<Image> from(ImageFilter f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Identity
            if (f.datasetId() != null) {
                predicates.add(cb.equal(root.get("dataset").get("id"), f.datasetId()));
            }

            // Categoricals
            addEqual(predicates, cb, root, "sex", f.sex());
            addEqual(predicates, cb, root, "convolutionKernel", f.convolutionKernel());
            addEqual(predicates, cb, root, "manufacturer", f.manufacturer());
            addEqual(predicates, cb, root, "manufacturerModel", f.manufacturerModel());
            addEqual(predicates, cb, root, "softwareVersion", f.softwareVersion());

            // Boolean
            if (f.hasSegmentation() != null) {
                predicates.add(cb.equal(root.get("hasSegmentation"), f.hasSegmentation()));
            }

            // DICOM numeric ranges
            addRange(predicates, cb, root, "ageYears", f.ageYearsMin(), f.ageYearsMax());
            addRange(predicates, cb, root, "studyDate", f.studyDateMin(), f.studyDateMax());
            addRange(predicates, cb, root, "kvp", f.kvpMin(), f.kvpMax());
            addRange(predicates, cb, root, "exposureMas", f.exposureMasMin(), f.exposureMasMax());
            addRange(predicates, cb, root, "sliceThicknessMm", f.sliceThicknessMmMin(), f.sliceThicknessMmMax());
            addRange(predicates, cb, root, "pixelSpacingMm", f.pixelSpacingMmMin(), f.pixelSpacingMmMax());
            addRange(predicates, cb, root, "rows", f.rowsMin(), f.rowsMax());
            addRange(predicates, cb, root, "columns", f.columnsMin(), f.columnsMax());
            addRange(predicates, cb, root, "nSlices", f.nSlicesMin(), f.nSlicesMax());

            // Shape
            addRange(predicates, cb, root, "shapeMeshVolume", f.shapeMeshVolumeMin(), f.shapeMeshVolumeMax());
            addRange(predicates, cb, root, "shapeVoxelVolume", f.shapeVoxelVolumeMin(), f.shapeVoxelVolumeMax());
            addRange(predicates, cb, root, "shapeSurfaceArea", f.shapeSurfaceAreaMin(), f.shapeSurfaceAreaMax());
            addRange(predicates, cb, root, "shapeSphericity", f.shapeSphericityMin(), f.shapeSphericityMax());
            addRange(predicates, cb, root, "shapeCompactness1", f.shapeCompactness1Min(), f.shapeCompactness1Max());
            addRange(predicates, cb, root, "shapeCompactness2", f.shapeCompactness2Min(), f.shapeCompactness2Max());
            addRange(predicates, cb, root, "shapeMaximum3dDiameter", f.shapeMaximum3dDiameterMin(),
                    f.shapeMaximum3dDiameterMax());
            addRange(predicates, cb, root, "shapeMajorAxisLength", f.shapeMajorAxisLengthMin(),
                    f.shapeMajorAxisLengthMax());
            addRange(predicates, cb, root, "shapeMinorAxisLength", f.shapeMinorAxisLengthMin(),
                    f.shapeMinorAxisLengthMax());
            addRange(predicates, cb, root, "shapeLeastAxisLength", f.shapeLeastAxisLengthMin(),
                    f.shapeLeastAxisLengthMax());
            addRange(predicates, cb, root, "shapeElongation", f.shapeElongationMin(), f.shapeElongationMax());
            addRange(predicates, cb, root, "shapeFlatness", f.shapeFlatnessMin(), f.shapeFlatnessMax());

            // First order
            addRange(predicates, cb, root, "firstEnergy", f.firstEnergyMin(), f.firstEnergyMax());
            addRange(predicates, cb, root, "firstTotalEnergy", f.firstTotalEnergyMin(), f.firstTotalEnergyMax());
            addRange(predicates, cb, root, "firstEntropy", f.firstEntropyMin(), f.firstEntropyMax());
            addRange(predicates, cb, root, "firstMinimum", f.firstMinimumMin(), f.firstMinimumMax());
            addRange(predicates, cb, root, "first10thPercentile", f.first10thPercentileMin(),
                    f.first10thPercentileMax());
            addRange(predicates, cb, root, "first90thPercentile", f.first90thPercentileMin(),
                    f.first90thPercentileMax());
            addRange(predicates, cb, root, "firstMaximum", f.firstMaximumMin(), f.firstMaximumMax());
            addRange(predicates, cb, root, "firstMean", f.firstMeanMin(), f.firstMeanMax());
            addRange(predicates, cb, root, "firstMedian", f.firstMedianMin(), f.firstMedianMax());
            addRange(predicates, cb, root, "firstInterquartileRange", f.firstInterquartileRangeMin(),
                    f.firstInterquartileRangeMax());
            addRange(predicates, cb, root, "firstRange", f.firstRangeMin(), f.firstRangeMax());
            addRange(predicates, cb, root, "firstMeanAbsoluteDeviation", f.firstMeanAbsoluteDeviationMin(),
                    f.firstMeanAbsoluteDeviationMax());
            addRange(predicates, cb, root, "firstRobustMeanAbsoluteDeviation", f.firstRobustMeanAbsoluteDeviationMin(),
                    f.firstRobustMeanAbsoluteDeviationMax());
            addRange(predicates, cb, root, "firstRootMeanSquared", f.firstRootMeanSquaredMin(),
                    f.firstRootMeanSquaredMax());
            addRange(predicates, cb, root, "firstSkewness", f.firstSkewnessMin(), f.firstSkewnessMax());
            addRange(predicates, cb, root, "firstKurtosis", f.firstKurtosisMin(), f.firstKurtosisMax());
            addRange(predicates, cb, root, "firstVariance", f.firstVarianceMin(), f.firstVarianceMax());
            addRange(predicates, cb, root, "firstUniformity", f.firstUniformityMin(), f.firstUniformityMax());

            // GLCM
            addRange(predicates, cb, root, "glcmAutocorrelation", f.glcmAutocorrelationMin(),
                    f.glcmAutocorrelationMax());
            addRange(predicates, cb, root, "glcmClusterProminence", f.glcmClusterProminenceMin(),
                    f.glcmClusterProminenceMax());
            addRange(predicates, cb, root, "glcmClusterShade", f.glcmClusterShadeMin(), f.glcmClusterShadeMax());
            addRange(predicates, cb, root, "glcmClusterTendency", f.glcmClusterTendencyMin(),
                    f.glcmClusterTendencyMax());
            addRange(predicates, cb, root, "glcmContrast", f.glcmContrastMin(), f.glcmContrastMax());
            addRange(predicates, cb, root, "glcmCorrelation", f.glcmCorrelationMin(), f.glcmCorrelationMax());
            addRange(predicates, cb, root, "glcmDifferenceAverage", f.glcmDifferenceAverageMin(),
                    f.glcmDifferenceAverageMax());
            addRange(predicates, cb, root, "glcmDifferenceEntropy", f.glcmDifferenceEntropyMin(),
                    f.glcmDifferenceEntropyMax());
            addRange(predicates, cb, root, "glcmDifferenceVariance", f.glcmDifferenceVarianceMin(),
                    f.glcmDifferenceVarianceMax());
            addRange(predicates, cb, root, "glcmId", f.glcmIdMin(), f.glcmIdMax());
            addRange(predicates, cb, root, "glcmIdm", f.glcmIdmMin(), f.glcmIdmMax());
            addRange(predicates, cb, root, "glcmIdmn", f.glcmIdmnMin(), f.glcmIdmnMax());
            addRange(predicates, cb, root, "glcmIdn", f.glcmIdnMin(), f.glcmIdnMax());
            addRange(predicates, cb, root, "glcmImc1", f.glcmImc1Min(), f.glcmImc1Max());
            addRange(predicates, cb, root, "glcmImc2", f.glcmImc2Min(), f.glcmImc2Max());
            addRange(predicates, cb, root, "glcmInverseVariance", f.glcmInverseVarianceMin(),
                    f.glcmInverseVarianceMax());
            addRange(predicates, cb, root, "glcmJointAverage", f.glcmJointAverageMin(), f.glcmJointAverageMax());
            addRange(predicates, cb, root, "glcmJointEnergy", f.glcmJointEnergyMin(), f.glcmJointEnergyMax());
            addRange(predicates, cb, root, "glcmJointEntropy", f.glcmJointEntropyMin(), f.glcmJointEntropyMax());
            addRange(predicates, cb, root, "glcmMaxProbabilities", f.glcmMaxProbabilitiesMin(),
                    f.glcmMaxProbabilitiesMax());
            addRange(predicates, cb, root, "glcmSumAverage", f.glcmSumAverageMin(), f.glcmSumAverageMax());
            addRange(predicates, cb, root, "glcmSumEntropy", f.glcmSumEntropyMin(), f.glcmSumEntropyMax());
            addRange(predicates, cb, root, "glcmSumSquares", f.glcmSumSquaresMin(), f.glcmSumSquaresMax());

            // GLRLM
            addRange(predicates, cb, root, "glrlmGrayLevelNonUniformity", f.glrlmGrayLevelNonUniformityMin(),
                    f.glrlmGrayLevelNonUniformityMax());
            addRange(predicates, cb, root, "glrlmGrayLevelNonUniformityNormalized",
                    f.glrlmGrayLevelNonUniformityNormalizedMin(), f.glrlmGrayLevelNonUniformityNormalizedMax());
            addRange(predicates, cb, root, "glrlmGrayLevelVariance", f.glrlmGrayLevelVarianceMin(),
                    f.glrlmGrayLevelVarianceMax());
            addRange(predicates, cb, root, "glrlmHighGrayLevelRunEmphasis", f.glrlmHighGrayLevelRunEmphasisMin(),
                    f.glrlmHighGrayLevelRunEmphasisMax());
            addRange(predicates, cb, root, "glrlmLongRunEmphasis", f.glrlmLongRunEmphasisMin(),
                    f.glrlmLongRunEmphasisMax());
            addRange(predicates, cb, root, "glrlmLongRunHighGrayLevelEmphasis",
                    f.glrlmLongRunHighGrayLevelEmphasisMin(), f.glrlmLongRunHighGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "glrlmLongRunLowGrayLevelEmphasis", f.glrlmLongRunLowGrayLevelEmphasisMin(),
                    f.glrlmLongRunLowGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "glrlmLowGrayLevelRunEmphasis", f.glrlmLowGrayLevelRunEmphasisMin(),
                    f.glrlmLowGrayLevelRunEmphasisMax());
            addRange(predicates, cb, root, "glrlmRunEntropy", f.glrlmRunEntropyMin(), f.glrlmRunEntropyMax());
            addRange(predicates, cb, root, "glrlmRunLengthNonUniformity", f.glrlmRunLengthNonUniformityMin(),
                    f.glrlmRunLengthNonUniformityMax());
            addRange(predicates, cb, root, "glrlmRunLengthNonUniformityNormalized",
                    f.glrlmRunLengthNonUniformityNormalizedMin(), f.glrlmRunLengthNonUniformityNormalizedMax());
            addRange(predicates, cb, root, "glrlmRunPercentage", f.glrlmRunPercentageMin(), f.glrlmRunPercentageMax());
            addRange(predicates, cb, root, "glrlmRunVariance", f.glrlmRunVarianceMin(), f.glrlmRunVarianceMax());
            addRange(predicates, cb, root, "glrlmShortRunEmphasis", f.glrlmShortRunEmphasisMin(),
                    f.glrlmShortRunEmphasisMax());
            addRange(predicates, cb, root, "glrlmShortRunHighGrayLevelEmphasis",
                    f.glrlmShortRunHighGrayLevelEmphasisMin(), f.glrlmShortRunHighGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "glrlmShortRunLowGrayLevelEmphasis",
                    f.glrlmShortRunLowGrayLevelEmphasisMin(), f.glrlmShortRunLowGrayLevelEmphasisMax());

            // GLSZM
            addRange(predicates, cb, root, "glszmGrayLevelNonUniformity", f.glszmGrayLevelNonUniformityMin(),
                    f.glszmGrayLevelNonUniformityMax());
            addRange(predicates, cb, root, "glszmGrayLevelNonUniformityNormalized",
                    f.glszmGrayLevelNonUniformityNormalizedMin(), f.glszmGrayLevelNonUniformityNormalizedMax());
            addRange(predicates, cb, root, "glszmGrayLevelVariance", f.glszmGrayLevelVarianceMin(),
                    f.glszmGrayLevelVarianceMax());
            addRange(predicates, cb, root, "glszmHighGrayLevelZoneEmphasis", f.glszmHighGrayLevelZoneEmphasisMin(),
                    f.glszmHighGrayLevelZoneEmphasisMax());
            addRange(predicates, cb, root, "glszmLargeAreaEmphasis", f.glszmLargeAreaEmphasisMin(),
                    f.glszmLargeAreaEmphasisMax());
            addRange(predicates, cb, root, "glszmLargeAreaHighGrayLevelEmphasis",
                    f.glszmLargeAreaHighGrayLevelEmphasisMin(), f.glszmLargeAreaHighGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "glszmLargeAreaLowGrayLevelEmphasis",
                    f.glszmLargeAreaLowGrayLevelEmphasisMin(), f.glszmLargeAreaLowGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "glszmLowGrayLevelZoneEmphasis", f.glszmLowGrayLevelZoneEmphasisMin(),
                    f.glszmLowGrayLevelZoneEmphasisMax());
            addRange(predicates, cb, root, "glszmSizeZoneNonUniformity", f.glszmSizeZoneNonUniformityMin(),
                    f.glszmSizeZoneNonUniformityMax());
            addRange(predicates, cb, root, "glszmSizeZoneNonUniformityNormalized",
                    f.glszmSizeZoneNonUniformityNormalizedMin(), f.glszmSizeZoneNonUniformityNormalizedMax());
            addRange(predicates, cb, root, "glszmSmallAreaEmphasis", f.glszmSmallAreaEmphasisMin(),
                    f.glszmSmallAreaEmphasisMax());
            addRange(predicates, cb, root, "glszmSmallAreaHighGrayLevelEmphasis",
                    f.glszmSmallAreaHighGrayLevelEmphasisMin(), f.glszmSmallAreaHighGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "glszmSmallAreaLowGrayLevelEmphasis",
                    f.glszmSmallAreaLowGrayLevelEmphasisMin(), f.glszmSmallAreaLowGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "glszmZoneEntropy", f.glszmZoneEntropyMin(), f.glszmZoneEntropyMax());
            addRange(predicates, cb, root, "glszmZonePercentage", f.glszmZonePercentageMin(),
                    f.glszmZonePercentageMax());
            addRange(predicates, cb, root, "glszmZoneVariance", f.glszmZoneVarianceMin(), f.glszmZoneVarianceMax());

            // NGTDM
            addRange(predicates, cb, root, "ngtdmBusyness", f.ngtdmBusynessMin(), f.ngtdmBusynessMax());
            addRange(predicates, cb, root, "ngtdmCoarseness", f.ngtdmCoarsenessMin(), f.ngtdmCoarsenessMax());
            addRange(predicates, cb, root, "ngtdmComplexity", f.ngtdmComplexityMin(), f.ngtdmComplexityMax());
            addRange(predicates, cb, root, "ngtdmContrast", f.ngtdmContrastMin(), f.ngtdmContrastMax());
            addRange(predicates, cb, root, "ngtdmStrength", f.ngtdmStrengthMin(), f.ngtdmStrengthMax());

            // GLDM
            addRange(predicates, cb, root, "gldmDependenceEntropy", f.gldmDependenceEntropyMin(),
                    f.gldmDependenceEntropyMax());
            addRange(predicates, cb, root, "gldmDependenceNonUniformity", f.gldmDependenceNonUniformityMin(),
                    f.gldmDependenceNonUniformityMax());
            addRange(predicates, cb, root, "gldmDependenceNonUniformityNormalized",
                    f.gldmDependenceNonUniformityNormalizedMin(), f.gldmDependenceNonUniformityNormalizedMax());
            addRange(predicates, cb, root, "gldmDependenceVariance", f.gldmDependenceVarianceMin(),
                    f.gldmDependenceVarianceMax());
            addRange(predicates, cb, root, "gldmGrayLevelNonUniformity", f.gldmGrayLevelNonUniformityMin(),
                    f.gldmGrayLevelNonUniformityMax());
            addRange(predicates, cb, root, "gldmGrayLevelVariance", f.gldmGrayLevelVarianceMin(),
                    f.gldmGrayLevelVarianceMax());
            addRange(predicates, cb, root, "gldmHighGrayLevelEmphasis", f.gldmHighGrayLevelEmphasisMin(),
                    f.gldmHighGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "gldmLargeDependenceEmphasis", f.gldmLargeDependenceEmphasisMin(),
                    f.gldmLargeDependenceEmphasisMax());
            addRange(predicates, cb, root, "gldmLargeDependenceHighGrayLevelEmphasis",
                    f.gldmLargeDependenceHighGrayLevelEmphasisMin(), f.gldmLargeDependenceHighGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "gldmLargeDependenceLowGrayLevelEmphasis",
                    f.gldmLargeDependenceLowGrayLevelEmphasisMin(), f.gldmLargeDependenceLowGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "gldmLowGrayLevelEmphasis", f.gldmLowGrayLevelEmphasisMin(),
                    f.gldmLowGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "gldmSmallDependenceEmphasis", f.gldmSmallDependenceEmphasisMin(),
                    f.gldmSmallDependenceEmphasisMax());
            addRange(predicates, cb, root, "gldmSmallDependenceHighGrayLevelEmphasis",
                    f.gldmSmallDependenceHighGrayLevelEmphasisMin(), f.gldmSmallDependenceHighGrayLevelEmphasisMax());
            addRange(predicates, cb, root, "gldmSmallDependenceLowGrayLevelEmphasis",
                    f.gldmSmallDependenceLowGrayLevelEmphasisMin(), f.gldmSmallDependenceLowGrayLevelEmphasisMax());

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static <T> void addEqual(
            List<Predicate> predicates, CriteriaBuilder cb,
            Root<Image> root, String field, T value) {
        if (value != null) {
            predicates.add(cb.equal(root.get(field), value));
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void addRange(
            List<Predicate> predicates, CriteriaBuilder cb,
            Root<Image> root, String field, T min, T max) {
        if (min != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(field), min));
        }
        if (max != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(field), max));
        }
    }
}