package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Label {

    ALL("ALL");

    private final String label;

    Label(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
    
}
