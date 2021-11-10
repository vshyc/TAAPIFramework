package customer.stake.dto.counters;

import customer.stake.enums.Label;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PostCountersRequest {

    private Customer customer;
    private Label label;
    private List<Attribute> attributes = null;

    public PostCountersRequest(Customer customer, Label label, List<Attribute> attributes) {
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }
}

