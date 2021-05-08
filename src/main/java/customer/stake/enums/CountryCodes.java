package customer.stake.enums;

import lombok.Getter;

@Getter
public enum CountryCodes {
    GERMANY("DE"),
    AUSTRIA("AT"),
    MALTA("MT");

    private String countryCode;

    CountryCodes(String countryCode) {
        this.countryCode = countryCode;
    }


}
