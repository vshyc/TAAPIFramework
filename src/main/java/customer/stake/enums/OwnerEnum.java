package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OwnerEnum {
    AML("AML"),
    PERSONAL("PERSONAL"),
    IMPOSED("IMPOSED");

    private String owner;

    OwnerEnum(String owner) {
        this.owner = owner;
    }

    @JsonValue
    public String getOwner() {
        return owner;
    }
}
