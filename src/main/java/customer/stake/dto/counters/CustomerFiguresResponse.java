package customer.stake.dto.counters;

import customer.stake.enums.Label;
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
    private Label label;
    private Attributes attributes;

    public CustomerFiguresResponse(Customer customer, Label label, Attributes attributes) {
        super();
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }

}