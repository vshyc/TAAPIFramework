package customer.stake.pojo.counters;

import java.util.HashMap;
import java.util.Map;

public class Customer {

    private String uuid;
    private Integer id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Customer() {
    }

    public Customer(Integer id) {
        super();
        this.id = id;
    }

    public Customer(String uuid, Integer id) {
        this.uuid = uuid;
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
