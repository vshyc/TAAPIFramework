package customer.stake.pojo.rgfes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESLimitsFromOptions {

    private List<RGFESLimitFromOptions> depositLimits;

    public RGFESLimitsFromOptions(List<RGFESLimitFromOptions> depositLimits) {
        this.depositLimits = depositLimits;
    }
}