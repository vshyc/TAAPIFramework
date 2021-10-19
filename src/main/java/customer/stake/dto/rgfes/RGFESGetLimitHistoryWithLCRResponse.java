package customer.stake.dto.rgfes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESGetLimitHistoryWithLCRResponse {

    private RGFESLimitHistoryProduct sports;
    private RGFESLimitHistoryProduct games;

    public RGFESGetLimitHistoryWithLCRResponse(RGFESLimitHistoryProduct sports, RGFESLimitHistoryProduct games) {
        this.sports = sports;
        this.games = games;
    }

    public RGFESLimitHistoryProduct getLimitHistoryProduct(String product) {
        if (product.equals("sports")) {
            return sports;
        } else if (product.equals("games")) {
            return games;
        } else return null;
    }
}
