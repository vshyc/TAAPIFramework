package customer.stake.pojo.limits;

import customer.stake.enums.IntervalEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Future {

    private Float value;
    private IntervalEnum interval;
    private String from;

    public Future(Float value, IntervalEnum interval, String from) {
        this.value = value;
        this.interval = interval;
        this.from = from;
    }
}
