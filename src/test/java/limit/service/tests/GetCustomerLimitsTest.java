package limit.service.tests;

import configuration.BaseTest;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import customer.stake.enums.TypeEnum;
import customer.stake.helpers.GetLimitsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.rop.GetLimitEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("GET Endpoint for Limit service Tests")
public class GetCustomerLimitsTest extends BaseTest {
    private  String uuid;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private LimitsResponseData data;

    @BeforeEach
    public void setUp(){
        uuid = new UserHelper().createGermanUserAndGetUuid();
    }

    @DisplayName("Check if new created user have imposed turnover limit")
    @Story("Check if new created user have imposed turnover limit")
    @Description("Check if new created user have imposed turnover limit")
    @Test
    public void getCustomerLimitsForNewRegisteredUserTest(){

        try {
             data = new GetLimitsHelper().checkIfLimitExistForUser(uuid,
                     OwnerEnum.PERSONAL,
                     TypeEnum.TURNOVER,
                     LabelEnums.tipico);
             Assertions.assertThat(data.getCurrent().getValue()).isEqualTo(1000f);
        }catch (NullPointerException e){
            Assertions.fail("Turnover IMPOSED limit not exist in DB after registration");
            log.error("Turnover IMPOSED limit not exist in DB ");
        }
    }

    @DisplayName("Check if call to Limit Service with no auth will respond with 401 Error code")
    @Test
    public void getCustomerLimitsWithNoAuthTest(){
        new GetLimitEndpoint().sendRequestWithNoAuth(uuid).assertNoAuthRequestStatusCode();
    }
}
