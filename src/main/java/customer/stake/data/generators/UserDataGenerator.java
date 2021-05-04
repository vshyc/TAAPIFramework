package customer.stake.data.generators;

import customer.stake.enums.CountryCodes;
import customer.stake.pojo.helpers.Address;
import customer.stake.pojo.helpers.UserDataForCRFES;
import customer.stake.pojo.helpers.UserDataForWebTestAPI;

import java.text.SimpleDateFormat;

public class UserDataGenerator extends TestDataGenerator{

    public UserDataForCRFES createGermanUserInCRFES(){

        int ramdomN = faker().number().numberBetween(0, 1);
        String sex;
        if (ramdomN == 0) {
            sex = "male";
        } else {
            sex = "female";
        }
        UserDataForCRFES user = new UserDataForCRFES();
        user.setSalutation(sex);
        user.setUsername(faker().name().firstName()+faker().number().numberBetween(1,10000));
        user.setFirstName(faker().name().firstName());
        user.setLastName(faker().name().lastName());
        user.setCity(faker().address().cityName());
        user.setStreet(faker().address().streetAddress());
        user.setEmail(faker().name().username()+"@mailinator.com");
        user.setPassword("B00Xware+");
        user.setPostcode(10115);
        user.setBirthCountry(CountryCodes.GERMANY.getCountryCode());
        user.setBirthCity(faker().address().city());
        user.setBirthName(faker().name().lastName());
        user.setNationality(CountryCodes.GERMANY.getCountryCode());
        user.setCountry(CountryCodes.GERMANY.getCountryCode());
        user.setDataProcessing(true);
        user.setLicenseRegion(CountryCodes.GERMANY.getCountryCode());
        user.setOAuthClientId("testing");
        user.setIoBlackBox("testing");
        user.setDob(new SimpleDateFormat("yyyy-MM-dd").format(faker().date().birthday(18,40)));
        return user;
    }

    public UserDataForWebTestAPI createGermanUserForWebTestAPI(){
        int ramdomN = faker().number().numberBetween(0, 1);
        String sex;
        if (ramdomN == 0) {
            sex = "male";
        } else {
            sex = "female";
        }
        return UserDataForWebTestAPI.builder().registrationIp("89.247.25.21")
                .login(faker().name().firstName()+faker().number().numberBetween(1,10000))
                .firstName("Tom")
                .lastName(faker().name().lastName())
                .password("Secret123!")
                .salutation(sex)
                .dateOfBirth(faker().date().birthday(18,40))
                .phone("+44555666777")
                .email(faker().name().username()+"@springfield.gov")
                .language("en")
                .address(Address.builder().street("BRIGITTEWEG 10")
                        .postcode("10115")
                        .city(faker().address().cityName())
                        .country("DE").build()).build();
    }
}
