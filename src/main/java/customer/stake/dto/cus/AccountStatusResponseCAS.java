package customer.stake.dto.cus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatusResponseCAS {

    private String customerId;
    private String deactivationReason;
    private String deactivatedUntil;
    private String deactivatedAt;
    private String minimalDeactivationDuration;
    private Boolean active;
    private Boolean blocked;
}
