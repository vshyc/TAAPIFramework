package customer.stake.dto.limits.history;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class GetLimitsHistoryResponseData {

    private ArrayList<LimitsHistory> limitsHistory;

    public GetLimitsHistoryResponseData(ArrayList<LimitsHistory> limitsHistory) {
        this.limitsHistory = limitsHistory;
    }
}
