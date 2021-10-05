package customer.stake.rop;

import customer.stake.helpers.HelpersConfig;
import customer.stake.dto.rgfes.RGFESGetLimitServiceLimitResponse;
import customer.stake.dto.rgfes.RGFESGetOptionServiceLimitResponse;
import customer.stake.properties.EnvConfig;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

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
        response = given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .contentType("application/vnd.tipico.regulations.customer.limits-v2+json")
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .accept("application/vnd.tipico.regulations.customer.limits-v2+json")
                .get("/customer/limits");
        return this;
    }

    public GetRGFESLimitEndpoint sendRequest(String sessionId, String acceptAndContentTypeHeader) {
        response = given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .contentType(acceptAndContentTypeHeader)
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .accept(acceptAndContentTypeHeader)
                .get("/customer/limits");
        return this;
    }
}