package customer.stake.pojo.counters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerFiguresRequest {

    private Customer customer;
    private String label;
    private List<Attribute> attributes = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public CustomerFiguresRequest() {
    }

    public CustomerFiguresRequest(Customer customer, String label, List<Attribute> attributes) {
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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}