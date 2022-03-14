package customer.stake.rop;

import customer.stake.dto.limits.history.GetLimitsHistoryResponseData;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Product;
import customer.stake.helpers.HelpersConfig;
import customer.stake.helpers.OauthHelper;
import customer.stake.properties.EnvConfig;
import customer.stake.request.configuration.RequestConfigurationBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GetRGLSLimitHistoryEndpoint extends BaseEndpoint<GetRGLSLimitHistoryEndpoint, GetLimitsHistoryResponseData> {

    private final EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return GetLimitsHistoryResponseData.class;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public GetRGLSLimitHistoryEndpoint sendRequest(String uuid) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().oauth2(new OauthHelper().getTMCApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .contentType(ContentType.JSON)
                .when().get("customers/{customerUuid}/limits/history", uuid);
        return this;
    }

    public GetRGLSLimitHistoryEndpoint sendRequest(String uuid , String accessToken) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().oauth2(accessToken)
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .contentType(ContentType.JSON)
                .when().get("customers/{customerUuid}/limits/history", uuid);
        return this;
    }

    public GetRGLSLimitHistoryEndpoint sendRequest(String uuid, Product product) {
        HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("customerUuid",uuid);
        pathParams.put("products", product);
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().oauth2(new OauthHelper().getTMCApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParams(pathParams)
                .when().get("customers/{customerUuid}/limits/history?products={products}");
        return this;
    }

    public GetRGLSLimitHistoryEndpoint sendRequest(String uuid, Product product, Label label) {
        HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("customerUuid",uuid);
        pathParams.put("products", product);
        pathParams.put("labels",label);
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().oauth2(new OauthHelper().getTMCApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .contentType(ContentType.JSON)
                .pathParams(pathParams)
                .when().get("customers/{customerUuid}/limits/history?products={products}&labels={labels}");
        return this;
    }

    public GetRGLSLimitHistoryEndpoint sendRequest(String uuid, Product product, Label label, LimitType limitType) {
        HashMap<String, Object> pathParams = new HashMap<>();
        pathParams.put("customerUuid",uuid);
        pathParams.put("products", product);
        pathParams.put("labels",label);
        pathParams.put("limitTypes",limitType);
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().oauth2(new OauthHelper().getTMCApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .pathParams(pathParams)
                .when().get("customers/{customerUuid}/limits/history?products={products}&labels={labels}&limitTypes={limitTypes}");
        return this;
    }
}
