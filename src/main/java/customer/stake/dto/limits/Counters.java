package customer.stake.dto.limits;

import customer.stake.enums.CounterTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class Counters {

    private LimitValidator payin = null;
    private LimitValidator payinBet = null;
    private LimitValidator payinCasino = null;
    private LimitValidator lossBet = null;
    private LimitValidator lossStake = null;
    private LimitValidator stake = null;
    private LimitValidator stakeBet = null;

    public Counters(LimitValidator payin, LimitValidator payinBet, LimitValidator payinCasino, LimitValidator lossBet,
                    LimitValidator lossStake, LimitValidator stake, LimitValidator stakeBet) {
        this.payin = payin;
        this.payinBet = payinBet;
        this.payinCasino = payinCasino;
        this.lossBet = lossBet;
        this.lossStake = lossStake;
        this.stake = stake;
        this.stakeBet = stakeBet;
    }

    public LimitValidator getLimitForCounter(CounterTypeEnum type) {
        if (type == CounterTypeEnum.PAYIN) {
            return payin;
        } else if (type == CounterTypeEnum.PAYIN_BET) {
            return payinBet;
        } else if (type == CounterTypeEnum.PAYIN_CASINO) {
            return payinCasino;
        } else if (type == CounterTypeEnum.LOSS_BET) {
            return lossBet;
        } else if (type == CounterTypeEnum.LOSS_STAKE) {
            return lossStake;
        } else if (type == CounterTypeEnum.STAKE) {
            return stake;
        } else if (type == CounterTypeEnum.STAKE_BET) {
            return stakeBet;
        } else return null;
    }
}
