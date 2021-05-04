package customer.stake.pojo.helpers;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Address {
    private String street;
    private String postcode;
    private String city;
    private String country;

    public Address(String street, String postcode, String city, String country) {
        this.street = street;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
    }
}
