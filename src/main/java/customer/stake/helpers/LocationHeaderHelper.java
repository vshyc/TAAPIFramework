package customer.stake.helpers;

import customer.stake.properties.EnvConfig;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class LocationHeaderHelper {

    private EnvConfig envConfig = HelpersConfig.envConfig;

    public String getLocationHeaderForNewLimit(String customerUUID){
        Response response = given().baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath()).
                auth().oauth2(new OauthHelper().getApplicationToken())
                .when().post("/customers/{customerUuid}/limits",customerUUID)
                .then().statusCode(HttpStatus.SC_CREATED).extract().response();
        return response.getHeader("location");
    }
}
