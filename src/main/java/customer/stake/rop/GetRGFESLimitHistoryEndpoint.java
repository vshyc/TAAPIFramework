package customer.stake.rop;

import customer.stake.dto.rgfes.RGFESGetLimitHistoryResponse;
import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;


public class GetRGFESLimitHistoryEndpoint extends BaseEndpoint<GetRGFESLimitHistoryEndpoint, RGFESGetLimitHistoryResponse>{

    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return RGFESGetLimitHistoryResponse.class;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public GetRGFESLimitHistoryEndpoint sendRequest(String sessionId) {
        response = given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .contentType("application/vnd.tipico.regulations.customer.limits-v2+json")
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .accept("application/vnd.tipico.regulations.customer.limits-v2+json")
                .get("/customer/limits/history");


        return null;
    }
}
