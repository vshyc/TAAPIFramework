package limit.service.tests;

import configuration.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@DisplayName("POST Endpoint for Limit service Tests")
public class PostCreateLimitsTest extends BaseTest {

    private  String uuid;

    @BeforeEach
    public  void setUp(){
        uuid = new UserHelper().createGermanUserAndGetUuid();

    }
    @DisplayName("Send POST to create limit")
    @Story("Send POST to create limit")
    @Description("Send POST to create limit")
    @Test
    public void postCreateLimitTestShouldReturnProperLocation(){
        Response response =given().baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath())
                .auth().oauth2(new OauthHelper().getApplicationToken())
                .post("/customers/{customerUuid}/limits",uuid)
                .then().statusCode(201).extract().response();
        Assertions.assertThat(response.getHeader("location")).contains("/v1/limits-service/customers");

    }


}
