package customer.stake.dto.models;

public class AccountStatusResponseCAS {

    private String customerId;

    private String deactivationReason;

    private String deactivatedUntil;

    private String deactivatedAt;

    private String minimalDeactivationDuration;

    private Boolean active;

    private Boolean blocked;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeactivationReason() {
        return deactivationReason;
    }

    public void setDeactivationReason(String deactivationReason) {
        this.deactivationReason = deactivationReason;
    }

    public String getDeactivatedUntil() {
        return deactivatedUntil;
    }

    public void setDeactivatedUntil(String deactivatedUntil) {
        this.deactivatedUntil = deactivatedUntil;
    }

    public String getDeactivatedAt() {
        return deactivatedAt;
    }

    public void setDeactivatedAt(String deactivatedAt) {
        this.deactivatedAt = deactivatedAt;
    }

    public String getMinimalDeactivationDuration() {
        return minimalDeactivationDuration;
    }

    public void setMinimalDeactivationDuration(String minimalDeactivationDuration) {
        this.minimalDeactivationDuration = minimalDeactivationDuration;
    }

    public Boolean getActive() { return active; }

    public void setActive(Boolean active) { this.active = active; }

    public Boolean getBlocked() { return blocked; }

    public void setBlocked(Boolean blocked) {this.blocked = blocked; }
}
