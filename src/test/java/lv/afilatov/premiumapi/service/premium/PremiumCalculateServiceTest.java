package lv.afilatov.premiumapi.service.premium;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static lv.afilatov.premiumapi.domain.enums.RiskType.FIRE;
import static lv.afilatov.premiumapi.domain.enums.RiskType.THEFT;
import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicy;
import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicyObject;
import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicySubObject;
import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicyWithoutObjects;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.afilatov.premiumapi.domain.enums.RiskType;
import lv.afilatov.premiumapi.domain.model.Policy;
import lv.afilatov.premiumapi.domain.model.PolicyObject;
import lv.afilatov.premiumapi.domain.model.PolicySubObject;

@ExtendWith(MockitoExtension.class)
public class PremiumCalculateServiceTest {

    private static final BigDecimal ZERO_PREMIUM = BigDecimal.ZERO.setScale(2);
    private final Map<RiskType, BigDecimal> insuredSumMappedByRiskType = new HashMap<>();

    @InjectMocks
    private PremiumCalculateService premiumCalculateService;

    @Test
    public void calculateTest() {
        var policy = setUpPolicy();
        var premium = premiumCalculateService.calculate(policy);
        var expectedPremium = getExpectedPremium();
        assertThat(premium, is(expectedPremium));
    }

    @Test
    public void calculateNoPolicySubObjectsTest() {
        var policy = setUpPolicy();
        policy.getPolicyObjects().forEach(obj -> obj.setPolicySubObjects(emptyList()));
        var premium = premiumCalculateService.calculate(policy);
        assertThat(premium, is(ZERO_PREMIUM));
    }

    @Test
    public void calculateNoPolicyObjectsTest() {
        var policy = createPolicyWithoutObjects();
        var premium = premiumCalculateService.calculate(policy);
        assertThat(premium, is(ZERO_PREMIUM));
    }

    @Test
    public void calculateMinimalFieldsTest() {
        var theftInsuredSum = BigDecimal.valueOf(RandomUtils.nextDouble(0, 100));
        insuredSumMappedByRiskType.put(THEFT, theftInsuredSum);
        var policySubObject = new PolicySubObject()
                .setInsuredSum(theftInsuredSum)
                .setRiskType(THEFT);
        var policyObject = new PolicyObject().setPolicySubObjects(singletonList(policySubObject));
        var policy = new Policy().setPolicyObjects(singletonList(policyObject));

        var expectedPremium = getExpectedPremium();
        var premium = premiumCalculateService.calculate(policy);
        assertThat(premium, is(expectedPremium));
    }

    private Policy setUpPolicy() {
        var fireInsuredSum1 = BigDecimal.valueOf(RandomUtils.nextDouble(0, 100));
        var theftInsuredSum1 = BigDecimal.valueOf(RandomUtils.nextDouble(0, 100));
        var policySubObj1_1 = createPolicySubObject(FIRE, fireInsuredSum1);
        var policySubObj1_2 = createPolicySubObject(THEFT, theftInsuredSum1);
        var policyObj1 = createPolicyObject(List.of(policySubObj1_1, policySubObj1_2));

        var fireInsuredSum2 = BigDecimal.valueOf(RandomUtils.nextDouble(0, 100));
        var theftInsuredSum2 = BigDecimal.valueOf(RandomUtils.nextDouble(0, 100));
        var policySubObj2_1 = createPolicySubObject(FIRE, fireInsuredSum2);
        var policySubObj2_2 = createPolicySubObject(THEFT, theftInsuredSum2);
        var policyObj2 = createPolicyObject(List.of(policySubObj2_1, policySubObj2_2));

        insuredSumMappedByRiskType.put(FIRE, fireInsuredSum1.add(fireInsuredSum2));
        insuredSumMappedByRiskType.put(THEFT, theftInsuredSum1.add(theftInsuredSum2));
        return createPolicy(List.of(policyObj1, policyObj2));
    }

    private BigDecimal getExpectedPremium() {
        var firePremium = calculatePremium(FIRE);
        var theftPremium = calculatePremium(THEFT);

        return firePremium.add(theftPremium).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePremium(RiskType riskType) {
        if (!insuredSumMappedByRiskType.containsKey(riskType)) {
            return ZERO_PREMIUM;
        }
        var insuredSum = insuredSumMappedByRiskType.get(riskType);
        return riskType.getCalculatableRiskType().calculate(insuredSum);
    }

}
