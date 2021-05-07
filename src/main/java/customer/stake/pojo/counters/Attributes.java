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
public class Attributes {

    private List<Double> payin = null;

    public Attributes(List<Double> payin) {
        super();
        this.payin = payin;
    }

}