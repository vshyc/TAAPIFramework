package configuration;

import customer.stake.helpers.HelpersConfig;
import customer.stake.properties.EnvConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.BeforeAll;


public class BaseTest {
    public static EnvConfig envConfig = HelpersConfig.createConfiguration();

    @BeforeAll
    public static void baseTestSetUp() {
        RestAssured.baseURI = envConfig.baseUri();
        RestAssured.basePath = envConfig.basePath();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
    }
}
