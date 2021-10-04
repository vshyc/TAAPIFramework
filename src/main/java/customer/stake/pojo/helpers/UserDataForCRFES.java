package customer.stake.pojo.helpers;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserDataForCRFES {

    private String salutation;
    private String username;
    private String firstName;
    private String lastName;
    private String city;
    private String street;
    private String email;
    private String password;
    private String dob;
    private Integer postcode;
    private String birthCountry;
    private String birthCity;
    private String birthName;
    private String nationality;
    private String country;
    private boolean dataProcessing;
    private String licenseRegion;
    private String oAuthClientId;
    private String ioBlackBox;


    public UserDataForCRFES(String salutation, String username, String firstName, String lastName, String city, String street,
                            String email, String password, String dob, Integer postcode, String birthCountry, String birthCity,
                            String birthName, String nationality, String country, boolean dataProcessing, String licenseRegion,
                            String oAuthClientId, String ioBlackBox) {
        super();
        this.salutation = salutation;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.postcode = postcode;
        this.birthCountry = birthCountry;
        this.birthCity = birthCity;
        this.birthName = birthName;
        this.nationality = nationality;
        this.country = country;
        this.dataProcessing = dataProcessing;
        this.licenseRegion = licenseRegion;
        this.oAuthClientId = oAuthClientId;
        this.ioBlackBox = ioBlackBox;
    }

}
