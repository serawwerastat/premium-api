package lv.afilatov.premiumapi.service.premium;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lv.afilatov.premiumapi.domain.model.Policy;

@Component
public class PremiumService {

    @Autowired
    private PremiumCalculateService premiumCalculateService;
    @Autowired
    private PremiumValidationService premiumValidationService;

    public BigDecimal calculatePremium(Policy policy) {
        premiumValidationService.validateForPremiumCalculation(policy);
        return premiumCalculateService.calculate(policy);
    }
}
