package customer.stake.dto.rgfes;

import customer.stake.enums.Interval;
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
    private Interval interval;
    private Date updatedDate;

    public RGFESLimitHistoryType(Boolean requested, Double value, Interval interval, Date updatedDate) {
        this.requested = requested;
        this.value = value;
        this.interval = interval;
        this.updatedDate = updatedDate;
    }
}
