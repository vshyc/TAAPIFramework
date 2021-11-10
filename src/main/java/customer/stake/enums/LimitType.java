package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;


public enum LimitType {
    DEPOSIT("deposit"),
    TURNOVER("turnover"),
    LOSS("loss");


    private String type;

    LimitType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }


}
