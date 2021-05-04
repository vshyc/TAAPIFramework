package limit.service.tests;

import configuration.BaseTest;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.pojo.limits.GetLimitsResponseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetCustomerLimitsTest extends BaseTest {
    private  String uuid;

    @BeforeEach
    public void setUp(){
        uuid = new UserHelper().createGermanUserAndGetUuid();

    }


    @Test
    public void getCustomerLimitsForNewRegisteredUser(){
        given().auth().oauth2(new OauthHelper().getApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath()).when().get(uuid+"/limits/").then()
                .statusCode(200).extract().as(GetLimitsResponseData.class);
    }
}
