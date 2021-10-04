package customer.stake.pojo.rgfes;

import customer.stake.enums.LimitTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESLimit {

    private RGFESLimitValues deposit;
    private RGFESLimitValues loss;
    private RGFESLimitValues turnover;

    public RGFESLimit(RGFESLimitValues deposit, RGFESLimitValues loss, RGFESLimitValues turnover) {
        this.deposit = deposit;
        this.loss = loss;
        this.turnover = turnover;
    }

    public RGFESLimitValues getLimit(LimitTypeEnum type) {
        if (type == LimitTypeEnum.DEPOSIT) {
            return deposit;
        } else if (type == LimitTypeEnum.LOSS) {
            return loss;
        } else if (type == LimitTypeEnum.TURNOVER) {
            return turnover;
        } else return null;
    }
}
