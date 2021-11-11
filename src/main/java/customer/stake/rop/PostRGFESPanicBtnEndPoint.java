package customer.stake.rop;

import static customer.stake.constants.RequestPaths.PANIC_BUTTON_REQUEST_PATH;
import static io.restassured.RestAssured.given;

import customer.stake.dto.rgfes.RGFESCreateLimitResponse;
import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;
import java.lang.reflect.Type;
import org.apache.http.HttpStatus;


public class PostRGFESPanicBtnEndPoint extends BaseEndpoint<PostRGFESPanicBtnEndPoint, RGFESCreateLimitResponse> {
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return RGFESCreateLimitResponse.class;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public PostRGFESPanicBtnEndPoint sendRequestGames(String sessionId) {
        response = given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.panicBtnGames())
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .post(PANIC_BUTTON_REQUEST_PATH+"app-tipico-games");
        return this;
    }

    public PostRGFESPanicBtnEndPoint sendRequestMysino(String sessionId) {
        response = given().baseUri(envConfig.mysinobaseUrl()).basePath(envConfig.panicBtnMysino())
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .post(PANIC_BUTTON_REQUEST_PATH+"app-mysino-games");
        return this;
    }
}