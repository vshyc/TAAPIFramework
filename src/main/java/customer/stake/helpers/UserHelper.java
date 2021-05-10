package customer.stake.helpers;
import customer.stake.helpers.HelpersConfig;
import customer.stake.data.generators.UserDataGenerator;
import customer.stake.pojo.helpers.UserDataForCRFES;
import customer.stake.pojo.helpers.UserDataForWebTestAPI;
import customer.stake.properties.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;


public class UserHelper {

    private UserDataForCRFES germanUserForCRFES = new UserDataGenerator().createGermanUserInCRFES();
    private UserDataForWebTestAPI germanUserForWebTestApi = new UserDataGenerator().createGermanUserForWebTestAPI();
    private JsonPath response;
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public String createGermanUserAndGetUuid(){
        return createGermanUserInWebTestApi().getString("tipicoCustomerId");
    }

    public String getGermanUserName() {
        return germanUserForWebTestApi.getLogin();
    }
    public String getGermanUserPassword(){
        return germanUserForWebTestApi.getPassword();
    }

    @Step("Sending a call to CRFES to create a user")
    public JsonPath createGermanUserInCRFES(){

            response =  given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.crfesPath()).contentType(ContentType.JSON).
                    header("X-Requested-By", "97066a5c-fc78-11e8-8eb2-f2801f1b9fd1").
                    body(germanUserForCRFES).post().then()
                    .statusCode(HttpStatus.SC_CREATED).extract().jsonPath();
        return response;
    }
    @Step("Sending a call to WebtestAPI to create a user")
    public JsonPath createGermanUserInWebTestApi(){
        response =  given().baseUri(envConfig.holderUrl()).basePath(envConfig.webTestApiPath())
                .contentType(ContentType.JSON).
                body(germanUserForWebTestApi).post().then()
                .statusCode(HttpStatus.SC_CREATED).extract().jsonPath();
        return response;
    }
    public String getId(JsonPath jsonPath){
        return jsonPath.getString("id");
    }
    public String getUuid(JsonPath jsonPath){
        return jsonPath.getString("tipicoCustomerId");
    }

}
