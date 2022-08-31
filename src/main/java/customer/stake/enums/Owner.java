package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Owner {
    AML("AML"),
    PERSONAL("PERSONAL"),
    FORCED("FORCED");

    private String owner;

    Owner(String owner) {
        this.owner = owner;
    }

    @JsonValue
    public String getOwner() {
        return owner;
    }
}
