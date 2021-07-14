package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LicenceRegion {

    GERMANY("DE"),
    AUSTRIA("AT"),
    SH("DE_SH"),
    COM("COM");

    private String licenceRegion;

    LicenceRegion(String licenceRegion) {
        this.licenceRegion = licenceRegion;
    }


    @JsonValue
    public String getLicenceRegion() {
        return licenceRegion;
    }
}
