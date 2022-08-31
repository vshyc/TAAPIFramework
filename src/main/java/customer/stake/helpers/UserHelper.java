package customer.stake.helpers;

import com.google.gson.JsonObject;
import customer.stake.dto.cus.AccountStatusResponseCAS;
import customer.stake.exeptions.EbetGatewayException;
import customer.stake.data.generators.UserDataGenerator;
import customer.stake.dto.helpers.UserDataForCRFES;
import customer.stake.dto.helpers.UserDataForWebTestAPI;
import customer.stake.properties.EnvConfig;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static customer.stake.constants.MediaTypes.CONTENT_TYPE;
import static customer.stake.constants.MediaTypes.GATEWAY_CUSTOMER_DOCUMENT_REQUEST_JSON;
import static customer.stake.constants.MediaTypes.GATEWAY_CUSTOMER_DOCUMENT_STATUS_JSON;
import static io.restassured.RestAssured.given;


public class UserHelper {

    private UserDataForCRFES germanUserForCRFES = new UserDataGenerator().createGermanUserInCRFES();
    private UserDataForWebTestAPI germanUserForWebTestApi = new UserDataGenerator().createGermanUserForWebTestAPI();
    private JsonPath response;
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public String createGermanUserAndGetUuid() {
        return createGermanUserInWebTestApi().getString("tipicoCustomerId");
    }

    public String getGermanUserName() {
        return germanUserForWebTestApi.getLogin();
    }

    public String getGermanUserPassword() {
        return germanUserForWebTestApi.getPassword();
    }

    @Step("Sending a call to CRFES to create a user")
    public JsonPath createGermanUserInCRFES() {

        response = given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.crfesPath()).contentType(ContentType.JSON).
                header("X-Requested-By", "97066a5c-fc78-11e8-8eb2-f2801f1b9fd1").
                body(germanUserForCRFES).post().then()
                .statusCode(HttpStatus.SC_CREATED).extract().jsonPath();
        return response;
    }

    @Step("Sending a call to WebtestAPI to create a user")
    public JsonPath createGermanUserInWebTestApi() {
        response = given().baseUri(envConfig.holderUrl()).basePath(envConfig.webTestApiPath())
                .contentType(ContentType.JSON).
                body(germanUserForWebTestApi).post().then()
                .statusCode(HttpStatus.SC_CREATED).extract().jsonPath();
        return response;
    }

    private void requestVideoVerification(final String username, String uuid) throws EbetGatewayException {
        String kycRequestExceptionMessage = "KYC video verification has not been requested due to unexpected response from ebet gateway";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("comment", "string");
        int statusCode = given().baseUri(envConfig.apiInt())
                .basePath(envConfig.ebetGatewayCustomerRequestEndpoint())
                .contentType(GATEWAY_CUSTOMER_DOCUMENT_REQUEST_JSON)
                .pathParam("uuid", uuid)
                .body(requestBody.toString())
                .log().all()
                .when().post().then().log().all().extract().statusCode();
        if (statusCode != HttpStatus.SC_NO_CONTENT) {
            throw new EbetGatewayException(kycRequestExceptionMessage);
        }
    }

    private void approveVideoVerification(final String username, String uuid) throws EbetGatewayException {
        String kycStatusExceptionMessage = "KYC video verification has not been accepted due to unexpected response from ebet gateway";
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("status", "approved");
        int statusCode = given().baseUri(envConfig.apiInt())
                .basePath(envConfig.ebetGatewayCustomerStatusEndpoint())
                .contentType(GATEWAY_CUSTOMER_DOCUMENT_STATUS_JSON)
                .pathParam("uuid", uuid)
                .body(requestBody.toString())
                .log().all()
                .when().post().then().log().all().extract().statusCode();
        if (statusCode != HttpStatus.SC_NO_CONTENT) {
            throw new EbetGatewayException(kycStatusExceptionMessage);
        }
    }

    public AccountStatusResponseCAS getAccountStatusFromCus(String uuid) throws EbetGatewayException {{
        String selfExclusionErrorMessage = "User not found!";
        Response response = given().baseUri(envConfig.casBaseUri())
                .basePath(envConfig.caseBasePath())
                .auth().preemptive().basic("admin", "geheim")
                .accept(CONTENT_TYPE)
                .pathParam("uuid", uuid)
                .log().all()
                .get();
        if (response.statusCode() != HttpStatus.SC_OK) {
            throw new EbetGatewayException(selfExclusionErrorMessage);
        }
        return response.body().as(AccountStatusResponseCAS.class);
    }

    }

    public void getKYCVerifiedStatus(final String username, String uuid) throws EbetGatewayException {
        requestVideoVerification(username, uuid);
        approveVideoVerification(username, uuid);
    }


    public String getId(JsonPath jsonPath) {
        return jsonPath.getString("id");
    }

    public String getUuid(JsonPath jsonPath) {
        return jsonPath.getString("tipicoCustomerId");
    }

    public String getLogin(JsonPath jsonPath) {
        return jsonPath.getString("login");
    }

}
