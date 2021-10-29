package customer.stake.dto.limits;

import customer.stake.enums.ValidatorResultEnum;
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
    private ValidatorResultEnum validationResult;

    public LimitValidator(Double allowedAmount, Double usedAmount, LimitDetails limitDetails, String optionDefId,
                          ValidatorResultEnum validationResult) {
        this.allowedAmount = allowedAmount;
        this.usedAmount = usedAmount;
        this.limitDetails = limitDetails;
        this.optionDefId = optionDefId;
        this.validationResult = validationResult;
    }
}
