package customer.stake.enums;

public enum CountryCodes {
    GERMANY("DE"),
    AUSTRIA("AT"),
    MALTA("MT");

    private String countryCode;

    CountryCodes(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

}
