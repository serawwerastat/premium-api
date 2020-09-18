package lv.afilatov.premiumapi.domain.enums;

import java.math.BigDecimal;

public enum RiskType {
    FIRE(0.014, new BigDecimal(100), false, 0.024),
    THEFT(0.11, new BigDecimal(15), true, 0.05);

    private final double coefficient;
    private final boolean isThresholdInclusive;
    private final BigDecimal sumThreshold;
    private final double thresholdCoefficient;

    RiskType(double coefficient, BigDecimal sumThreshold, boolean isThresholdInclusive, double thresholdCoefficient) {
        this.coefficient = coefficient;
        this.sumThreshold = sumThreshold;
        this.isThresholdInclusive = isThresholdInclusive;
        this.thresholdCoefficient = thresholdCoefficient;
    }

    public double getCoefficientBasedOn(BigDecimal insuredSum) {
        if (isBiggerOrEqualThenThreshold(insuredSum)) {
            return thresholdCoefficient;
        }
        return coefficient;
    }

    private boolean isBiggerOrEqualThenThreshold(BigDecimal insuredSum) {
        var compareResult = insuredSum.compareTo(sumThreshold);
        return isThresholdInclusive ? compareResult >= 0 : compareResult > 0;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public boolean isThresholdInclusive() {
        return isThresholdInclusive;
    }

    public BigDecimal getSumThreshold() {
        return sumThreshold;
    }

    public double getThresholdCoefficient() {
        return thresholdCoefficient;
    }

    @Override public String toString() {
        return this.name();
    }
}
