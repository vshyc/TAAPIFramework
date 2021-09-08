package customer.stake.rop;

import customer.stake.helpers.GetLimitsHelper;
import customer.stake.helpers.HelpersConfig;
import customer.stake.helpers.LocationHeaderHelper;
import customer.stake.pojo.limits.GetLimitsResponseData;
import customer.stake.pojo.limits.LimitCreationData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.properties.EnvConfig;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class PutLimitEndpoint extends BaseEndpoint<PutLimitEndpoint, LimitsResponseData>{
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return LimitsResponseData.class;
    }


    public PutLimitEndpoint sendRequestToCreateNewLimit(LimitCreationData body, String oauthToken, String uuid) {
        String location = new LocationHeaderHelper().getLocationHeaderForNewLimit(uuid);
        response = given().baseUri(envConfig.baseUri()).basePath(location)
                .contentType(ContentType.JSON)
                .auth().oauth2(oauthToken)
                .body(body).when().put();
        return this;
    }
    public PutLimitEndpoint sendRequestWithNoBodyToCreateNewLimit(String oauthToken, String uuid) {
        String location = new LocationHeaderHelper().getLocationHeaderForNewLimit(uuid);
        response = given().baseUri(envConfig.baseUri()).basePath(location)
                .contentType(ContentType.JSON)
                .auth().oauth2(oauthToken).when().put();
        return this;
    }
    public PutLimitEndpoint sendRequestToCreateNewLimitWithNoAuth(LimitCreationData body, String uuid) {
        String location = new LocationHeaderHelper().getLocationHeaderForNewLimit(uuid);
        response = given().baseUri(envConfig.baseUri()).basePath(location)
                .contentType(ContentType.JSON)
                .body(body).when().put();
        return this;
    }
    public PutLimitEndpoint sendRequestToCreateNewLimitWithNoContentType(LimitCreationData body,
                                                                         String oauthToken,
                                                                         String uuid) {
        String location = new LocationHeaderHelper().getLocationHeaderForNewLimit(uuid);
        response = given().baseUri(envConfig.baseUri()).basePath(location)
                .auth().oauth2(oauthToken)
                .body(body).when().put();
        return this;
    }
    public PutLimitEndpoint sendRequestToUpdateLimit(LimitCreationData body, String oauthToken, String uuid){

        response = given().baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .contentType(ContentType.JSON)
                .auth().oauth2(oauthToken)
                .body(body).when().put("customers/{customerUuid}/limits/{limitUuid}",uuid,
                        new GetLimitsHelper().checkIfLimitExistForUser(uuid,body.getOwner(),body.getType(),
                                body.getLabel()).getLimitUUID());
        return this;
    }

    public GetLimitsResponseData getModelTypeForUpdate() {
        return new GetLimitsResponseData();
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}
