package lv.afilatov.premiumapi.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lv.afilatov.premiumapi.domain.model.Policy;
import lv.afilatov.premiumapi.service.premium.PremiumService;

@RestController
@RequestMapping("/premium")
public class PremiumCalculatorController {

    @Autowired
    private PremiumService premiumService;

    @PostMapping("calculate")
    public BigDecimal calculate(@RequestBody Policy policy) {
        return premiumService.calculatePremium(policy);
    }
}
