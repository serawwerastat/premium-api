package lv.afilatov.premiumapi.domain.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lv.afilatov.premiumapi.domain.enums.PolicyStatus;

public class Policy {

    private String policyNumber;
    private PolicyStatus policyStatus;
    private List<PolicyObject> policyObjects = new ArrayList<>();

    public String getPolicyNumber() {
        return policyNumber;
    }

    public Policy setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
        return this;
    }

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public Policy setPolicyStatus(PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
        return this;
    }

    public List<PolicyObject> getPolicyObjects() {
        return policyObjects;
    }

    public Policy setPolicyObjects(List<PolicyObject> policyObjects) {
        this.policyObjects = policyObjects;
        return this;
    }

    public List<PolicySubObject> getPolicySubObjects() {
        return policyObjects.stream()
                .flatMap(policyObject -> policyObject.getPolicySubObjects().stream())
                .collect(toList());
    }

    @Override public String toString() {
        return new ToStringBuilder(this)
                .append("policyNumber", policyNumber)
                .append("policyStatus", policyStatus)
                .append("policyObjects", policyObjects)
                .toString();
    }
}
