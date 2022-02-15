package customer.stake.dto.limits.history;

import customer.stake.dto.limits.Current;
import customer.stake.dto.limits.Future;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class LimitsHistory {
    private LimitType type;
    private String product;
    private Label label;
    private String limitUUID;
    private String owner;
    private Current current;
    private Future future;
    private String customerUUID;
    private Date createdAt;
    private String createdByClient;
    private String updatedByClient;
    private AuditDetails auditDetails;
    private Date updatedAt;

    public LimitsHistory(LimitType type, String product, Label label, String limitUUID, String owner, Current current,
                         Future future, String customerUUID, Date createdAt, String createdByClient,
                         String updatedByClient, AuditDetails auditDetails, Date updatedAt) {
        this.type = type;
        this.product = product;
        this.label = label;
        this.limitUUID = limitUUID;
        this.owner = owner;
        this.current = current;
        this.future = future;
        this.customerUUID = customerUUID;
        this.createdAt = createdAt;
        this.createdByClient = createdByClient;
        this.updatedByClient = updatedByClient;
        this.auditDetails = auditDetails;
        this.updatedAt = updatedAt;
    }
}
