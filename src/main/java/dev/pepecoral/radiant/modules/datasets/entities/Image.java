package dev.pepecoral.radiant.modules.datasets.entities;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "images")
@EqualsAndHashCode(of = { "id" })
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dataset_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Dataset dataset;

    // -------------------------------------------------------------------------
    // DICOM metadata — all nullable, all indexed for filtering
    // -------------------------------------------------------------------------

    @Column(nullable = true)
    String sex;

    @Column(nullable = true)
    Integer ageYears;

    @Column(nullable = true)
    LocalDate studyDate;

    @Column(nullable = true)
    Double kvp;

    @Column(nullable = true)
    Double exposureMas;

    @Column(nullable = true)
    String convolutionKernel;

    @Column(nullable = true)
    Double sliceThicknessMm;

    @Column(nullable = true)
    Double pixelSpacingMm;

    @Column(nullable = true)
    String manufacturer;

    @Column(nullable = true)
    String manufacturerModel;

    @Column(nullable = true)
    String softwareVersion;

    @Column(nullable = true)
    Integer rows;

    @Column(nullable = true)
    Integer columns;

    @Column(nullable = true)
    Integer nSlices;

    @Column(nullable = false)
    boolean hasSegmentation = false;

    // -------------------------------------------------------------------------
    // Radiomics — all nullable (null when hasSegmentation is false)
    // -------------------------------------------------------------------------

    // Shape
    @Column(nullable = true)
    Double shapeMeshVolume;
    @Column(nullable = true)
    Double shapeVoxelVolume;
    @Column(nullable = true)
    Double shapeSurfaceArea;
    @Column(nullable = true)
    Double shapeSphericity;
    @Column(nullable = true)
    Double shapeCompactness1;
    @Column(nullable = true)
    Double shapeCompactness2;
    @Column(nullable = true)
    Double shapeMaximum3dDiameter;
    @Column(nullable = true)
    Double shapeMajorAxisLength;
    @Column(nullable = true)
    Double shapeMinorAxisLength;
    @Column(nullable = true)
    Double shapeLeastAxisLength;
    @Column(nullable = true)
    Double shapeElongation;
    @Column(nullable = true)
    Double shapeFlatness;

    // First order
    @Column(nullable = true)
    Double firstEnergy;
    @Column(nullable = true)
    Double firstTotalEnergy;
    @Column(nullable = true)
    Double firstEntropy;
    @Column(nullable = true)
    Double firstMinimum;
    @Column(nullable = true)
    Double first10thPercentile;
    @Column(nullable = true)
    Double first90thPercentile;
    @Column(nullable = true)
    Double firstMaximum;
    @Column(nullable = true)
    Double firstMean;
    @Column(nullable = true)
    Double firstMedian;
    @Column(nullable = true)
    Double firstInterquartileRange;
    @Column(nullable = true)
    Double firstRange;
    @Column(nullable = true)
    Double firstMeanAbsoluteDeviation;
    @Column(nullable = true)
    Double firstRobustMeanAbsoluteDeviation;
    @Column(nullable = true)
    Double firstRootMeanSquared;
    @Column(nullable = true)
    Double firstSkewness;
    @Column(nullable = true)
    Double firstKurtosis;
    @Column(nullable = true)
    Double firstVariance;
    @Column(nullable = true)
    Double firstUniformity;

    // GLCM
    @Column(nullable = true)
    Double glcmAutocorrelation;
    @Column(nullable = true)
    Double glcmClusterProminence;
    @Column(nullable = true)
    Double glcmClusterShade;
    @Column(nullable = true)
    Double glcmClusterTendency;
    @Column(nullable = true)
    Double glcmContrast;
    @Column(nullable = true)
    Double glcmCorrelation;
    @Column(nullable = true)
    Double glcmDifferenceAverage;
    @Column(nullable = true)
    Double glcmDifferenceEntropy;
    @Column(nullable = true)
    Double glcmDifferenceVariance;
    @Column(nullable = true)
    Double glcmId;
    @Column(nullable = true)
    Double glcmIdm;
    @Column(nullable = true)
    Double glcmIdmn;
    @Column(nullable = true)
    Double glcmIdn;
    @Column(nullable = true)
    Double glcmImc1;
    @Column(nullable = true)
    Double glcmImc2;
    @Column(nullable = true)
    Double glcmInverseVariance;
    @Column(nullable = true)
    Double glcmJointAverage;
    @Column(nullable = true)
    Double glcmJointEnergy;
    @Column(nullable = true)
    Double glcmJointEntropy;
    @Column(nullable = true)
    Double glcmMaxProbabilities;
    @Column(nullable = true)
    Double glcmSumAverage;
    @Column(nullable = true)
    Double glcmSumEntropy;
    @Column(nullable = true)
    Double glcmSumSquares;

    // GLRLM
    @Column(nullable = true)
    Double glrlmGrayLevelNonUniformity;
    @Column(nullable = true)
    Double glrlmGrayLevelNonUniformityNormalized;
    @Column(nullable = true)
    Double glrlmGrayLevelVariance;
    @Column(nullable = true)
    Double glrlmHighGrayLevelRunEmphasis;
    @Column(nullable = true)
    Double glrlmLongRunEmphasis;
    @Column(nullable = true)
    Double glrlmLongRunHighGrayLevelEmphasis;
    @Column(nullable = true)
    Double glrlmLongRunLowGrayLevelEmphasis;
    @Column(nullable = true)
    Double glrlmLowGrayLevelRunEmphasis;
    @Column(nullable = true)
    Double glrlmRunEntropy;
    @Column(nullable = true)
    Double glrlmRunLengthNonUniformity;
    @Column(nullable = true)
    Double glrlmRunLengthNonUniformityNormalized;
    @Column(nullable = true)
    Double glrlmRunPercentage;
    @Column(nullable = true)
    Double glrlmRunVariance;
    @Column(nullable = true)
    Double glrlmShortRunEmphasis;
    @Column(nullable = true)
    Double glrlmShortRunHighGrayLevelEmphasis;
    @Column(nullable = true)
    Double glrlmShortRunLowGrayLevelEmphasis;

    // GLSZM
    @Column(nullable = true)
    Double glszmGrayLevelNonUniformity;
    @Column(nullable = true)
    Double glszmGrayLevelNonUniformityNormalized;
    @Column(nullable = true)
    Double glszmGrayLevelVariance;
    @Column(nullable = true)
    Double glszmHighGrayLevelZoneEmphasis;
    @Column(nullable = true)
    Double glszmLargeAreaEmphasis;
    @Column(nullable = true)
    Double glszmLargeAreaHighGrayLevelEmphasis;
    @Column(nullable = true)
    Double glszmLargeAreaLowGrayLevelEmphasis;
    @Column(nullable = true)
    Double glszmLowGrayLevelZoneEmphasis;
    @Column(nullable = true)
    Double glszmSizeZoneNonUniformity;
    @Column(nullable = true)
    Double glszmSizeZoneNonUniformityNormalized;
    @Column(nullable = true)
    Double glszmSmallAreaEmphasis;
    @Column(nullable = true)
    Double glszmSmallAreaHighGrayLevelEmphasis;
    @Column(nullable = true)
    Double glszmSmallAreaLowGrayLevelEmphasis;
    @Column(nullable = true)
    Double glszmZoneEntropy;
    @Column(nullable = true)
    Double glszmZonePercentage;
    @Column(nullable = true)
    Double glszmZoneVariance;

    // NGTDM
    @Column(nullable = true)
    Double ngtdmBusyness;
    @Column(nullable = true)
    Double ngtdmCoarseness;
    @Column(nullable = true)
    Double ngtdmComplexity;
    @Column(nullable = true)
    Double ngtdmContrast;
    @Column(nullable = true)
    Double ngtdmStrength;

    // GLDM
    @Column(nullable = true)
    Double gldmDependenceEntropy;
    @Column(nullable = true)
    Double gldmDependenceNonUniformity;
    @Column(nullable = true)
    Double gldmDependenceNonUniformityNormalized;
    @Column(nullable = true)
    Double gldmDependenceVariance;
    @Column(nullable = true)
    Double gldmGrayLevelNonUniformity;
    @Column(nullable = true)
    Double gldmGrayLevelVariance;
    @Column(nullable = true)
    Double gldmHighGrayLevelEmphasis;
    @Column(nullable = true)
    Double gldmLargeDependenceEmphasis;
    @Column(nullable = true)
    Double gldmLargeDependenceHighGrayLevelEmphasis;
    @Column(nullable = true)
    Double gldmLargeDependenceLowGrayLevelEmphasis;
    @Column(nullable = true)
    Double gldmLowGrayLevelEmphasis;
    @Column(nullable = true)
    Double gldmSmallDependenceEmphasis;
    @Column(nullable = true)
    Double gldmSmallDependenceHighGrayLevelEmphasis;
    @Column(nullable = true)
    Double gldmSmallDependenceLowGrayLevelEmphasis;
}