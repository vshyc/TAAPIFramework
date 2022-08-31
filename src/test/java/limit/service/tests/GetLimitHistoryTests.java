package limit.service.tests;

import configuration.BaseTest;
import customer.stake.dto.limits.history.GetLimitsHistoryResponseData;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Owner;
import customer.stake.enums.Product;
import customer.stake.helpers.HelpersConfig;
import customer.stake.helpers.LimitsHelper;
import customer.stake.helpers.OauthHelper;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("GET Endpoint for Limit History Tests")
public class GetLimitHistoryTests extends BaseTest {
    private String uuid;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private GetLimitsHistoryResponseData data;
    private UserHelper userHelper;
    private final LimitsHelper limitHelper= new LimitsHelper();
    private String accessToken;

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
        GetRGLSLimitHistoryEndpoint data =  new GetRGLSLimitHistoryEndpoint().sendRequest(uuid);
        if (HelpersConfig.isStaging()) {
            data.assertNoContentStatusCode();
        }else {
            data.assertRequestStatusCode();
        }
    }

    @DisplayName("Send Get for limit history in Limit Service")
    @Story("Send Get for limit history in Limit Service with no accessToken")
    @Description("Send Get for limit history in Limit Service for new created user with no history with no auth Token")
    @Test
    public void getLimitHistoryForNewUserNoAuth(){
        accessToken = "";
        new GetRGLSLimitHistoryEndpoint().sendRequest(uuid, accessToken).assertNoAuthRequestStatusCode();
    }

    @DisplayName("Send Get for limit history in Limit Service")
    @Story("Send Get for limit history in Limit Service with no accessToken")
    @Description("Send Get for limit history in Limit Service for new created user with no history with no auth Token")
    @Test
    public void getLimitHistoryForNewUserWrongAuth(){
        accessToken = new OauthHelper().getApplicationToken();
        new GetRGLSLimitHistoryEndpoint().sendRequest(uuid, accessToken).assertForbiddenStatusCode();
    }

    @DisplayName("Send Get for limit history in Limit Service")
    @Story("Send Get for limit history in Limit Service with user Access Token")
    @Description("Send Get for limit history in Limit Service for new created user with no history with and user Token")
    @Test
    public void getLimitHistoryForNewUserUserAuth(){
        accessToken = new OauthHelper().getUserToken(userHelper.getGermanUserName(), userHelper.getGermanUserPassword());
        GetRGLSLimitHistoryEndpoint data = new GetRGLSLimitHistoryEndpoint().sendRequest(uuid, accessToken);
        if (HelpersConfig.isStaging()){
            data.assertNoContentStatusCode();
        }else {
            data.assertRequestStatusCode();
        }
    }

    @DisplayName("Send Get for limit history in Limit Service")
    @Story("Send Get for limit history in Limit Service")
    @Description("Send Get for limit history in Limit Service for new created user with created Limit")
    @Test
    public void getLimitHistory(){
        limitHelper.createLimitWithUserToken(userHelper,uuid,LimitType.DEPOSIT, Owner.PERSONAL,Label.tipico, Product.SPORTS,
                800d, Interval.MONTH);
        data = new GetRGLSLimitHistoryEndpoint().sendRequest(uuid).getResponseModel();
        Assertions.assertThat(data.getLimitsHistory().get(0).getCustomerUUID()).isEqualTo(uuid);
    }

    @DisplayName("Send Get for limit history in Limit Service")
    @Story("Send Get for limit history in Limit Service")
    @Description("Send Get for limit history in Limit Service for new created user with limit, Filtering on non existing Limit")
    @Test
    public void getLimitHistoryWithFiltering(){
        limitHelper.createLimitWithUserToken(userHelper,uuid,LimitType.DEPOSIT, Owner.PERSONAL,Label.tipico, Product.GAMBLING,
                800d, Interval.MONTH);
        new GetRGLSLimitHistoryEndpoint().sendRequest(uuid,Product.SPORTS,
                Label.tipico, LimitType.DEPOSIT).assertNoContentStatusCode();
    }

    @DisplayName("Send Get for limit history in Limit Service")
    @Story("Send Get for limit history in Limit Service")
    @Description("Create and update Limit to lower value and check if both are visible in Limit History on RGLS endpoint")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and updated value = {6}")
    @CsvFileSource(files = "src/test/resources/updateLimitTestData.csv", numLinesToSkip = 1)
    public void getLimitHistoryForUserThatEditedLimit(LimitType type, Owner owner,
            Label label, Product product,
            Double value, Interval interval, Double updatedValue){
        limitHelper.createLimitWithUserToken(userHelper,uuid,type, owner,label,product, value, interval);
        limitHelper.updateLimit(userHelper,uuid,type, owner,label,product, updatedValue, interval);
        data = new GetRGLSLimitHistoryEndpoint().sendRequest(uuid).getResponseModel();
        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(data.getLimitsHistory().stream()
                .filter(response -> uuid.equals(response.getCustomerUUID()))
                .filter(response -> owner.equals(response.getOwner()))
                .filter(response -> type.equals(response.getType()))
                .filter(response -> label.equals(response.getLabel()))
                .filter(response -> product.equals(response.getProduct()))
                .filter(response -> value.equals(response.getCurrent().getValue()))
                .findAny()).isNotEmpty();
        softly.assertThat(data.getLimitsHistory().stream()
                .filter(response -> uuid.equals(response.getCustomerUUID()))
                .filter(response -> owner.equals(response.getOwner()))
                .filter(response -> type.equals(response.getType()))
                .filter(response -> label.equals(response.getLabel()))
                .filter(response -> product.equals(response.getProduct()))
                .filter(response -> updatedValue.equals(response.getCurrent().getValue()))
                .findAny()).isNotEmpty();
        });
    }
}
