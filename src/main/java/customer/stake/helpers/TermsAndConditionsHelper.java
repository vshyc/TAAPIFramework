package customer.stake.helpers;

import customer.stake.enums.LicenceRegion;
import customer.stake.pojo.helpers.TAC;
import customer.stake.properties.EnvConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TermsAndConditionsHelper {
    private EnvConfig envConfig = HelpersConfig.createConfiguration();
    private String user = "admin";
    private String password = envConfig.tacPass();

    public Response acceptAllDocumentsInTAC(String uuid){
        return given().baseUri(envConfig.tacBaseUri()).basePath(envConfig.tacPath())
                .contentType(ContentType.JSON).auth().preemptive()
                .basic(user,password)
                .body(TAC.builder().clientId("ebet").isAffiliate(false).licenseRegion(LicenceRegion.GERMANY).build())
                .when()
                .put("{customerUuid}/documents",uuid);
    }
}
