package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Label {

    ALL("ALL"),
    TIPICO("TIPICO"),
    GAMES("GAMES"),
    MYSINO("MYSINO"),
    GLOBAL("GLOBAL"),
    all("all"),
    tipico("tipico"),
    games("games"),
    mysino("mysino"),
    All("All"),
    Tipico("Tipico"),
    Games("Games"),
    Mysino("Mysino"),
    Original_Wetten("original-wetten"),
    Wettbruder("wettbruder"),
    Wettenmarkt("wettenmarkt");

    private final String label;

    Label(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
