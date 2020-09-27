package lv.afilatov.premiumapi.domain.model.risk_type;

import java.math.BigDecimal;

public class RiskTypeThreshold {

    private final BigDecimal sumThreshold;
    private final double thresholdCoefficient;
    private final boolean isThresholdInclusive;

    private RiskTypeThreshold(BigDecimal sumThreshold, double thresholdCoefficient, boolean isThresholdInclusive) {
        this.isThresholdInclusive = isThresholdInclusive;
        this.sumThreshold = sumThreshold;
        this.thresholdCoefficient = thresholdCoefficient;
    }

    public static RiskTypeThreshold createInclusiveThreshold(int sumThreshold, double thresholdCoefficient) {
        return new RiskTypeThreshold(new BigDecimal(sumThreshold), thresholdCoefficient, true);
    }

    public static RiskTypeThreshold createExclusiveThreshold(int sumThreshold, double thresholdCoefficient) {
        return new RiskTypeThreshold(new BigDecimal(sumThreshold), thresholdCoefficient, false);
    }

    public boolean exceedThreshold(BigDecimal insuredSum) {
        var compareResult = insuredSum.compareTo(sumThreshold);
        return isThresholdInclusive ? compareResult >= 0 : compareResult > 0;
    }

    public double getThresholdCoefficient() {
        return thresholdCoefficient;
    }

    public BigDecimal getSumThreshold() {
        return sumThreshold;
    }
}
