package limit.service.tests;

import configuration.BaseTest;
import customer.stake.dto.limits.LimitsResponseData;
import customer.stake.dto.limits.history.GetLimitsHistoryResponseData;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.helpers.UserHelper;
import customer.stake.rop.GetRGLSLimitHistoryEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetLimitHistoryTests extends BaseTest {
    private String uuid;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private GetLimitsHistoryResponseData data;
    private UserHelper userHelper = new UserHelper();

    @BeforeEach
    @Step("Create a user for test ")
    public void setUp() {
        uuid = userHelper.createGermanUserAndGetUuid();
    }


    @DisplayName("Send Get for limit history in Limit Service")
    @Story("Send Get for limit history in Limit Service")
    @Description("Send Get for limit history in Limit Service for new created user with no history")
    @Test
    public void getLimitHistoryForNewUser(){
        new GetRGLSLimitHistoryEndpoint().sendRequest(uuid).assertNoContentStatusCode();
    }

    @Test
    public void getLimitHistory(){
       data = new GetRGLSLimitHistoryEndpoint().sendRequest("572e96b4-9868-44dc-9815-6047ec380aec").getResponseModel();
        Assertions.assertThat(data.getLimitsHistory().get(0).getCustomerUUID()).isEqualTo("572e96b4-9868-44dc-9815-6047ec380aec");
    }

    @Test
    public void getLimitHistoryWithFiltering(){
        data = new GetRGLSLimitHistoryEndpoint().sendRequest("572e96b4-9868-44dc-9815-6047ec380aec","sports",
                Label.tipico, LimitType.DEPOSIT).getResponseModel();
        Assertions.assertThat(data.getLimitsHistory().get(0).getCustomerUUID()).isEqualTo("572e96b4-9868-44dc-9815-6047ec380aec");
    }
}
