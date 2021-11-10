package customer.stake.dto.counters;

import customer.stake.enums.CounterType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class Attribute {

    private CounterType type;
    private Double amount;

    public Attribute(CounterType type, Double amount) {
        super();
        this.type = type;
        this.amount = amount;
    }

}