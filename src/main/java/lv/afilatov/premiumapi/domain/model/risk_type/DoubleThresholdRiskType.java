package lv.afilatov.premiumapi.domain.model.risk_type;

import java.math.BigDecimal;

import lv.afilatov.premiumapi.domain.interfaces.CalculatableRiskType;

public class DoubleThresholdRiskType implements CalculatableRiskType {

    private final double defaultCoefficient;
    private final RiskTypeThreshold firstThreshold;
    private final RiskTypeThreshold secondThreshold;

    public DoubleThresholdRiskType(double defaultCoefficient, RiskTypeThreshold firstThreshold, RiskTypeThreshold secondThreshold) {
        if (firstThreshold.getSumThreshold().compareTo(secondThreshold.getSumThreshold()) >= 0) {
            var exceptionMessage = String.format("Second threshold should be greater then first one. "
                    + "First Threshold: %s. Second Threshold: %s.", firstThreshold.getSumThreshold(), secondThreshold.getSumThreshold());
            throw new IllegalArgumentException(exceptionMessage);
        }
        this.defaultCoefficient = defaultCoefficient;
        this.firstThreshold = firstThreshold;
        this.secondThreshold = secondThreshold;
    }

    @Override
    public BigDecimal calculate(BigDecimal insuredSum) {
        var coefficient = getCoefficientBasedOn(insuredSum);
        return insuredSum.multiply(new BigDecimal(coefficient));
    }

    private double getCoefficientBasedOn(BigDecimal insuredSum) {
        if (firstThreshold.exceedThreshold(insuredSum)) {
            return secondThreshold.exceedThreshold(insuredSum) ?
                    secondThreshold.getThresholdCoefficient() :
                    firstThreshold.getThresholdCoefficient();
        }
        return defaultCoefficient;
    }
}
