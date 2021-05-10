package customer.stake.pojo.limits;


import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import customer.stake.enums.TypeEnum;
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
    private TypeEnum type;
    private OwnerEnum owner;
    private LabelEnums label;
    private String product;
    private Float used;
    private Float remaining;
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


    public LimitsResponseData(String limitUUID, TypeEnum type, OwnerEnum owner, LabelEnums label, String product, Float used,
                              Float remaining, Integer leftTime, Current current, Future future, String createdAt,
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