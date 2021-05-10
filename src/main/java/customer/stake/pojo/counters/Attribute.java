package customer.stake.pojo.counters;

import customer.stake.enums.TypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@Builder
public class Attribute {

    private TypeEnum type;
    private Double amount;

    public Attribute(TypeEnum type, Double amount) {
        super();
        this.type = type;
        this.amount = amount;
    }

}