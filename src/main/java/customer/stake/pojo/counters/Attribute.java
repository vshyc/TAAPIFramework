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
public class Attribute {

    private String type;
    private Integer amount;

    public Attribute(String type, Integer amount) {
        super();
        this.type = type;
        this.amount = amount;
    }

}