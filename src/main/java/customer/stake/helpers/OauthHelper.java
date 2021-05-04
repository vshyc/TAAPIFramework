package customer.stake.helpers;

import customer.stake.properties.EnvConfig;
import io.restassured.path.json.JsonPath;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class OauthHelper {

    EnvConfig envConfig= ConfigFactory.create(EnvConfig.class);

    public String getApplicationToken(){
        JsonPath response = given().baseUri(envConfig.authBaseUri()).basePath(envConfig.authBasePath()).
                auth().preemptive().basic(envConfig.authUser(),envConfig.authPassword())
                .when().post("/token?grant_type={grantType}","client_credentials")
                .then().statusCode(HttpStatus.SC_OK).extract().jsonPath();
        return response.getString("access_token");
    }

    public String getUserToken(String username, String userPassword){
        JsonPath response = given().baseUri(envConfig.authBaseUri()).basePath(envConfig.authBasePath()).
                auth().preemptive().basic(envConfig.authUser(),envConfig.authPassword())
                .when().post("/token?grant_type={grantType}&username={username}&password={password}",
                        "password",username,userPassword)
                .then().statusCode(HttpStatus.SC_OK).extract().jsonPath();
        return response.getString("access_token");
    }
}
