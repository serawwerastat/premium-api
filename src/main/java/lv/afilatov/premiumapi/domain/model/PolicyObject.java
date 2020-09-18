package lv.afilatov.premiumapi.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PolicyObject {

    private String name;
    private List<PolicySubObject> policySubObjects = new ArrayList<>();

    public String getName() {
        return name;
    }

    public PolicyObject setName(String name) {
        this.name = name;
        return this;
    }

    public List<PolicySubObject> getPolicySubObjects() {
        return policySubObjects;
    }

    public PolicyObject setPolicySubObjects(List<PolicySubObject> policySubObjects) {
        this.policySubObjects = policySubObjects;
        return this;
    }

    @Override public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("policySubObjects", policySubObjects)
                .toString();
    }
}
