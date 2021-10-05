package customer.stake.dto.limits;

import customer.stake.enums.IntervalEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import customer.stake.enums.LimitTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class LimitCreationData {

    private LimitTypeEnum type;
    private OwnerEnum owner;
    private LabelEnums label;
    private String product;
    private Double value;
    private IntervalEnum interval;

    public LimitCreationData(LimitTypeEnum type, OwnerEnum owner, LabelEnums label, String product, Double value, IntervalEnum interval) {
        this.type = type;
        this.owner = owner;
        this.label = label;
        this.product = product;
        this.value = value;
        this.interval = interval;
    }
}
