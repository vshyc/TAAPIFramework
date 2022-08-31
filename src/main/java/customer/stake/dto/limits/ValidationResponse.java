package customer.stake.dto.limits;

import customer.stake.enums.CounterType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ValidationResponse {

    private List<LimitValidator> payin = null;
    private List<LimitValidator> payinBet = null;
    private List<LimitValidator> payinCasino = null;
    private List<LimitValidator> lossBet = null;
    private List<LimitValidator> lossStake = null;
    private List<LimitValidator> stake = null;
    private List<LimitValidator> stakeBet = null;

    public ValidationResponse(List<LimitValidator> payin, List<LimitValidator> payinBet,
                              List<LimitValidator> payinCasino, List<LimitValidator> lossBet,
                              List<LimitValidator> lossStake, List<LimitValidator> stake, List<LimitValidator> stakeBet) {
        this.payin = payin;
        this.payinBet = payinBet;
        this.payinCasino = payinCasino;
        this.lossBet = lossBet;
        this.lossStake = lossStake;
        this.stake = stake;
        this.stakeBet = stakeBet;
    }

    public List<LimitValidator> getLimitForCounter(CounterType type) {
        if (type == CounterType.PAYIN) {
            return payin;
        } else if (type == CounterType.PAYINS_BETTING) {
            return payinBet;
        } else if (type == CounterType.PAYINS_GAMBLING) {
            return payinCasino;
        } else if (type == CounterType.LOSSING_BETS) {
            return lossBet;
        } else if (type == CounterType.LOSSING_STAKES) {
            return lossStake;
        } else if (type == CounterType.STAKES) {
            return stake;
        } else if (type == CounterType.STAKES_BETTING) {
            return stakeBet;
        } else return null;
    }
}
