package customer.stake.pojo.counters;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CustomerFiguresResponse {

    private Customer customer;
    private String label;
    private Attributes attributes;

    public CustomerFiguresResponse(Customer customer, String label, Attributes attributes) {
        super();
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }

}