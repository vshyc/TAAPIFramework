package customer.stake.pojo.helpers;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserDataForWebTestAPI {

    private String registrationIp;
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private String salutation;
    private Date dateOfBirth;
    private String phone;
    private String email;
    private String language;
    private Address address;

    public UserDataForWebTestAPI(String registrationIp, String login, String firstName, String lastName, String password
            , String salutation, Date dateOfBirth, String phone, String email, String language, Address address) {
        this.registrationIp = registrationIp;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.salutation = salutation;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.language = language;
        this.address = address;
    }
}
