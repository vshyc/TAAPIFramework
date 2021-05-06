package customer.stake.helpers;

import customer.stake.enums.OwnerEnum;
import customer.stake.pojo.limits.GetLimitsResponseData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.properties.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetLimitsHelper {

    private EnvConfig envConfig = HelpersConfig.createConfiguration();


    @Step("Check if limit exists in CSS")
    public LimitsResponseData checkIfLimitExistForUser(String uuid, OwnerEnum limitOwner, String limitType) {

        Response responseData = given().auth().oauth2(new OauthHelper().getApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath()).when().get("customers/" + uuid + "/limits/");
        LimitsResponseData data;

        data =  (responseData.getStatusCode() == 200) ?
         responseData.then().extract().as(GetLimitsResponseData.class).getLimits().stream()
                .filter(response -> limitOwner.toString().equals(response.getOwner()))
                .filter(response -> limitType.equals(response.getType()))
                .findAny()
                .orElse(null) :  null;
        return data;
    }


}
