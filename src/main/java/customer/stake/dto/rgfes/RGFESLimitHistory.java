package customer.stake.dto.rgfes;

import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class RGFESLimitHistory {

    private LimitType limitType;
    private Boolean requested;
    private Double value;
    private Interval interval;
    private Date updatedDate;
    private String product;
    private Label label;

    public RGFESLimitHistory(LimitType limitType, Boolean requested, Double value, Interval interval, Date updatedDate,
            Product product, Label label) {
        this.limitType = limitType;
        this.requested = requested;
        this.value = value;
        this.interval = interval;
        this.updatedDate = updatedDate;
        this.product = product.name();
        this.label = label;
    }
}
