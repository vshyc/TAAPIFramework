package customer.stake.dto.rgfes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESCreateLimitResponse {

    private RGFESLimit sports;
    private RGFESLimit games;

    public RGFESCreateLimitResponse(RGFESLimit sports, RGFESLimit games) {
        this.sports = sports;
        this.games = games;
    }

    public RGFESLimit getLimit(String type) {
        if (type.equals("sports")) {
            return sports;
        } else if (type.equals("games")) {
            return games;
        } else return null;
    }
}
