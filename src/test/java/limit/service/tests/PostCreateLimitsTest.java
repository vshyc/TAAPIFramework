package limit.service.tests;

import configuration.BaseTest;
import customer.stake.properties.EnvConfig;
import io.restassured.response.Response;
import customer.stake.helpers.LocationHeaderHelper;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import org.aeonbits.owner.ConfigFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostCreateLimitsTest extends BaseTest {

    private  String uuid;

    @BeforeEach
    public  void setUp(){
        uuid = new UserHelper().createGermanUserAndGetUuid();

    }

    @Test
    public void postCreateLimitTestShouldReturnProperLocation(){
        Response response =given().baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .auth().oauth2(new OauthHelper().getApplicationToken())
                .post("/customers/{customerUuid}/limits",uuid)
                .then().statusCode(201).extract().response();
        Assertions.assertThat(response.getHeader("location")).contains("/v1/limits-service/customers");

    }


}
