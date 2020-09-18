package lv.afilatov.premiumapi;

import static lv.afilatov.premiumapi.domain.enums.RiskType.FIRE;
import static lv.afilatov.premiumapi.domain.enums.RiskType.THEFT;
import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicy;
import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicyObject;
import static lv.afilatov.premiumapi.test_util.TestConstructors.createPolicySubObject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PremiumApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void calculateSubObjectsHasDifferentRiskTypesTest() throws Exception {
        var expectedResponse = "2.28";

        var policySubObj1_1 = createPolicySubObject(FIRE, new BigDecimal(100));
        var policySubObj1_2 = createPolicySubObject(THEFT, new BigDecimal(8));
        var policyObj1 = createPolicyObject(List.of(policySubObj1_1, policySubObj1_2));
        var policy = createPolicy(List.of(policyObj1));

        var policyJson = objectMapper.writeValueAsString(policy);
        System.out.println(policyJson);
        var result = mockMvc.perform(calculate(policyJson))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();
        assertThat(response, is(expectedResponse));
    }

    @Test
    void calculateMultipleSubObjectsHasSameRiskTypesTest() throws Exception {
        var expectedResponse = "17.13";

        var policySubObj1_1 = createPolicySubObject(FIRE, new BigDecimal(200));
        var policySubObj1_2 = createPolicySubObject(THEFT, new BigDecimal(100));
        var policyObj1 = createPolicyObject(List.of(policySubObj1_1, policySubObj1_2));

        var policySubObj2_1 = createPolicySubObject(FIRE, new BigDecimal(300));
        var policySubObj2_2 = createPolicySubObject(THEFT, new BigDecimal("2.51"));
        var policyObj2 = createPolicyObject(List.of(policySubObj2_1, policySubObj2_2));

        var policy = createPolicy(List.of(policyObj1, policyObj2));

        var policyJson = objectMapper.writeValueAsString(policy);
        var result = mockMvc.perform(calculate(policyJson))
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();
        assertThat(response, is(expectedResponse));
    }

    private MockHttpServletRequestBuilder calculate(String policyJson) {
        return post("/premium/calculate", 42L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(policyJson);
    }

}
