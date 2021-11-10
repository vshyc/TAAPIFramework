package limit.service.tests;

import configuration.BaseTest;
import customer.stake.enums.Label;
import customer.stake.enums.Owner;
import customer.stake.enums.LimitType;
import customer.stake.helpers.GetLimitsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.dto.limits.LimitsResponseData;
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
    private String uuid;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private LimitsResponseData data;
    private UserHelper userHelper = new UserHelper();

    @BeforeEach
    public void setUp() {
        uuid = userHelper.createGermanUserAndGetUuid();
    }

    @DisplayName("Check if new created user have imposed turnover limit")
    @Story("Check if new created user have imposed turnover limit")
    @Description("Check if new created user have imposed turnover limit")
    @Test
    public void getCustomerLimitsForNewRegisteredUserTest() {

        try {
            data = new GetLimitsHelper().checkIfLimitExistForUser(uuid,
                    Owner.PERSONAL,
                    LimitType.TURNOVER,
                    Label.tipico);
            Assertions.assertThat(data.getCurrent().getValue()).isEqualTo(1000f);
        } catch (NullPointerException e) {
            if (envConfig.env().equals("staging")) {
                log.info("On staging we are not imposing Turnover Limit yet");
                Assertions.assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> data.getCurrent().getValue());
            } else {
                Assertions.fail("Turnover IMPOSED limit not exist in DB after registration");
                log.error("Turnover IMPOSED limit not exist in DB ");
            }
        }
    }

    @DisplayName("Check if call to Limit Service with no auth will respond with 401 Error code")
    @Test
    public void getCustomerLimitsWithNoAuthTest() {
        new GetLimitEndpoint().sendRequestWithNoAuth(uuid).assertNoAuthRequestStatusCode();
    }

}
