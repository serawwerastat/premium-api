package lv.afilatov.premiumapi.domain.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lv.afilatov.premiumapi.domain.enums.RiskType;

public class PolicySubObject {

    private String name;
    private BigDecimal insuredSum;
    private RiskType riskType;

    public String getName() {
        return name;
    }

    public PolicySubObject setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getInsuredSum() {
        return insuredSum;
    }

    public PolicySubObject setInsuredSum(BigDecimal insuredSum) {
        this.insuredSum = insuredSum;
        return this;
    }

    public RiskType getRiskType() {
        return riskType;
    }

    public PolicySubObject setRiskType(RiskType riskType) {
        this.riskType = riskType;
        return this;
    }

    @Override public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("insuredSum", insuredSum)
                .append("riskType", riskType)
                .toString();
    }

}
