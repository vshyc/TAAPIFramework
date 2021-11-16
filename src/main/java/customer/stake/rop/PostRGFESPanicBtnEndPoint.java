package customer.stake.rop;

import static customer.stake.constants.RequestPaths.PANIC_BUTTON_GAMES_PATH;
import static customer.stake.constants.RequestPaths.PANIC_BUTTON_MYSINO_PATH;
import static io.restassured.RestAssured.given;

import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;
import io.restassured.response.Response;
import java.lang.reflect.Type;
import org.apache.http.HttpStatus;


public class PostRGFESPanicBtnEndPoint extends BaseEndpoint<PostRGFESPanicBtnEndPoint, Response> {

    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return null;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public PostRGFESPanicBtnEndPoint sendRequestGames(String sessionId) {
        response = given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.rgfesPath())
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .post(PANIC_BUTTON_GAMES_PATH);
        return this;
    }

    public PostRGFESPanicBtnEndPoint sendRequestMysino(String sessionId) {
        response = given().baseUri(envConfig.mysinoBaseUrl()).basePath(envConfig.rgfesPath())
                .header("Cookie", String.format("SESSION_ID=%s", sessionId))
                .post(PANIC_BUTTON_MYSINO_PATH);
        return this;
    }
}