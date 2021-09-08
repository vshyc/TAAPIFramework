package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IntervalEnum {
    MONTH("MONTH"),
    WEEK("WEEK"),
    DAY("DAY");

    private String interval;

    IntervalEnum(String interval) {
        this.interval = interval;
    }

    @JsonValue
    public String getInterval() {
        return interval;
    }
}
