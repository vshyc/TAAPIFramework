package customer.stake.data.generators;

import customer.stake.enums.CountryCodes;
import customer.stake.dto.helpers.Address;
import customer.stake.dto.helpers.UserDataForCRFES;
import customer.stake.dto.helpers.UserDataForWebTestAPI;

import java.text.SimpleDateFormat;

public class UserDataGenerator extends TestDataGenerator {

    public UserDataForCRFES createGermanUserInCRFES() {
        int ramdomN = faker().number().numberBetween(0, 1);
        String sex;
        if (ramdomN == 0) {
            sex = "male";
        } else {
            sex = "female";
        }
        return UserDataForCRFES.builder()
                .salutation(sex)
                .username(faker().name().firstName() + faker().number().numberBetween(1, 10000))
                .firstName(faker().name().firstName())
                .lastName(faker().name().lastName())
                .city(faker().address().cityName())
                .street(faker().address().streetAddress())
                .email(faker().name().username() + "@mailinator.com")
                .password("B00Xware+")
                .postcode(10115)
                .birthCountry(CountryCodes.GERMANY.getCountryCode())
                .birthCity(faker().address().city())
                .birthName(faker().name().lastName())
                .nationality(CountryCodes.GERMANY.getCountryCode())
                .country(CountryCodes.GERMANY.getCountryCode())
                .dataProcessing(true)
                .licenseRegion(CountryCodes.GERMANY.getCountryCode())
                .oAuthClientId("testing")
                .ioBlackBox("testing")
                .dob(new SimpleDateFormat("yyyy-MM-dd").format(faker().date().birthday(18, 40))).build();
    }

    public UserDataForWebTestAPI createGermanUserForWebTestAPI() {
        int ramdomN = faker().number().numberBetween(0, 1);
        String sex;
        if (ramdomN == 0) {
            sex = "male";
        } else {
            sex = "female";
        }
        return UserDataForWebTestAPI.builder().registrationIp("89.247.25.21")
                .login(faker().name().firstName() + faker().number().numberBetween(1, 10000) + faker().name().lastName())
                .firstName("Tom")
                .lastName(faker().name().lastName())
                .password("Secret123!")
                .salutation(sex)
                .dateOfBirth(faker().date().birthday(18, 40))
                .phone("+44555666777")
                .email(faker().name().username() + faker().number().numberBetween(1, 10000) + "@springfield.gov")
                .language("en")
                .address(Address.builder().street("BRIGITTEWEG 10")
                        .postcode("10115")
                        .city(faker().address().cityName())
                        .country(CountryCodes.GERMANY.getCountryCode()).build()).build();
    }
}
