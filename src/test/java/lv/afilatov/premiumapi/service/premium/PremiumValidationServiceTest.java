package lv.afilatov.premiumapi.service.premium;

import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import lv.afilatov.premiumapi.domain.model.Policy;

@ExtendWith(MockitoExtension.class)
public class PremiumValidationServiceTest {

    @InjectMocks
    private PremiumValidationService premiumValidationService;

    @Test
    public void validatePolicyTest() {
        premiumValidationService.validatePolicy(new Policy());
    }

    @Test
    public void validatePolicyNullTest() {
        assertThrows(IllegalArgumentException.class, () -> premiumValidationService.validatePolicy(null));
    }

    @Test
    public void validateForPremiumCalculationTest() {
        var policy = createPolicy();
        premiumValidationService.validateForPremiumCalculation(policy);
    }

    @Test
    public void validateForPremiumCalculationNullEnsuredSumTest() {
        var policy = createPolicy();
        policy.getPolicySubObjects().forEach(subObject -> subObject.setInsuredSum(null));
        assertThrows(IllegalArgumentException.class, () -> premiumValidationService.validateForPremiumCalculation(policy));
    }
}
