package customer.stake.pojo.helpers;

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

    public TAC(String clientId, Boolean isAffiliate, LicenceRegion licenseRegion) {
        this.clientId = clientId;
        this.isAffiliate = isAffiliate;
        this.licenseRegion = licenseRegion;
    }
}

