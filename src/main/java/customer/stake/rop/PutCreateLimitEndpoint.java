package customer.stake.rop;

import customer.stake.helpers.LocationHeaderHelper;
import customer.stake.helpers.OauthHelper;
import customer.stake.pojo.limits.GetLimitsResponseData;
import customer.stake.pojo.limits.LimitCreationData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.properties.EnvConfig;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class PutCreateLimitEndpoint extends BaseEndpoint<PutCreateLimitEndpoint, LimitsResponseData>{
    EnvConfig envConfig = ConfigFactory.create(EnvConfig.class);

    @Override
    protected Type getModelType() {
        return LimitsResponseData.class;
    }


    public PutCreateLimitEndpoint sendRequestToCreateNewLimit(LimitCreationData body, String oauthToken, String uuid) {
        String location = new LocationHeaderHelper().getLocationHeaderForNewLimit(uuid);
        response = given().baseUri(envConfig.baseUri()).basePath(location)
                .contentType(ContentType.JSON)
                .auth().oauth2(oauthToken)
                .body(body).when().put();
        return this;
    }
    public PutCreateLimitEndpoint sendRequestToUpdateLimit(LimitCreationData body, String oauthToken, String uuid){

        GetLimitsResponseData getResponse = given().auth().oauth2(new OauthHelper().getApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath()).when().get("customers/{customerUuid}/limits/",uuid).then()
                .statusCode(200).extract().as(GetLimitsResponseData.class);

        response = given().baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .contentType(ContentType.JSON)
                .auth().oauth2(oauthToken)
                .body(body).when().put("customers/{customerUuid}/limits/{limitUuid}",uuid,getResponse.getLimits().get(0).getLimitUUID());
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
