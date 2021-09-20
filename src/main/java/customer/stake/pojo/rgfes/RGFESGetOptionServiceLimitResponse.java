package customer.stake.pojo.rgfes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESGetOptionServiceLimitResponse {

    private RGFESLimitsFromOptions customLimits;
    private RGFESLimitsFromOptions accountLimits;

    public RGFESGetOptionServiceLimitResponse(RGFESLimitsFromOptions customLimits, RGFESLimitsFromOptions accountLimits) {
        this.customLimits = customLimits;
        this.accountLimits = accountLimits;
    }
}