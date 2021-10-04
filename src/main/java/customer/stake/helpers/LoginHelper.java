package customer.stake.helpers;

import customer.stake.properties.EnvConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginHelper {
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public Response LoginUserToAccountApp(String username) {
        String body = String.format("username=%s&password=Secret123!", username);
        return given().baseUri(envConfig.accountDeUrl()).basePath(envConfig.cafesPath())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .header("X-Forwarded-For", "10.10.9.254").body(body).post();
    }

    public String getSessionId(Response response) {
        return response.getCookie("SESSION_ID");
    }

    public String getJSessionId(Response response) {
        return response.getCookie("JSESSIONID");
    }

    public String getSlaveId(Response response) {
        return response.getCookie("SLAVE_ID");
    }

    public String getLanguage(Response response) {
        return response.getCookie("language");
    }

}
