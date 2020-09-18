package lv.afilatov.premiumapi.service.premium;

import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Component;

import lv.afilatov.premiumapi.domain.model.Policy;

@Component
public class PremiumValidationService {

    void validateForPremiumCalculation(Policy policy) {
        validatePolicy(policy);
        var subObjectsWithNullInsuredSum = policy.getPolicySubObjects().stream()
                .filter(subObject -> subObject.getInsuredSum() == null)
                .collect(toList());
        if (!subObjectsWithNullInsuredSum.isEmpty()) {
            var exceptionMsg = "One or more received policy sub objects had null insured sum. Invalid policy sub objects: ";
            throw new IllegalArgumentException(exceptionMsg + subObjectsWithNullInsuredSum);
        }
    }

    void validatePolicy(Policy policy) {
        if (policy == null) {
            throw new IllegalArgumentException("Received null policy");
        }
    }
}
