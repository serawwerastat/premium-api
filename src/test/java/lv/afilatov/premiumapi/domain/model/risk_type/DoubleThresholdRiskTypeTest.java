package lv.afilatov.premiumapi.domain.model.risk_type;

import static lv.afilatov.premiumapi.domain.model.risk_type.RiskTypeThreshold.createExclusiveThreshold;
import static lv.afilatov.premiumapi.test_util.TestConstructors.createRiskTypeThreshold;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

public class DoubleThresholdRiskTypeTest {

    @Test
    public void calculateDefaultCoefficientTest() {
        var sumThreshold = RandomUtils.nextInt(1, 100);
        var defaultCoefficient = RandomUtils.nextDouble(0.01, 1);
        var firstThreshold = createRiskTypeThreshold(sumThreshold);
        var secondThreshold = createRiskTypeThreshold(sumThreshold * 2);
        var doubleThresholdRiskType = new DoubleThresholdRiskType(defaultCoefficient, firstThreshold, secondThreshold);

        var lesserInsuredSum = sumThreshold - 1;
        var premium = doubleThresholdRiskType.calculate(new BigDecimal(lesserInsuredSum));
        var expectedPremium = calculate(lesserInsuredSum, defaultCoefficient);
        assertThat(premium, is(expectedPremium));
    }

    @Test
    public void calculateFirstThresholdCoefficientTest() {
        var sumThreshold = RandomUtils.nextInt(1, 100);
        var defaultCoefficient = RandomUtils.nextDouble(0.01, 1);
        var firstThreshold = createRiskTypeThreshold(sumThreshold);
        var secondThreshold = createRiskTypeThreshold(sumThreshold * 2);
        var doubleThresholdRiskType = new DoubleThresholdRiskType(defaultCoefficient, firstThreshold, secondThreshold);

        var greaterThenFirstThresholdInsuredSum = sumThreshold + 1;
        var premium = doubleThresholdRiskType.calculate(new BigDecimal(greaterThenFirstThresholdInsuredSum));
        var expectedPremium = calculate(greaterThenFirstThresholdInsuredSum, firstThreshold.getThresholdCoefficient());
        assertThat(premium, is(expectedPremium));
    }

    @Test
    public void calculateSecondThresholdCoefficientTest() {
        var sumThreshold = RandomUtils.nextInt(1, 100);
        var defaultCoefficient = RandomUtils.nextDouble(0.01, 1);
        var firstThreshold = createRiskTypeThreshold(sumThreshold);
        var secondThreshold = createRiskTypeThreshold(sumThreshold * 2);
        var doubleThresholdRiskType = new DoubleThresholdRiskType(defaultCoefficient, firstThreshold, secondThreshold);

        var greaterThenSecondThresholdInsuredSum = sumThreshold * 3;
        var premium = doubleThresholdRiskType.calculate(new BigDecimal(greaterThenSecondThresholdInsuredSum));
        var expectedPremium = calculate(greaterThenSecondThresholdInsuredSum, secondThreshold.getThresholdCoefficient());
        assertThat(premium, is(expectedPremium));
    }

    @Test
    public void createDoubleThresholdRiskTypeWithSecondThresholdEqualsToFirstOneTest() {
        var sumThreshold = RandomUtils.nextInt(1, 100);
        var defaultCoefficient = RandomUtils.nextDouble(0.01, 1);
        var thresholdCoefficient = RandomUtils.nextDouble(0.01, 1);
        assertThrows(IllegalArgumentException.class, () -> new DoubleThresholdRiskType(defaultCoefficient,
                createExclusiveThreshold(sumThreshold, thresholdCoefficient),
                createExclusiveThreshold(sumThreshold, thresholdCoefficient)));
    }

    @Test
    public void createDoubleThresholdRiskTypeWithSecondThresholdLesserThenFirstOneTest() {
        var sumThreshold = RandomUtils.nextInt(1, 100);
        var defaultCoefficient = RandomUtils.nextDouble(0.01, 1);
        var thresholdCoefficient = RandomUtils.nextDouble(0.01, 1);
        assertThrows(IllegalArgumentException.class, () -> new DoubleThresholdRiskType(defaultCoefficient,
                createExclusiveThreshold(sumThreshold, thresholdCoefficient),
                createExclusiveThreshold(sumThreshold - 1, thresholdCoefficient)));
    }

    private BigDecimal calculate(int insuredSum, double coefficient) {
        return new BigDecimal(insuredSum).multiply(new BigDecimal(coefficient));
    }
}
