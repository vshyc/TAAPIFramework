package customer.stake.pojo.limits;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Future {

    public Float value;
    public String interval;
    public String from;

    public Future(Float value, String interval, String from) {
        this.value = value;
        this.interval = interval;
        this.from = from;
    }
}
