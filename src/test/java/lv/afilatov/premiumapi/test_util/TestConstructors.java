package lv.afilatov.premiumapi.test_util;

import static lv.afilatov.premiumapi.domain.enums.RiskType.FIRE;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import lv.afilatov.premiumapi.domain.enums.PolicyStatus;
import lv.afilatov.premiumapi.domain.enums.RiskType;
import lv.afilatov.premiumapi.domain.model.Policy;
import lv.afilatov.premiumapi.domain.model.PolicyObject;
import lv.afilatov.premiumapi.domain.model.PolicySubObject;

public class TestConstructors {

    public static Policy createPolicy() {
        var policySubObject = createPolicySubObject(FIRE);
        var policyObject = createPolicyObject(List.of(policySubObject));
        return createPolicyWithoutObjects().setPolicyObjects(List.of(policyObject));
    }

    public static Policy createPolicy(List<PolicyObject> policyObjects) {
        return createPolicyWithoutObjects().setPolicyObjects(policyObjects);
    }

    public static Policy createPolicyWithoutObjects() {
        return new Policy()
                .setPolicyNumber(RandomStringUtils.randomAlphanumeric(8))
                .setPolicyStatus(PolicyStatus.REGISTERED);
    }

    public static PolicyObject createPolicyObject(List<PolicySubObject> policySubObjects) {
        return createPolicyObjectWithoutSubObjects().setPolicySubObjects(policySubObjects);
    }

    public static PolicyObject createPolicyObjectWithoutSubObjects() {
        return new PolicyObject().setName(RandomStringUtils.randomAlphanumeric(4));
    }

    public static PolicySubObject createPolicySubObject(RiskType riskType) {
        return createPolicySubObject(riskType, BigDecimal.valueOf(RandomUtils.nextDouble(0, 100)));
    }

    public static PolicySubObject createPolicySubObject(RiskType riskType, BigDecimal insuredSum) {
        return new PolicySubObject()
                .setName(RandomStringUtils.randomAlphanumeric(3))
                .setInsuredSum(insuredSum)
                .setRiskType(riskType);
    }
}
