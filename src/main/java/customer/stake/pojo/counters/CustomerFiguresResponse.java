package customer.stake.pojo.counters;

import java.util.HashMap;
import java.util.Map;

public class CustomerFiguresResponse {

    private Customer customer;
    private String label;
    private Attributes attributes;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public CustomerFiguresResponse() {
    }

    public CustomerFiguresResponse(Customer customer, String label, Attributes attributes) {
        super();
        this.customer = customer;
        this.label = label;
        this.attributes = attributes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}