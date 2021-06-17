package customer.stake.pojo.rgfes;

import customer.stake.pojo.limits.Current;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESLimitValues {

    private String limitUUID;
    private Double used;
    private Double remaining;
    private Current current;

    public RGFESLimitValues(String limitUUID, Double used, Double remaining, Current current) {
        this.limitUUID = limitUUID;
        this.used = used;
        this.remaining = remaining;
        this.current = current;
    }
}
