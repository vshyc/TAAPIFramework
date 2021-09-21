package customer.stake.pojo.rgfes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESGetLimitServiceLimitResponse {

    private RGFESLimitsFromLimitService sports;
    private RGFESLimitsFromLimitService games;

    public RGFESGetLimitServiceLimitResponse(RGFESLimitsFromLimitService sports, RGFESLimitsFromLimitService games) {
        this.sports = sports;
        this.games = games;
    }
}
