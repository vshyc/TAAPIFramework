package customer.stake.dto.limits;

import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.Owner;
import customer.stake.enums.LimitType;
import customer.stake.enums.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class LimitCreationData {

    private LimitType type;
    private Owner owner;
    private Label label;
    private Product product;
    private Double value;
    private Interval interval;

    public LimitCreationData(LimitType type, Owner owner, Label label, Product product, Double value, Interval interval) {
        this.type = type;
        this.owner = owner;
        this.label = label;
        this.product = product;
        this.value = value;
        this.interval = interval;
    }
}
