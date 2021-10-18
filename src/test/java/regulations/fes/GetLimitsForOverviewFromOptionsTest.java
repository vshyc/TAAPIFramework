package regulations.fes;

import com.tipico.ta.reqtest.extension.TestCaseId;
import configuration.BaseTest;
import customer.stake.enums.IntervalEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.LimitTypeEnum;
import customer.stake.enums.OwnerEnum;
import customer.stake.helpers.LoginHelper;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.TermsAndConditionsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.dto.limits.LimitCreationData;
import customer.stake.dto.limits.LimitsResponseData;
import customer.stake.dto.rgfes.RGFESGetLimitServiceLimitResponse;
import customer.stake.dto.rgfes.RGFESGetOptionServiceLimitResponse;
import customer.stake.rop.GetRGFESLimitEndpoint;
import customer.stake.rop.PutLimitEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("RGFES Geting Limits Tests")
public class GetLimitsForOverviewFromOptionsTest extends BaseTest {

    private UserHelper userHelper;
    private TermsAndConditionsHelper tacHelper;
    private LoginHelper loginHelper;
    private Response tacResponse;
    private String sessionId;
    private JsonPath createdUser;
    private String uuid;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private String acceptV3Header = "application/vnd.tipico.regulations.customer.limits-v3+json";
    private boolean isStaging;

    @BeforeEach
    @Step("Create and login user for test ")
    public void setUp() {
        loginHelper = new LoginHelper();
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        tacHelper = new TermsAndConditionsHelper();
        tacResponse = tacHelper.acceptAllDocumentsInTAC(userHelper.getUuid(createdUser));
        sessionId = loginHelper.getSessionId(loginHelper.LoginUserToAccountApp(userHelper.getGermanUserName()));
        uuid = userHelper.getUuid(createdUser);
        isStaging = envConfig.env().equals("staging");
    }

    @Feature("Getting Limits from RGFES V2")
    @DisplayName("Getting Limits from RGFES V2")
    @Description("Getting Limits by RGFES from Option Service")
    @Test
    @Tag("RegressionTests")
    @TestCaseId(3458)
    public void getLimitsForOverview() {
        RGFESGetOptionServiceLimitResponse response = new GetRGFESLimitEndpoint().sendRequest(sessionId).assertRequestStatusCode()
                .getResponseModel();
        Assertions.assertThat(response.getCustomLimits().getDepositLimits().get(0).getName()).isEqualTo("max-payin");
        Assertions.assertThat(response.getAccountLimits().getDepositLimits().get(0).getName()).isEqualTo("max-payin-aml");
    }

    @Feature("Getting Limits from RGFES V3")
    @DisplayName("Getting Limits from RGFES V3")
    @Description("Getting Limits by RGFES from Limit Service")
    @Test
    @Tag("RegressionTests")
    @TestCaseId(3459)
    public void getLimitsForOverviewFromLimitService() {
        RGFESGetLimitServiceLimitResponse response = new GetRGFESLimitEndpoint().sendRequest(sessionId, acceptV3Header)
                .assertRequestStatusCode()
                .getModelTypeForLimitServiceResponse();
        if (!isStaging) {
            Assertions.assertThat(response.getSports().getTurnover().getRemaining()).isEqualTo(1000.0);
            Assertions.assertThat(response.getSports().getTurnover().getCurrent().getValue()).isEqualTo(1000.0);
            Assertions.assertThat(response.getSports().getTurnover().getCurrent().getInterval()).isEqualTo(IntervalEnum.MONTH);
        }
    }

    @Feature("Getting Limits from RGFES V3")
    @DisplayName("Creating and getting Limits from RGFES V3")
    @Description("Creating and getting Limits by RGFES from Limit Service")
    @ParameterizedTest(name = "{index} -> Creating a limit type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and checking if limit is visible in GET call")
    @CsvFileSource(files = "src/test/resources/getNewCreatedLimit.csv", numLinesToSkip = 1)
    @Tag("RegressionTests")
    @TestCaseId(3460)
    public void getNewCreatedLimitForOverviewFromLimitService(LimitTypeEnum limitType, OwnerEnum owner, LabelEnums label,
                                                              String product, Double value, IntervalEnum interval) {
        if (!isStaging && limitType == LimitTypeEnum.TURNOVER) {
            Assertions.assertThat(true).as("The turnover limit exist on registration so " +
                    "creating it by RGFES is not posible");
        } else {
            createLimitWithApplicationToken(limitType, owner, label, product, value, interval);
            RGFESGetLimitServiceLimitResponse response = new GetRGFESLimitEndpoint().sendRequest(sessionId, acceptV3Header)
                    .assertRequestStatusCode()
                    .getModelTypeForLimitServiceResponse();
            Assertions.assertThat(response.getSports().getLimitType(limitType).getRemaining()).isEqualTo(value);
            Assertions.assertThat(response.getSports().getLimitType(limitType).getCurrent().getValue()).isEqualTo(value);
            Assertions.assertThat(response.getSports().getLimitType(limitType).getCurrent().getInterval()).isEqualTo(interval);
        }
    }

    @Feature("Getting Limits from RGFES")
    @DisplayName("Getting Limits from RGFES no auth")
    @Description("Getting Limits by RGFES from Option Service without providing proper sessionId")
    @Test
    @Tag("RegressionTests")
    @TestCaseId(3461)
    public void getLimitsNoAuthForOverview() {
        new GetRGFESLimitEndpoint().sendRequest("").assertNoAuthRequestStatusCode();
    }

    @Step("Sending a call to Limit Service with Application Token to create Limit")
    private LimitsResponseData createLimitWithApplicationToken(LimitTypeEnum type, OwnerEnum owner,
                                                               LabelEnums label, String product,
                                                               Double value, IntervalEnum interval) {
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToCreateNewLimit(body, new OauthHelper().getApplicationToken(), uuid)
                .assertRequestStatusCode().getResponseModel();
    }
}