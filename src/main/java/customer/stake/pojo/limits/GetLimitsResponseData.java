package customer.stake.pojo.limits;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class GetLimitsResponseData {

    private List<LimitsResponseData> limits;

    public GetLimitsResponseData(List<LimitsResponseData> limits) {
        this.limits = limits;
    }
}
