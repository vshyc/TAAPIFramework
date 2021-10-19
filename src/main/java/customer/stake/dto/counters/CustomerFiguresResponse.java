package customer.stake.dto.counters;

import customer.stake.enums.LabelEnums;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class CustomerFiguresResponse {

    private Customer customer;
    private LabelEnums label;
    private Attributes attributes;

    public CustomerFiguresResponse(Customer customer, LabelEnums label, Attributes attributes) {
        super();
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }

}