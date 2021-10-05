package customer.stake.dto.helpers;

import customer.stake.enums.LabelEnums;
import customer.stake.enums.LicenceRegion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class TAC {
    private String clientId;
    private Boolean isAffiliate;
    private LicenceRegion licenseRegion;
    private LabelEnums label;

    public TAC(String clientId, Boolean isAffiliate, LicenceRegion licenseRegion, LabelEnums label) {
        this.clientId = clientId;
        this.isAffiliate = isAffiliate;
        this.licenseRegion = licenseRegion;
        this.label = label;
    }
}

