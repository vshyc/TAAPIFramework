package customer.stake.dto.limits;

import customer.stake.enums.ValidatorResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class LimitValidator {

    private Double allowedAmount;
    private Double usedAmount;
    private LimitDetails limitDetails;
    private String optionDefId;
    private ValidatorResult validationResult;

    public LimitValidator(Double allowedAmount, Double usedAmount, LimitDetails limitDetails, String optionDefId,
                          ValidatorResult validationResult) {
        this.allowedAmount = allowedAmount;
        this.usedAmount = usedAmount;
        this.limitDetails = limitDetails;
        this.optionDefId = optionDefId;
        this.validationResult = validationResult;
    }
}
