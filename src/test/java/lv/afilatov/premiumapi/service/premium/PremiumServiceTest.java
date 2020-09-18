package lv.afilatov.premiumapi.service.premium;

import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicyWithoutObjects;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PremiumServiceTest {

    @InjectMocks
    private PremiumService premiumService;
    @Mock
    private PremiumCalculateService premiumCalculateService;
    @Mock
    private PremiumValidationService premiumValidationService;

    @Test
    public void calculatePremiumTest() {
        var policy = createPolicyWithoutObjects();
        var mockPremium = new BigDecimal(1);
        when(premiumCalculateService.calculate(policy)).thenReturn(mockPremium);
        var premium = premiumService.calculatePremium(policy);
        assertThat(premium, is(mockPremium));
        verify(premiumValidationService).validateForPremiumCalculation(policy);
    }
}
