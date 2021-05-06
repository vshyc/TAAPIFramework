package configuration;

import customer.stake.properties.EnvConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static EnvConfig envConfig;

    @BeforeAll
    public static void baseTestSetUp() {
        ConfigFactory.setProperty("file","${env}");
        envConfig = ConfigFactory.create(EnvConfig.class);
        RestAssured.baseURI = envConfig.baseUri();
        RestAssured.basePath = envConfig.basePath();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
    }
}
