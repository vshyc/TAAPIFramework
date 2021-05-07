package customer.stake.rop;

import customer.stake.helpers.HelpersConfig;
import customer.stake.helpers.OauthHelper;
import customer.stake.properties.EnvConfig;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class PostLimitEndpoint extends BaseEndpoint<PostLimitEndpoint, Response>{

    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return null;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_CREATED;
    }

    public PostLimitEndpoint sendRequest(String uuid){
        response = given().baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .auth().oauth2(new OauthHelper().getApplicationToken())
                .post("/customers/{customerUuid}/limits",uuid);
        return this;
    }

}
