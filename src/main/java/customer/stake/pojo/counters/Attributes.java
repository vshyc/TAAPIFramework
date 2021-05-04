package customer.stake.pojo.counters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attributes {

    private List<Double> payin = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Attributes() {
    }
    public Attributes(List<Double> payin) {
        super();
        this.payin = payin;
    }

    public List<Double> getPayin() {
        return payin;
    }

    public void setPayin(List<Double> payin) {
        this.payin = payin;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}