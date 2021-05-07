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

    private Float value;
    private String interval;
    private String from;

    public Future(Float value, String interval, String from) {
        this.value = value;
        this.interval = interval;
        this.from = from;
    }
}
