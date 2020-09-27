package lv.afilatov.premiumapi.domain.model.risk_type;

import java.math.BigDecimal;

import lv.afilatov.premiumapi.domain.interfaces.CalculatableRiskType;

public class SampleRiskType implements CalculatableRiskType {

    private Object any1;
    private Object any2;
    private Object any3;

    // any variables
    public SampleRiskType(Object any1, Object any2, Object any3) {
        this.any1 = any1;
        this.any2 = any2;
        this.any3 = any3;
    }

    @Override public BigDecimal calculate(BigDecimal insuredSum) {
        // any logic you want
        return BigDecimal.ZERO;
    }
}
