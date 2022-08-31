package customer.stake.rop;

import customer.stake.dto.rgfes.RGFESGetLimitHistoryResponse;
import customer.stake.dto.rgfes.RGFESGetLimitHistoryWithLRCResponse;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Product;
import customer.stake.helpers.HelpersConfig;
import customer.stake.helpers.LRCHelper;
import customer.stake.properties.EnvConfig;
import customer.stake.request.configuration.RequestConfigurationBuilder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static customer.stake.constants.MediaTypes.LIMIT_HISTORY_JSON;
import static customer.stake.constants.MediaTypes.LIMIT_SERVICE_JSON;
import static customer.stake.constants.RequestPaths.LIMIT_HISTORY_REQUEST_PATH;
import static io.restassured.RestAssured.given;


public class GetRGFESLimitHistoryEndpoint extends BaseEndpoint<GetRGFESLimitHistoryEndpoint, RGFESGetLimitHistoryResponse>{

    private final EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return RGFESGetLimitHistoryResponse.class;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public RGFESGetLimitHistoryWithLRCResponse getModelTypeForLimitHistoryWithLRC() {
        return response.as(RGFESGetLimitHistoryWithLRCResponse.class);
    }

    public GetRGFESLimitHistoryEndpoint sendRequest(String sessionId) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .contentType(LIMIT_SERVICE_JSON)
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .accept(LIMIT_SERVICE_JSON)
                .get(LIMIT_HISTORY_REQUEST_PATH);
        return this;
    }
    public GetRGFESLimitHistoryEndpoint sendRequest(String sessionId, Product product , Label label, LimitType type) {
        LRCHelper lrcHelper = new LRCHelper();
        String LicenceRegionContext = lrcHelper.createLRC(product,label);
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .contentType(LIMIT_HISTORY_JSON)
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .header("tipico-license-region-context",LicenceRegionContext)
                .accept(LIMIT_HISTORY_JSON)
                .get(LIMIT_HISTORY_REQUEST_PATH+"?limitTypes={limitType}",type);
        return this;
    }
}
