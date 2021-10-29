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
public class LimitDetails {

    private Double value;
    private IntervalEnum interval;

    public LimitDetails(Double value, IntervalEnum interval) {
        this.value = value;
        this.interval = interval;
    }
}
