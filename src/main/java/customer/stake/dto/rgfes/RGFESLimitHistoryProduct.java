package customer.stake.dto.rgfes;

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
public class RGFESLimitHistoryProduct {

    private List<RGFESLimitHistoryType> deposit;
    private List<RGFESLimitHistoryType> loss;
    private List<RGFESLimitHistoryType> turnover;

    public RGFESLimitHistoryProduct(List<RGFESLimitHistoryType> deposit, List<RGFESLimitHistoryType> loss, List<RGFESLimitHistoryType> turnover) {
        this.deposit = deposit;
        this.loss = loss;
        this.turnover = turnover;
    }

    public List<RGFESLimitHistoryType> getLimitHistoryType(LimitTypeEnum type) {
        if (type == LimitTypeEnum.DEPOSIT) {
            return deposit;
        } else if (type == LimitTypeEnum.LOSS) {
            return loss;
        } else if (type == LimitTypeEnum.TURNOVER) {
            return turnover;
        } else return null;
    }
}
