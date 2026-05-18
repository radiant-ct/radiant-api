package dev.pepecoral.radiant.modules.datasets.dtos;

import java.time.LocalDate;

import dev.pepecoral.radiant.modules.datasets.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageCreationDTO {

    // DICOM metadata
    private String sex;
    private Integer ageYears;
    private LocalDate studyDate;
    private Double kvp;
    private Double exposureMas;
    private String convolutionKernel;
    private Double sliceThicknessMm;
    private Double pixelSpacingMm;
    private String manufacturer;
    private String manufacturerModel;
    private String softwareVersion;
    private Integer rows;
    private Integer columns;
    private Integer nSlices;
    private boolean hasSegmentation;

    // Radiomics — shape
    private Double shapeMeshVolume;
    private Double shapeVoxelVolume;
    private Double shapeSurfaceArea;
    private Double shapeSphericity;
    private Double shapeCompactness1;
    private Double shapeCompactness2;
    private Double shapeMaximum3dDiameter;
    private Double shapeMajorAxisLength;
    private Double shapeMinorAxisLength;
    private Double shapeLeastAxisLength;
    private Double shapeElongation;
    private Double shapeFlatness;

    // Radiomics — first order
    private Double firstEnergy;
    private Double firstTotalEnergy;
    private Double firstEntropy;
    private Double firstMinimum;
    private Double first10thPercentile;
    private Double first90thPercentile;
    private Double firstMaximum;
    private Double firstMean;
    private Double firstMedian;
    private Double firstInterquartileRange;
    private Double firstRange;
    private Double firstMeanAbsoluteDeviation;
    private Double firstRobustMeanAbsoluteDeviation;
    private Double firstRootMeanSquared;
    private Double firstSkewness;
    private Double firstKurtosis;
    private Double firstVariance;
    private Double firstUniformity;

    // Radiomics — GLCM
    private Double glcmAutocorrelation;
    private Double glcmClusterProminence;
    private Double glcmClusterShade;
    private Double glcmClusterTendency;
    private Double glcmContrast;
    private Double glcmCorrelation;
    private Double glcmDifferenceAverage;
    private Double glcmDifferenceEntropy;
    private Double glcmDifferenceVariance;
    private Double glcmId;
    private Double glcmIdm;
    private Double glcmIdmn;
    private Double glcmIdn;
    private Double glcmImc1;
    private Double glcmImc2;
    private Double glcmInverseVariance;
    private Double glcmJointAverage;
    private Double glcmJointEnergy;
    private Double glcmJointEntropy;
    private Double glcmMaxProbabilities;
    private Double glcmSumAverage;
    private Double glcmSumEntropy;
    private Double glcmSumSquares;

    // Radiomics — GLRLM
    private Double glrlmGrayLevelNonUniformity;
    private Double glrlmGrayLevelNonUniformityNormalized;
    private Double glrlmGrayLevelVariance;
    private Double glrlmHighGrayLevelRunEmphasis;
    private Double glrlmLongRunEmphasis;
    private Double glrlmLongRunHighGrayLevelEmphasis;
    private Double glrlmLongRunLowGrayLevelEmphasis;
    private Double glrlmLowGrayLevelRunEmphasis;
    private Double glrlmRunEntropy;
    private Double glrlmRunLengthNonUniformity;
    private Double glrlmRunLengthNonUniformityNormalized;
    private Double glrlmRunPercentage;
    private Double glrlmRunVariance;
    private Double glrlmShortRunEmphasis;
    private Double glrlmShortRunHighGrayLevelEmphasis;
    private Double glrlmShortRunLowGrayLevelEmphasis;

    // Radiomics — GLSZM
    private Double glszmGrayLevelNonUniformity;
    private Double glszmGrayLevelNonUniformityNormalized;
    private Double glszmGrayLevelVariance;
    private Double glszmHighGrayLevelZoneEmphasis;
    private Double glszmLargeAreaEmphasis;
    private Double glszmLargeAreaHighGrayLevelEmphasis;
    private Double glszmLargeAreaLowGrayLevelEmphasis;
    private Double glszmLowGrayLevelZoneEmphasis;
    private Double glszmSizeZoneNonUniformity;
    private Double glszmSizeZoneNonUniformityNormalized;
    private Double glszmSmallAreaEmphasis;
    private Double glszmSmallAreaHighGrayLevelEmphasis;
    private Double glszmSmallAreaLowGrayLevelEmphasis;
    private Double glszmZoneEntropy;
    private Double glszmZonePercentage;
    private Double glszmZoneVariance;

    // Radiomics — NGTDM
    private Double ngtdmBusyness;
    private Double ngtdmCoarseness;
    private Double ngtdmComplexity;
    private Double ngtdmContrast;
    private Double ngtdmStrength;

    // Radiomics — GLDM
    private Double gldmDependenceEntropy;
    private Double gldmDependenceNonUniformity;
    private Double gldmDependenceNonUniformityNormalized;
    private Double gldmDependenceVariance;
    private Double gldmGrayLevelNonUniformity;
    private Double gldmGrayLevelVariance;
    private Double gldmHighGrayLevelEmphasis;
    private Double gldmLargeDependenceEmphasis;
    private Double gldmLargeDependenceHighGrayLevelEmphasis;
    private Double gldmLargeDependenceLowGrayLevelEmphasis;
    private Double gldmLowGrayLevelEmphasis;
    private Double gldmSmallDependenceEmphasis;
    private Double gldmSmallDependenceHighGrayLevelEmphasis;
    private Double gldmSmallDependenceLowGrayLevelEmphasis;

    public Image toImage() {
        Image image = new Image();
        image.setSex(this.sex);
        image.setAgeYears(this.ageYears);
        image.setStudyDate(this.studyDate);
        image.setKvp(this.kvp);
        image.setExposureMas(this.exposureMas);
        image.setConvolutionKernel(this.convolutionKernel);
        image.setSliceThicknessMm(this.sliceThicknessMm);
        image.setPixelSpacingMm(this.pixelSpacingMm);
        image.setManufacturer(this.manufacturer);
        image.setManufacturerModel(this.manufacturerModel);
        image.setSoftwareVersion(this.softwareVersion);
        image.setRows(this.rows);
        image.setColumns(this.columns);
        image.setNSlices(this.nSlices);
        image.setHasSegmentation(this.hasSegmentation);

        image.setShapeMeshVolume(this.shapeMeshVolume);
        image.setShapeVoxelVolume(this.shapeVoxelVolume);
        image.setShapeSurfaceArea(this.shapeSurfaceArea);
        image.setShapeSphericity(this.shapeSphericity);
        image.setShapeCompactness1(this.shapeCompactness1);
        image.setShapeCompactness2(this.shapeCompactness2);
        image.setShapeMaximum3dDiameter(this.shapeMaximum3dDiameter);
        image.setShapeMajorAxisLength(this.shapeMajorAxisLength);
        image.setShapeMinorAxisLength(this.shapeMinorAxisLength);
        image.setShapeLeastAxisLength(this.shapeLeastAxisLength);
        image.setShapeElongation(this.shapeElongation);
        image.setShapeFlatness(this.shapeFlatness);

        image.setFirstEnergy(this.firstEnergy);
        image.setFirstTotalEnergy(this.firstTotalEnergy);
        image.setFirstEntropy(this.firstEntropy);
        image.setFirstMinimum(this.firstMinimum);
        image.setFirst10thPercentile(this.first10thPercentile);
        image.setFirst90thPercentile(this.first90thPercentile);
        image.setFirstMaximum(this.firstMaximum);
        image.setFirstMean(this.firstMean);
        image.setFirstMedian(this.firstMedian);
        image.setFirstInterquartileRange(this.firstInterquartileRange);
        image.setFirstRange(this.firstRange);
        image.setFirstMeanAbsoluteDeviation(this.firstMeanAbsoluteDeviation);
        image.setFirstRobustMeanAbsoluteDeviation(this.firstRobustMeanAbsoluteDeviation);
        image.setFirstRootMeanSquared(this.firstRootMeanSquared);
        image.setFirstSkewness(this.firstSkewness);
        image.setFirstKurtosis(this.firstKurtosis);
        image.setFirstVariance(this.firstVariance);
        image.setFirstUniformity(this.firstUniformity);

        image.setGlcmAutocorrelation(this.glcmAutocorrelation);
        image.setGlcmClusterProminence(this.glcmClusterProminence);
        image.setGlcmClusterShade(this.glcmClusterShade);
        image.setGlcmClusterTendency(this.glcmClusterTendency);
        image.setGlcmContrast(this.glcmContrast);
        image.setGlcmCorrelation(this.glcmCorrelation);
        image.setGlcmDifferenceAverage(this.glcmDifferenceAverage);
        image.setGlcmDifferenceEntropy(this.glcmDifferenceEntropy);
        image.setGlcmDifferenceVariance(this.glcmDifferenceVariance);
        image.setGlcmId(this.glcmId);
        image.setGlcmIdm(this.glcmIdm);
        image.setGlcmIdmn(this.glcmIdmn);
        image.setGlcmIdn(this.glcmIdn);
        image.setGlcmImc1(this.glcmImc1);
        image.setGlcmImc2(this.glcmImc2);
        image.setGlcmInverseVariance(this.glcmInverseVariance);
        image.setGlcmJointAverage(this.glcmJointAverage);
        image.setGlcmJointEnergy(this.glcmJointEnergy);
        image.setGlcmJointEntropy(this.glcmJointEntropy);
        image.setGlcmMaxProbabilities(this.glcmMaxProbabilities);
        image.setGlcmSumAverage(this.glcmSumAverage);
        image.setGlcmSumEntropy(this.glcmSumEntropy);
        image.setGlcmSumSquares(this.glcmSumSquares);

        image.setGlrlmGrayLevelNonUniformity(this.glrlmGrayLevelNonUniformity);
        image.setGlrlmGrayLevelNonUniformityNormalized(this.glrlmGrayLevelNonUniformityNormalized);
        image.setGlrlmGrayLevelVariance(this.glrlmGrayLevelVariance);
        image.setGlrlmHighGrayLevelRunEmphasis(this.glrlmHighGrayLevelRunEmphasis);
        image.setGlrlmLongRunEmphasis(this.glrlmLongRunEmphasis);
        image.setGlrlmLongRunHighGrayLevelEmphasis(this.glrlmLongRunHighGrayLevelEmphasis);
        image.setGlrlmLongRunLowGrayLevelEmphasis(this.glrlmLongRunLowGrayLevelEmphasis);
        image.setGlrlmLowGrayLevelRunEmphasis(this.glrlmLowGrayLevelRunEmphasis);
        image.setGlrlmRunEntropy(this.glrlmRunEntropy);
        image.setGlrlmRunLengthNonUniformity(this.glrlmRunLengthNonUniformity);
        image.setGlrlmRunLengthNonUniformityNormalized(this.glrlmRunLengthNonUniformityNormalized);
        image.setGlrlmRunPercentage(this.glrlmRunPercentage);
        image.setGlrlmRunVariance(this.glrlmRunVariance);
        image.setGlrlmShortRunEmphasis(this.glrlmShortRunEmphasis);
        image.setGlrlmShortRunHighGrayLevelEmphasis(this.glrlmShortRunHighGrayLevelEmphasis);
        image.setGlrlmShortRunLowGrayLevelEmphasis(this.glrlmShortRunLowGrayLevelEmphasis);

        image.setGlszmGrayLevelNonUniformity(this.glszmGrayLevelNonUniformity);
        image.setGlszmGrayLevelNonUniformityNormalized(this.glszmGrayLevelNonUniformityNormalized);
        image.setGlszmGrayLevelVariance(this.glszmGrayLevelVariance);
        image.setGlszmHighGrayLevelZoneEmphasis(this.glszmHighGrayLevelZoneEmphasis);
        image.setGlszmLargeAreaEmphasis(this.glszmLargeAreaEmphasis);
        image.setGlszmLargeAreaHighGrayLevelEmphasis(this.glszmLargeAreaHighGrayLevelEmphasis);
        image.setGlszmLargeAreaLowGrayLevelEmphasis(this.glszmLargeAreaLowGrayLevelEmphasis);
        image.setGlszmLowGrayLevelZoneEmphasis(this.glszmLowGrayLevelZoneEmphasis);
        image.setGlszmSizeZoneNonUniformity(this.glszmSizeZoneNonUniformity);
        image.setGlszmSizeZoneNonUniformityNormalized(this.glszmSizeZoneNonUniformityNormalized);
        image.setGlszmSmallAreaEmphasis(this.glszmSmallAreaEmphasis);
        image.setGlszmSmallAreaHighGrayLevelEmphasis(this.glszmSmallAreaHighGrayLevelEmphasis);
        image.setGlszmSmallAreaLowGrayLevelEmphasis(this.glszmSmallAreaLowGrayLevelEmphasis);
        image.setGlszmZoneEntropy(this.glszmZoneEntropy);
        image.setGlszmZonePercentage(this.glszmZonePercentage);
        image.setGlszmZoneVariance(this.glszmZoneVariance);

        image.setNgtdmBusyness(this.ngtdmBusyness);
        image.setNgtdmCoarseness(this.ngtdmCoarseness);
        image.setNgtdmComplexity(this.ngtdmComplexity);
        image.setNgtdmContrast(this.ngtdmContrast);
        image.setNgtdmStrength(this.ngtdmStrength);

        image.setGldmDependenceEntropy(this.gldmDependenceEntropy);
        image.setGldmDependenceNonUniformity(this.gldmDependenceNonUniformity);
        image.setGldmDependenceNonUniformityNormalized(this.gldmDependenceNonUniformityNormalized);
        image.setGldmDependenceVariance(this.gldmDependenceVariance);
        image.setGldmGrayLevelNonUniformity(this.gldmGrayLevelNonUniformity);
        image.setGldmGrayLevelVariance(this.gldmGrayLevelVariance);
        image.setGldmHighGrayLevelEmphasis(this.gldmHighGrayLevelEmphasis);
        image.setGldmLargeDependenceEmphasis(this.gldmLargeDependenceEmphasis);
        image.setGldmLargeDependenceHighGrayLevelEmphasis(this.gldmLargeDependenceHighGrayLevelEmphasis);
        image.setGldmLargeDependenceLowGrayLevelEmphasis(this.gldmLargeDependenceLowGrayLevelEmphasis);
        image.setGldmLowGrayLevelEmphasis(this.gldmLowGrayLevelEmphasis);
        image.setGldmSmallDependenceEmphasis(this.gldmSmallDependenceEmphasis);
        image.setGldmSmallDependenceHighGrayLevelEmphasis(this.gldmSmallDependenceHighGrayLevelEmphasis);
        image.setGldmSmallDependenceLowGrayLevelEmphasis(this.gldmSmallDependenceLowGrayLevelEmphasis);

        return image;
    }

    public ImageCreationDTO(Image image) {
        this.sex = image.getSex();
        // This constructor is just for testing purposes
    }
}