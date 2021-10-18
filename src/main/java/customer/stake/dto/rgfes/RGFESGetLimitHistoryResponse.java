package customer.stake.dto.rgfes;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESGetLimitHistoryResponse {

    List<RGFESLimitHistory> history;

    public RGFESGetLimitHistoryResponse(List<RGFESLimitHistory> history) {
        this.history = history;
    }
}
