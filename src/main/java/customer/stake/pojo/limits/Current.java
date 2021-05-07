package customer.stake.pojo.limits;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Current {

    private Float value;
    private String interval;

    public Current(Float value, String interval) {
        this.value = value;
        this.interval = interval;
    }
}
