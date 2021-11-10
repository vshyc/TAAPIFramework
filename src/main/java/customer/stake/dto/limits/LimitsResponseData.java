package customer.stake.dto.limits;


import customer.stake.enums.Label;
import customer.stake.enums.Owner;
import customer.stake.enums.LimitType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class LimitsResponseData {

    private String limitUUID;
    private LimitType type;
    private Owner owner;
    private Label label;
    private String product;
    private Double used;
    private Double remaining;
    private Integer leftTime;
    private Current current;
    private Future future;
    private String createdAt;
    private String createdBy;
    private String createdByClient;
    private String updatedAt;
    private String updatedBy;
    private String updatedByClient;
    private String createdSource;
    private String updatedSource;


    public LimitsResponseData(String limitUUID, LimitType type, Owner owner, Label label, String product, Double used,
                              Double remaining, Integer leftTime, Current current, Future future, String createdAt,
                              String createdBy, String createdByClient, String updatedAt, String updatedBy,
                              String updatedByClient, String createdSource, String updatedSource) {
        this.limitUUID = limitUUID;
        this.type = type;
        this.owner = owner;
        this.label = label;
        this.product = product;
        this.used = used;
        this.remaining = remaining;
        this.leftTime = leftTime;
        this.current = current;
        this.future = future;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.createdByClient = createdByClient;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.updatedByClient = updatedByClient;
        this.createdSource = createdSource;
        this.updatedSource = updatedSource;
    }

}