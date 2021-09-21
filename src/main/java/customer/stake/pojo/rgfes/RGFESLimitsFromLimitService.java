package customer.stake.pojo.rgfes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
