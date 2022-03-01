package customer.stake.dto.limits.history;

import customer.stake.dto.limits.Current;
import customer.stake.dto.limits.Future;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Owner;
import customer.stake.enums.Product;
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
    private Product product;
    private Label label;
    private String limitUUID;
    private Owner owner;
    private Current current;
    private Future future;
    private String customerUUID;
    private Date createdAt;
    private String createdByClient;
    private String createdBy;
    private String updatedByClient;
    private String updatedBy;
    private AuditDetails auditDetails;
    private Date updatedAt;

    public LimitsHistory(LimitType type, Product product, Label label, String limitUUID, Owner owner,
            Current current, Future future, String customerUUID, Date createdAt, String createdByClient,
            String createdBy, String updatedByClient, String updatedBy,
            AuditDetails auditDetails, Date updatedAt) {
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
        this.createdBy = createdBy;
        this.updatedByClient = updatedByClient;
        this.updatedBy = updatedBy;
        this.auditDetails = auditDetails;
        this.updatedAt = updatedAt;
    }
}
