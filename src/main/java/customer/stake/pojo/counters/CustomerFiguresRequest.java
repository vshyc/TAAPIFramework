package customer.stake.pojo.counters;

import customer.stake.enums.LabelEnums;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class CustomerFiguresRequest {

    private Customer customer;
    private LabelEnums label;
    private List<Attribute> attributes = null;

    public CustomerFiguresRequest(Customer customer, LabelEnums label, List<Attribute> attributes) {
        super();
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }


}