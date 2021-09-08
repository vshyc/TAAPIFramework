package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;


public enum LimitTypeEnum {
    DEPOSIT("deposit"),
    TURNOVER("turnover"),
    LOSS("loss");


    private String type;

    LimitTypeEnum(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }


}
