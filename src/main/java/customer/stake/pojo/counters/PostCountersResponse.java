package customer.stake.pojo.counters;

import customer.stake.enums.LabelEnums;
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
    private LabelEnums label;
    private Attributes attributes;

    public PostCountersResponse(Customer customer, LabelEnums label, Attributes attributes) {
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }
}
