package customer.stake.dto.rgfes;

import customer.stake.enums.IntervalEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RGFESLimitHistoryType {

    private Boolean requested;
    private Double value;
    private IntervalEnum interval;
    private Date updatedDate;

    public RGFESLimitHistoryType(Boolean requested, Double value, IntervalEnum interval, Date updatedDate) {
        this.requested = requested;
        this.value = value;
        this.interval = interval;
        this.updatedDate = updatedDate;
    }
}
