package lv.afilatov.premiumapi.domain.model.risk_type;

import java.math.BigDecimal;

import lv.afilatov.premiumapi.domain.interfaces.CalculatableRiskType;

public class SingleThresholdRiskType implements CalculatableRiskType {

    private final double defaultCoefficient;
    private final RiskTypeThreshold threshold;

    public SingleThresholdRiskType(double defaultCoefficient, RiskTypeThreshold threshold) {
        this.defaultCoefficient = defaultCoefficient;
        this.threshold = threshold;
    }

    @Override
    public BigDecimal calculate(BigDecimal insuredSum) {
        var coefficient = getCoefficientBasedOn(insuredSum);
        return insuredSum.multiply(new BigDecimal(coefficient));
    }

    private double getCoefficientBasedOn(BigDecimal insuredSum) {
        return threshold.exceedThreshold(insuredSum) ? threshold.getThresholdCoefficient() : defaultCoefficient;
    }
}
