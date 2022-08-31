package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder
public enum Product {
    SPORTS("sports"),
    GLOBAL("GLOBAL"),
    GAMBLING("gambling");

    private final String product;

    Product(String product) {
        this.product = product;
    }

    @JsonValue
    public String getProduct() {
        return product;
    }
}
