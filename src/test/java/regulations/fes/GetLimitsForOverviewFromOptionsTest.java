package regulations.fes;

import configuration.BaseTest;
import customer.stake.helpers.LoginHelper;
import customer.stake.helpers.TermsAndConditionsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.pojo.rgfes.RGFESGetOptionServiceLimitResponse;
import customer.stake.rop.GetRGFESLimitEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetLimitsForOverviewFromOptionsTest extends BaseTest {

    private UserHelper userHelper;
    private TermsAndConditionsHelper tacHelper;
    private LoginHelper loginHelper;
    private Response tacResponse;
    private String sessionId;
    private JsonPath createdUser;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    @Step("Create and login user for test ")
    public void setUp(){
        loginHelper = new LoginHelper();
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        tacHelper = new TermsAndConditionsHelper();
        tacResponse = tacHelper.acceptAllDocumentsInTAC(userHelper.getUuid(createdUser));
        sessionId = loginHelper.getSessionId(loginHelper.LoginUserToAccountApp(userHelper.getGermanUserName()));
    }

    @Feature("Getting Limits from RGFES")
    @DisplayName("Getting Limits from RGFES")
    @Description("Getting Limits by RGFES from Option Service")
    @Test
    public void getLimitsForOverview(){
        RGFESGetOptionServiceLimitResponse response = new GetRGFESLimitEndpoint().sendRequest(sessionId).assertRequestStatusCode()
                .getModelTypeForLimitServiceResponse();
        Assertions.assertThat(response.getCustomLimits().getDepositLimits().get(0).getName()).isEqualTo("max-payin");
        Assertions.assertThat(response.getAccountLimits().getDepositLimits().get(0).getName()).isEqualTo("max-payin-aml");
    }

    @Feature("Getting Limits from RGFES")
    @DisplayName("Getting Limits from RGFES no auth")
    @Description("Getting Limits by RGFES from Option Service without providing proper sessionId")
    @Test
    public void getLimitsNoAuthForOverview(){
        Response response = new GetRGFESLimitEndpoint().sendRequest("").getResponse();
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
    }
}