package customer.stake.pojo.limits;

import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class LimitCreationData {

    private String type;
    private Enum<OwnerEnum> owner;
    private Enum<LabelEnums> label;
    private String product;
    private Double value;
    private String interval;

    public LimitCreationData(String type, Enum<OwnerEnum> owner, Enum<LabelEnums> label, String product, Double value, String interval) {
        this.type = type;
        this.owner = owner;
        this.label = label;
        this.product = product;
        this.value = value;
        this.interval = interval;
    }
}
