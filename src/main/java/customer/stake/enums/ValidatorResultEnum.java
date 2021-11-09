package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ValidatorResultEnum {
    VALID_WITH_NO_VALIDATOR_DEFINED("VALID_WITH_NO_VALIDATOR_DEFINED"),
    LIMIT_EXCEED("LIMIT_EXCEED"),
    VALID("VALID"),
    LIMIT_EXACTLY_MATCHED("LIMIT_EXACTLY_MATCHED"),
    VALID_WITH_NO_LIMIT_DEFINED("VALID_WITH_NO_LIMIT_DEFINED");

    private String validatorResult;

    ValidatorResultEnum(String validatorResult){
        this.validatorResult=validatorResult;
    }

    @JsonValue
    public String getValidatorResult() {
        return validatorResult;
    }
}
