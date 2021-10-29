package customer.stake.dto.counters;

import customer.stake.enums.CounterTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class Attribute {

    private CounterTypeEnum type;
    private Double amount;

    public Attribute(CounterTypeEnum type, Double amount) {
        super();
        this.type = type;
        this.amount = amount;
    }

}