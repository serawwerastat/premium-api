package lv.afilatov.premiumapi.service.premium;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collector;

import org.springframework.stereotype.Component;

import lv.afilatov.premiumapi.domain.enums.RiskType;
import lv.afilatov.premiumapi.domain.model.Policy;
import lv.afilatov.premiumapi.domain.model.PolicySubObject;

@Component
public class PremiumCalculateService {

    BigDecimal calculate(Policy policy) {
        var insuredSumMappedByRiskType = policy.getPolicySubObjects().stream()
                .collect(groupingBy(PolicySubObject::getRiskType, getCollectorToSumInsurance()));
        return calculatePremiums(insuredSumMappedByRiskType);
    }

    private Collector<PolicySubObject, ?, BigDecimal> getCollectorToSumInsurance() {
        return reducing(BigDecimal.ZERO, PolicySubObject::getInsuredSum, BigDecimal::add);
    }

    private BigDecimal calculatePremiums(Map<RiskType, BigDecimal> insuredSumMappedByRiskType) {
        var premium = BigDecimal.ZERO;
        premium = insuredSumMappedByRiskType.entrySet().stream()
                .map(this::calculatePremium)
                .reduce(premium, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        return premium;
    }

    private BigDecimal calculatePremium(Map.Entry<RiskType, BigDecimal> riskTypeInsuredSumEntry) {
        var riskType = riskTypeInsuredSumEntry.getKey();
        var insuredSum = riskTypeInsuredSumEntry.getValue();
        var coefficient = riskType.getCoefficientBasedOn(insuredSum);
        return insuredSum.multiply(new BigDecimal(coefficient));
    }
}
