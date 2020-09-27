package lv.afilatov.premiumapi.domain.model.risk_type;

import static lv.afilatov.premiumapi.domain.model.risk_type.RiskTypeThreshold.createExclusiveThreshold;
import static lv.afilatov.premiumapi.domain.model.risk_type.RiskTypeThreshold.createInclusiveThreshold;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

public class RiskTypeThresholdTest {

    @Test
    public void exceedExclusiveThresholdGreaterTest() {
        var sumThreshold = RandomUtils.nextInt();
        var thresholdCoefficient = RandomUtils.nextDouble();
        var threshold = createExclusiveThreshold(sumThreshold, thresholdCoefficient);
        assertThat(threshold.exceedThreshold(new BigDecimal(sumThreshold + 1)), is(true));
    }

    @Test
    public void exceedExclusiveThresholdLesserTest() {
        var sumThreshold = RandomUtils.nextInt();
        var thresholdCoefficient = RandomUtils.nextDouble();
        var threshold = createExclusiveThreshold(sumThreshold, thresholdCoefficient);
        assertThat(threshold.exceedThreshold(new BigDecimal(sumThreshold - 1)), is(false));
    }

    @Test
    public void exceedExclusiveThresholdEqualTest() {
        var sumThreshold = RandomUtils.nextInt();
        var thresholdCoefficient = RandomUtils.nextDouble();
        var threshold = createExclusiveThreshold(sumThreshold, thresholdCoefficient);
        assertThat(threshold.exceedThreshold(new BigDecimal(sumThreshold)), is(false));
    }

    @Test
    public void exceedInclusiveThresholdGreaterTest() {
        var sumThreshold = RandomUtils.nextInt();
        var thresholdCoefficient = RandomUtils.nextDouble();
        var threshold = createInclusiveThreshold(sumThreshold, thresholdCoefficient);
        assertThat(threshold.exceedThreshold(new BigDecimal(sumThreshold + 1)), is(true));
    }

    @Test
    public void exceedInclusiveThresholdLesserTest() {
        var sumThreshold = RandomUtils.nextInt();
        var thresholdCoefficient = RandomUtils.nextDouble();
        var threshold = createInclusiveThreshold(sumThreshold, thresholdCoefficient);
        assertThat(threshold.exceedThreshold(new BigDecimal(sumThreshold - 1)), is(false));
    }

    @Test
    public void exceedInclusiveThresholdEqualTest() {
        var sumThreshold = RandomUtils.nextInt();
        var thresholdCoefficient = RandomUtils.nextDouble();
        var threshold = createInclusiveThreshold(sumThreshold, thresholdCoefficient);
        assertThat(threshold.exceedThreshold(new BigDecimal(sumThreshold)), is(true));
    }
}
