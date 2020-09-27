package lv.afilatov.premiumapi.domain.enums;

import static lv.afilatov.premiumapi.domain.model.risk_type.RiskTypeThreshold.createExclusiveThreshold;
import static lv.afilatov.premiumapi.domain.model.risk_type.RiskTypeThreshold.createInclusiveThreshold;

import lv.afilatov.premiumapi.domain.interfaces.CalculatableRiskType;
import lv.afilatov.premiumapi.domain.model.risk_type.DoubleThresholdRiskType;
import lv.afilatov.premiumapi.domain.model.risk_type.SampleRiskType;
import lv.afilatov.premiumapi.domain.model.risk_type.SingleThresholdRiskType;

public enum RiskType {
    FIRE(new SingleThresholdRiskType(0.014, createExclusiveThreshold(100, 0.024))),
    THEFT(new SingleThresholdRiskType(0.11, createInclusiveThreshold(15, 0.05))),
    FLOOD(new DoubleThresholdRiskType(0.05, createInclusiveThreshold(50, 0.1),
            createExclusiveThreshold(100, 0.2))),
    ALIENS(new SampleRiskType("anyValue", "anyValue", "anyValue"));

    private final CalculatableRiskType calculatableRiskType;

    RiskType(CalculatableRiskType calculatableRiskType) {
        this.calculatableRiskType = calculatableRiskType;
    }

    public CalculatableRiskType getCalculatableRiskType() {
        return calculatableRiskType;
    }

    @Override public String toString() {
        return this.name();
    }
}
