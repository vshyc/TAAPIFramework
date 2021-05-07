package customer.stake.pojo.counters;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CustomerFiguresRequest {

    private Customer customer;
    private String label;
    private List<Attribute> attributes = null;

    public CustomerFiguresRequest(Customer customer, String label, List<Attribute> attributes) {
        super();
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }


}