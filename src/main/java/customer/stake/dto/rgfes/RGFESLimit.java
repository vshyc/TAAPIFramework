package customer.stake.dto.rgfes;

import customer.stake.enums.LimitType;
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

    public RGFESLimitValues getLimit(LimitType type) {
        if (type == LimitType.DEPOSIT) {
            return deposit;
        } else if (type == LimitType.LOSS) {
            return loss;
        } else if (type == LimitType.TURNOVER) {
            return turnover;
        } else return null;
    }
}
