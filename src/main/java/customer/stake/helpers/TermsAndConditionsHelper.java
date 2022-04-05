package customer.stake.helpers;

import customer.stake.enums.Label;
import customer.stake.enums.LicenceRegion;
import customer.stake.dto.helpers.TAC;
import customer.stake.enums.Product;
import customer.stake.properties.EnvConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TermsAndConditionsHelper {
    private final EnvConfig envConfig = HelpersConfig.createConfiguration();
    private final String user = "admin";
    private final String password = envConfig.tacPass();
    private final LRCHelper lrcHelper = new LRCHelper();

    public Response acceptAllDocumentsInTAC(String uuid) {
        return given().baseUri(envConfig.tacBaseUri()).basePath(envConfig.tacPath())
                .contentType(ContentType.JSON).auth().preemptive()
                .basic(user, password)
                .header("tipico-license-region-context",lrcHelper.createLRC(Product.SPORTS,Label.tipico))
                .body(TAC.builder().clientId("ebet").isAffiliate(false).licenseRegion(LicenceRegion.GERMANY)
                        .label(Label.tipico).build())
                .when()
                .put("{customerUuid}/documents", uuid);
    }

    public Response acceptAllDocumentsInTAC(String uuid,Label label) {
        return given().baseUri(envConfig.tacBaseUri()).basePath(envConfig.tacPath())
                .contentType(ContentType.JSON).auth().preemptive()
                .basic(user, password)
                .header("tipico-license-region-context",lrcHelper.createLRC(Product.SPORTS,label))
                .body(TAC.builder().clientId("ebet").isAffiliate(false).licenseRegion(LicenceRegion.GERMANY)
                        .label(Label.tipico).build())
                .when()
                .put("{customerUuid}/documents", uuid);
    }

    public Response acceptAllDocumentsInTAC(String uuid,Product product) {
        return given().baseUri(envConfig.tacBaseUri()).basePath(envConfig.tacPath())
                .contentType(ContentType.JSON).auth().preemptive()
                .basic(user, password)
                .header("tipico-license-region-context",lrcHelper.createLRC(product,Label.tipico))
                .body(TAC.builder().clientId("ebet").isAffiliate(false).licenseRegion(LicenceRegion.GERMANY)
                        .label(Label.tipico).build())
                .when()
                .put("{customerUuid}/documents", uuid);
    }

    public Response acceptAllDocumentsInTAC(String uuid,Label label,Product product) {
        return given().baseUri(envConfig.tacBaseUri()).basePath(envConfig.tacPath())
                .contentType(ContentType.JSON).auth().preemptive()
                .basic(user, password)
                .header("tipico-license-region-context",lrcHelper.createLRC(product,label))
                .body(TAC.builder().clientId("ebet").isAffiliate(false).licenseRegion(LicenceRegion.GERMANY)
                        .label(Label.tipico).build())
                .when()
                .put("{customerUuid}/documents", uuid);
    }
}
