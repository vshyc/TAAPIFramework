package customer.stake.dto.rgfes;

import customer.stake.dto.limits.Current;
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
