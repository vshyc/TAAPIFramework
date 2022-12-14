package customer.stake.dto.counters;

import customer.stake.dto.limits.ValidationResponse;
import customer.stake.enums.Label;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostCountersResponse {

    private Customer customer;
    private Label label;
    private Attributes attributes;
    private ValidationResponse validationResponse;

    public PostCountersResponse(Customer customer, Label label, Attributes attributes) {
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }

    public PostCountersResponse(Customer customer, Label label, Attributes attributes, ValidationResponse validationResponse) {
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
        this.validationResponse = validationResponse;
    }
}
