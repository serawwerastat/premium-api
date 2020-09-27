package lv.afilatov.premiumapi.domain.model.risk_type;

import static lv.afilatov.premiumapi.test_util.TestConstructors.createRiskTypeThreshold;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

public class SingleThresholdRiskTypeTest {

    @Test
    public void calculateDefaultCoefficientTest() {
        var sumThreshold = RandomUtils.nextInt(1, 100);
        var defaultCoefficient = RandomUtils.nextDouble(0.01, 1);
        var threshold = createRiskTypeThreshold(sumThreshold);
        var singleThresholdRiskType = new SingleThresholdRiskType(defaultCoefficient, threshold);

        var lesserInsuredSum = sumThreshold - 1;
        var premium = singleThresholdRiskType.calculate(new BigDecimal(lesserInsuredSum));
        var expectedPremium = calculate(lesserInsuredSum, defaultCoefficient);
        assertThat(premium, is(expectedPremium));
    }

    @Test
    public void calculateThresholdCoefficientTest() {
        var sumThreshold = RandomUtils.nextInt(1, 100);
        var defaultCoefficient = RandomUtils.nextDouble(0.01, 1);
        var threshold = createRiskTypeThreshold(sumThreshold);
        var singleThresholdRiskType = new SingleThresholdRiskType(defaultCoefficient, threshold);

        var greaterInsuredSum = sumThreshold + 1;
        var premium = singleThresholdRiskType.calculate(new BigDecimal(greaterInsuredSum));
        var expectedPremium = calculate(greaterInsuredSum, threshold.getThresholdCoefficient());
        assertThat(premium, is(expectedPremium));
    }

    private BigDecimal calculate(int insuredSum, double coefficient) {
        return new BigDecimal(insuredSum).multiply(new BigDecimal(coefficient));
    }

}
