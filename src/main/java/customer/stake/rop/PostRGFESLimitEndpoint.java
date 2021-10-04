package customer.stake.rop;

import customer.stake.helpers.HelpersConfig;
import customer.stake.pojo.limits.LimitCreationData;
import customer.stake.pojo.rgfes.RGFESCreateLimitResponse;
import customer.stake.properties.EnvConfig;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

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
        response = given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .contentType(ContentType.JSON)
                .accept("application/vnd.tipico.regulations.customer.limits-v2+json")
                .body(body)
                .post("/customer/limits");
        return this;
    }

}