package customer.stake.rop;

import customer.stake.helpers.HelpersConfig;
import customer.stake.helpers.OauthHelper;
import customer.stake.dto.limits.GetLimitsResponseData;
import customer.stake.properties.EnvConfig;
import customer.stake.request.configuration.RequestConfigurationBuilder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class GetLimitEndpoint extends BaseEndpoint<GetLimitEndpoint, GetLimitsResponseData> {

    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return GetLimitsResponseData.class;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public GetLimitEndpoint sendRequest(String uuid) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().oauth2(new OauthHelper().getApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .when().get("customers/{customerUuid}/limits/", uuid);
        return this;
    }

    public GetLimitEndpoint sendRequestWithNoAuth(String uuid) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .when().get("customers/{customerUuid}/limits/", uuid);
        return this;
    }

    protected int getMissingLimitStatusCode() {
        return HttpStatus.SC_NO_CONTENT;
    }

    public GetLimitEndpoint assertMissingLimitRequestStatusCode() {
        return assertStatusCode(getMissingLimitStatusCode());
    }
}
