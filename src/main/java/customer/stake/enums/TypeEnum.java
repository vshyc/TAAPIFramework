package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;


public enum TypeEnum {
    DEPOSIT("deposit"),
    TURNOVER("turnover"),
    LOSS("loss"),
    PAYIN("payin"),
    PAYIN_BET("payinBet"),
    PAYIN_CASINO("payinCasino"),
    LOSS_BET("lossBet"),
    LOSS_STAKE("lossStake"),
    STAKE("stake"),
    STAKE_BET("stakeBet");

    private String type;

    TypeEnum(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }


}
