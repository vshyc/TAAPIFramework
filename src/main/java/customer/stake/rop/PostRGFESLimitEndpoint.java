package customer.stake.rop;

import customer.stake.helpers.HelpersConfig;
import customer.stake.dto.limits.LimitCreationData;
import customer.stake.dto.rgfes.RGFESCreateLimitResponse;
import customer.stake.properties.EnvConfig;
import customer.stake.request.configuration.RequestConfigurationBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static customer.stake.constants.MediaTypes.LIMIT_SERVICE_JSON;
import static customer.stake.constants.RequestPaths.LIMITS_REQUEST_PATH;
import static io.restassured.RestAssured.given;


public class PostRGFESLimitEndpoint extends BaseEndpoint<PostRGFESLimitEndpoint, RGFESCreateLimitResponse> {
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return RGFESCreateLimitResponse.class;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public PostRGFESLimitEndpoint sendRequest(String sessionId, LimitCreationData body) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .contentType(ContentType.JSON)
                .accept(LIMIT_SERVICE_JSON)
                .body(body)
                .post(LIMITS_REQUEST_PATH);
        return this;
    }

}