package lv.afilatov.premiumapi.enums;

import static lv.afilatov.premiumapi.domain.enums.RiskType.FIRE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class RiskTypeTest {

    @Test
    public void getCoefficientBasedOnLesserThenThresholdTest() {
        var threshold = FIRE.getSumThreshold();
        var lesserThenThresholdInsuredSum = threshold.subtract(new BigDecimal(1));
        var lesserThresholdCoefficient = FIRE.getCoefficientBasedOn(lesserThenThresholdInsuredSum);
        assertThat(lesserThresholdCoefficient, is(FIRE.getCoefficient()));
    }

    @Test
    public void getCoefficientBasedOnGreaterThenThresholdTest() {
        var threshold = FIRE.getSumThreshold();
        var greaterThresholdCoefficient = FIRE.getCoefficientBasedOn(threshold.add(new BigDecimal(1)));
        assertThat(greaterThresholdCoefficient, is(FIRE.getThresholdCoefficient()));
    }

    @Test
    public void getCoefficientBasedOnEqualThresholdTest() {
        var threshold = FIRE.getSumThreshold();
        var equalCoefficient = FIRE.getCoefficientBasedOn(threshold);
        if (FIRE.isThresholdInclusive()) {
            assertThat(equalCoefficient, is(FIRE.getThresholdCoefficient()));
        } else {
            assertThat(equalCoefficient, is(FIRE.getCoefficient()));
        }
    }
}
