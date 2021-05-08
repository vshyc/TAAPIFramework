package customer.stake.enums;

import lombok.Getter;

@Getter
public enum LabelEnums {

    ALL("ALL"),
    TIPICO("TIPICO"),
    GAMES("GAMES"),
    MYSINO("MYSINO");

    private String label;

    LabelEnums(String label) {
        this.label = label;
    }


}
