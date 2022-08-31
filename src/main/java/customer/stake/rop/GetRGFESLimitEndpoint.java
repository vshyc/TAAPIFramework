package customer.stake.rop;

import customer.stake.helpers.HelpersConfig;
import customer.stake.dto.rgfes.RGFESGetLimitServiceLimitResponse;
import customer.stake.dto.rgfes.RGFESGetOptionServiceLimitResponse;
import customer.stake.properties.EnvConfig;
import customer.stake.request.configuration.RequestConfigurationBuilder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static customer.stake.constants.MediaTypes.LIMIT_SERVICE_JSON;
import static customer.stake.constants.RequestPaths.LIMITS_REQUEST_PATH;
import static io.restassured.RestAssured.given;

public class GetRGFESLimitEndpoint extends BaseEndpoint<GetRGFESLimitEndpoint, RGFESGetOptionServiceLimitResponse> {

    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return RGFESGetOptionServiceLimitResponse.class;
    }

    public RGFESGetLimitServiceLimitResponse getModelTypeForLimitServiceResponse() {
        return response.as(RGFESGetLimitServiceLimitResponse.class);
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public GetRGFESLimitEndpoint sendRequest(String sessionId) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .contentType(LIMIT_SERVICE_JSON)
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .accept(LIMIT_SERVICE_JSON)
                .get(LIMITS_REQUEST_PATH);
        return this;
    }

    public GetRGFESLimitEndpoint sendRequest(String sessionId, String acceptAndContentTypeHeader) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .contentType(acceptAndContentTypeHeader)
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .accept(acceptAndContentTypeHeader)
                .get(LIMITS_REQUEST_PATH);
        return this;
    }
}