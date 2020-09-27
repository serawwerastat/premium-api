package lv.afilatov.premiumapi.domain.interfaces;

import java.math.BigDecimal;

public interface CalculatableRiskType {

    BigDecimal calculate(BigDecimal insuredSum);
}
