package customer.stake.dto.rgfes;

import com.fasterxml.jackson.annotation.JsonProperty;
import customer.stake.enums.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESGetLimitHistoryWithLRCResponse {

    private RGFESLimitHistoryProduct sports;
    private RGFESLimitHistoryProduct games;
    @JsonProperty("GLOBAL")
    private RGFESLimitHistoryProduct GLOBAL;

    public RGFESGetLimitHistoryWithLRCResponse(RGFESLimitHistoryProduct sports, RGFESLimitHistoryProduct games) {
        this.sports = sports;
        this.games = games;
    }

    public RGFESGetLimitHistoryWithLRCResponse(RGFESLimitHistoryProduct sports,
            RGFESLimitHistoryProduct games, RGFESLimitHistoryProduct GLOBAL) {
        this.sports = sports;
        this.games = games;
        this.GLOBAL = GLOBAL;
    }

    public RGFESLimitHistoryProduct getLimitHistoryProduct(Product product) {
        if (product.equals(Product.SPORTS)) {
            return sports;
        } else if (product.equals(Product.GAMBLING)) {
            return games;
        } else if (product.equals(Product.GLOBAL)) {
            return GLOBAL;
        } else return null;
    }
}
