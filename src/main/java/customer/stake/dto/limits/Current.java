package customer.stake.dto.limits;

import customer.stake.enums.IntervalEnum;
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
    private IntervalEnum interval;

    public Current(Double value, IntervalEnum interval) {
        this.value = value;
        this.interval = interval;
    }
}
