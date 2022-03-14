package customer.stake.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Product {
    SPORTS("sports"),
    GAMES("games");

    private final String product;

    Product(String product) {
        this.product = product;
    }

    @JsonValue
    public String getProduct() {
        return product;
    }
}
