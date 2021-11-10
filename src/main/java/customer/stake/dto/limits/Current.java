package customer.stake.dto.limits;

import customer.stake.enums.Interval;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Current {

    private Double value;
    private Interval interval;

    public Current(Double value, Interval interval) {
        this.value = value;
        this.interval = interval;
    }
}
