package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LabelEnums {

    ALL("ALL"),
    TIPICO("TIPICO"),
    GAMES("GAMES"),
    MYSINO("MYSINO");

    private String label;

    LabelEnums(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

}
