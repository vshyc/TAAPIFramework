package customer.stake.pojo.rgfes;

import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.LimitTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESLimitsFromLimitService {

    private RGFESLimitFromLimitService deposit;
    private RGFESLimitFromLimitService turnover;
    private RGFESLimitFromLimitService loss;

    public RGFESLimitsFromLimitService(RGFESLimitFromLimitService deposit, RGFESLimitFromLimitService turnover, RGFESLimitFromLimitService loss) {
        this.deposit = deposit;
        this.turnover = turnover;
        this.loss = loss;
    }
    public RGFESLimitFromLimitService getLimitType(LimitTypeEnum limitType) {
        if(limitType == LimitTypeEnum.DEPOSIT) {
            return deposit;
        }
        else if(limitType == LimitTypeEnum.TURNOVER){
            return turnover;
        }
        else if(limitType == LimitTypeEnum.LOSS){
            return loss;
        }
        else return null;
    }
}
