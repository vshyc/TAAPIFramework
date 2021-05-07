package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LabelEnums {

    ALL("all"),
    TIPICO("tipico"),
    GAMES("games"),
    MYSINO("mysino");

    private String label;

    LabelEnums(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

}
