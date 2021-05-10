package customer.stake.pojo.limits;

import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import customer.stake.enums.TypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class LimitCreationData {

    private TypeEnum type;
    private OwnerEnum owner;
    private LabelEnums label;
    private String product;
    private Double value;
    private String interval;

    public LimitCreationData(TypeEnum type, OwnerEnum owner, LabelEnums label, String product, Double value, String interval) {
        this.type = type;
        this.owner = owner;
        this.label = label;
        this.product = product;
        this.value = value;
        this.interval = interval;
    }
}
