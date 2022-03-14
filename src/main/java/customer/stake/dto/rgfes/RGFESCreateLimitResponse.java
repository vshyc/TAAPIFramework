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
public class RGFESCreateLimitResponse {

    private RGFESLimit sports;
    private RGFESLimit games;

    public RGFESCreateLimitResponse(RGFESLimit sports, RGFESLimit games) {
        this.sports = sports;
        this.games = games;
    }

    public RGFESLimit getLimit(Product product) {
        if (product.equals(Product.SPORTS)) {
            return sports;
        } else if (product.equals(Product.GAMES)) {
            return games;
        } else return null;
    }
}
