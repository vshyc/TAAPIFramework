package customer.stake.pojo.counters;

import java.util.HashMap;
import java.util.Map;

public class Attribute {

    private String type;
    private Integer amount;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Attribute() {
    }

    public Attribute(String type, Integer amount) {
        super();
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}