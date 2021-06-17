package customer.stake.pojo.helpers;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class Payment {

    private Double amount;
    private String bonusId;
    private String iovationBlackbox;
    private String sofortShDetails;
    private String origin;

    public Payment(Double amount, String bonusId, String iovationBlackbox, String sofortShDetails, String origin) {
        this.amount = amount;
        this.bonusId = bonusId;
        this.iovationBlackbox = iovationBlackbox;
        this.sofortShDetails = sofortShDetails;
        this.origin = origin;
    }
}
