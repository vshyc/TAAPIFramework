package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CounterType {

    PAYIN("payin"),
    PAYIN_BET("payinBet"),
    PAYIN_CASINO("payinCasino"),
    LOSS_BET("lossBet"),
    LOSS_STAKE("lossStake"),
    STAKE("stake"),
    STAKE_BET("stakeBet");

    private String type;

    CounterType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }


}