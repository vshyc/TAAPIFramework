package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CounterType {

    PAYIN("payin"),
    PAYINS_BETTING("payinsBetting"),
    PAYINS_GAMBLING("payinsGambling"),
    LOSSING_BETS("lossingBets"),
    LOSSING_STAKES("lossingStakes"),
    STAKES("stakes"),
    STAKES_BETTING("stakesBetting");

    private String type;

    CounterType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }


}