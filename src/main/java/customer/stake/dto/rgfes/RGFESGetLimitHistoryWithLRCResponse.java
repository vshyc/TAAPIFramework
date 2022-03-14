package customer.stake.dto.rgfes;

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

    public RGFESGetLimitHistoryWithLRCResponse(RGFESLimitHistoryProduct sports, RGFESLimitHistoryProduct games) {
        this.sports = sports;
        this.games = games;
    }

    public RGFESLimitHistoryProduct getLimitHistoryProduct(Product product) {
        if (product.equals(Product.SPORTS)) {
            return sports;
        } else if (product.equals(Product.GAMES)) {
            return games;
        } else return null;
    }
}
