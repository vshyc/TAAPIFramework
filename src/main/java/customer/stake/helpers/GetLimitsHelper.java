package customer.stake.helpers;


import customer.stake.enums.OwnerEnum;
import customer.stake.pojo.limits.GetLimitsResponseData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.properties.EnvConfig;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;

public class GetLimitsHelper {

    EnvConfig envConfig= ConfigFactory.create(EnvConfig.class);

    public LimitsResponseData checkIfLimitExistForUser(String uuid, OwnerEnum limitOwner, String limitType) {

        GetLimitsResponseData responseData = given().auth().oauth2(new OauthHelper().getApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath()).when().get("customers/" + uuid + "/limits/").then()
                .statusCode(200).extract().as(GetLimitsResponseData.class);

        LimitsResponseData data = responseData.getLimits().stream()
                .filter(response -> limitOwner.toString().equals(response.getOwner()))
                .filter(response -> limitType.equals(response.getType()))
                .findAny()
                .orElse(null);

        return data;
    }


}
