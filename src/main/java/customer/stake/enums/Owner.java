package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Owner {
    AML("AML"),
    PERSONAL("PERSONAL"),
    IMPOSED("IMPOSED");

    private String owner;

    Owner(String owner) {
        this.owner = owner;
    }

    @JsonValue
    public String getOwner() {
        return owner;
    }
}
