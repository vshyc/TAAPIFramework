package limit.service.tests;

import configuration.BaseTest;
import customer.stake.rop.PostLimitEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import customer.stake.helpers.UserHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Response response = new PostLimitEndpoint().sendRequest(uuid).assertRequestStatusCode().getResponse();

        Assertions.assertThat(response.getHeader("location")).describedAs("check if location header " +
                "contains path to limit service").contains("/v1/limits-service/customers");
        Assertions.assertThat(response.getHeader("location")).describedAs("check if location header " +
                "contains user UUID").contains(uuid);

    }


}
