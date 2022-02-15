package customer.stake.dto.limits.history;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class AuditDetails {
    private Object timestamp;
    private int operationType;
    private String author;
    private String client;
    private String source;

    public AuditDetails(Object timestamp, int operationType, String author, String client, String source) {
        this.timestamp = timestamp;
        this.operationType = operationType;
        this.author = author;
        this.client = client;
        this.source = source;
    }
}
