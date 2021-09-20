package customer.stake.pojo.rgfes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESLimitFromOptions {

    private String name;
    private String interval;
    private Double limit;
    private Double used;

    public RGFESLimitFromOptions(String name, String interval, Double limit, Double used) {
        this.name = name;
        this.interval = interval;
        this.limit = limit;
        this.used = used;
    }
}