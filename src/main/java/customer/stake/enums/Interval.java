package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Interval {
    MONTH("MONTH"),
    WEEK("WEEK"),
    DAY("DAY");

    private String interval;

    Interval(String interval) {
        this.interval = interval;
    }

    @JsonValue
    public String getInterval() {
        return interval;
    }
}
