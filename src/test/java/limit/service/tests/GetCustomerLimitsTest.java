package limit.service.tests;

import configuration.BaseTest;
import customer.stake.helpers.UserHelper;
import customer.stake.pojo.limits.GetLimitsResponseData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.rop.GetLimitEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GetCustomerLimitsTest extends BaseTest {
    private  String uuid;

    @BeforeEach
    public void setUp(){
        uuid = new UserHelper().createGermanUserAndGetUuid();
    }


    @Test
    public void getCustomerLimitsForNewRegisteredUser(){
        GetLimitsResponseData responseData = new GetLimitEndpoint().sendRequest(uuid).assertRequestStatusCode()
                .getResponseModel();


        LimitsResponseData data = responseData.getLimits().stream()
                .filter(response -> "IMPOSED".equals(response.getOwner()))
                .filter(response -> "turnover".equals(response.getType()))
                .findAny()
                .orElse(null);

        System.out.println(data.getOwner());
    }
}
