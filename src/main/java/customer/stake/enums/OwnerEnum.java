package customer.stake.enums;

import lombok.Getter;

@Getter
public enum OwnerEnum {
    AML("AML"),
    PERSONAL("PERSONAL"),
    IMPOSED("IMPOSED");

    OwnerEnum(String owner) {
    }

}
