package limit.service.tests;

import configuration.BaseTest;
import customer.stake.dto.limits.history.GetLimitsHistoryResponseData;
import customer.stake.dto.limits.history.LimitsHistory;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Owner;
import customer.stake.helpers.LimitsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.rop.GetRGLSLimitHistoryEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetLimitHistoryTests extends BaseTest {
    private String uuid;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private GetLimitsHistoryResponseData data;
    private UserHelper userHelper;
    private final LimitsHelper limitHelper= new LimitsHelper();

    @BeforeEach
    @Step("Create a user for test ")
    public void setUp() {
        userHelper = new UserHelper();
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
        limitHelper.createLimitWithUserToken(userHelper,uuid,LimitType.DEPOSIT, Owner.PERSONAL,Label.tipico,"sports",
                800d, Interval.MONTH);
        data = new GetRGLSLimitHistoryEndpoint().sendRequest(uuid).getResponseModel();
        Assertions.assertThat(data.getLimitsHistory().get(0).getCustomerUUID()).isEqualTo(uuid);
    }

    @Test
    public void getLimitHistoryWithFiltering(){
        new GetRGLSLimitHistoryEndpoint().sendRequest(uuid,"sports",
                Label.tipico, LimitType.DEPOSIT).assertNoContentStatusCode();
    }

    @Test
    public void getLimitHistoryForUserThatEditedLimit(){
        limitHelper.createLimitWithUserToken(userHelper,uuid,LimitType.DEPOSIT, Owner.PERSONAL,Label.tipico,"sports",
                800d, Interval.MONTH);
        limitHelper.updateLimit(userHelper,uuid,LimitType.DEPOSIT, Owner.PERSONAL,Label.tipico,"sports",
                700d, Interval.MONTH);
        data = new GetRGLSLimitHistoryEndpoint().sendRequest(uuid).getResponseModel();
        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(data.getLimitsHistory().stream()
                .filter(response -> uuid.equals(response.getCustomerUUID()))
                .filter(response -> Owner.PERSONAL.equals(response.getOwner()))
                .filter(response -> LimitType.DEPOSIT.equals(response.getType()))
                .filter(response -> Label.tipico.equals(response.getLabel()))
                .filter(response -> "sports".equals(response.getProduct()))
                .filter(response -> Double.valueOf(700d).equals(response.getCurrent().getValue()))
                .findAny()).isNotEmpty();
        softly.assertThat(data.getLimitsHistory().stream()
                .filter(response -> uuid.equals(response.getCustomerUUID()))
                .filter(response -> Owner.PERSONAL.equals(response.getOwner()))
                .filter(response -> LimitType.DEPOSIT.equals(response.getType()))
                .filter(response -> Label.tipico.equals(response.getLabel()))
                .filter(response -> "sports".equals(response.getProduct()))
                .filter(response -> Double.valueOf(800d).equals(response.getCurrent().getValue()))
                .findAny()).isNotEmpty();
        });
    }
}
