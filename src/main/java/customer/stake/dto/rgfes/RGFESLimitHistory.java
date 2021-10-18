package customer.stake.dto.rgfes;

import customer.stake.enums.IntervalEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.LimitTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESLimitHistory {

    private LimitTypeEnum limitType;
    private Boolean requested;
    private Double value;
    private IntervalEnum interval;
    private Date updatedDate;
    private String product;
    private LabelEnums label;

    public RGFESLimitHistory(LimitTypeEnum limitType, Boolean requested, Double value, IntervalEnum interval, Date updatedDate, String product, LabelEnums label) {
        this.limitType = limitType;
        this.requested = requested;
        this.value = value;
        this.interval = interval;
        this.updatedDate = updatedDate;
        this.product = product;
        this.label = label;
    }
}
