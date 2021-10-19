package customer.stake.dto.counters;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class Customer {

    private String uuid;
    private String id;

    public Customer(String id) {
        super();
        this.id = id;
    }

    public Customer(String uuid, String id) {
        this.uuid = uuid;
        this.id = id;
    }

}
