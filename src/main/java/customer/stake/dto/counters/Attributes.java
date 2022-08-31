package customer.stake.dto.counters;

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
public class Attributes {

    private List<Double> payin = null;
    private List<Double> payinBet = null;
    private List<Double> payinCasino = null;
    private List<Double> lossBet = null;
    private List<Double> lossStake = null;
    private List<Double> stake = null;
    private List<Double> stakeBet = null;

    public Attributes(List<Double> payin, List<Double> payinCasino) {
        super();
        this.payin = payin;
        this.payinCasino = payinCasino;
    }

    public Attributes(List<Double> payin) {
        this.payin = payin;
    }

    public Attributes(List<Double> payin, List<Double> payinBet, List<Double> payinCasino, List<Double> lossBet,
                      List<Double> lossStake, List<Double> stake, List<Double> stakeBet) {
        this.payin = payin;
        this.payinBet = payinBet;
        this.payinCasino = payinCasino;
        this.lossBet = lossBet;
        this.lossStake = lossStake;
        this.stake = stake;
        this.stakeBet = stakeBet;
    }

    public List<Double> getAtribute(CounterType type) {
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
