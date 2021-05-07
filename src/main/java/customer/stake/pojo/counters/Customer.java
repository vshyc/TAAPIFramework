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
public class Customer {

    private String uuid;
    private Integer id;

    public Customer(Integer id) {
        super();
        this.id = id;
    }

    public Customer(String uuid, Integer id) {
        this.uuid = uuid;
        this.id = id;
    }

}
