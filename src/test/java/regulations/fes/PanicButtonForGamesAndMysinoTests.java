package regulations.fes;

import static org.assertj.core.api.Assertions.assertThat;

import configuration.BaseTest;
import customer.stake.dto.models.AccountStatusResponseCAS;
import customer.stake.exeptions.EbetGatewayException;
import customer.stake.helpers.LoginHelper;
import customer.stake.helpers.TermsAndConditionsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.rop.PostRGFESPanicBtnEndPoint;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("Validate panic button end point for Games and Mysino")
class PanicButtonForGamesAndMysinoTests extends BaseTest {

    private UserHelper userHelper;
    private TermsAndConditionsHelper tacHelper;
    private LoginHelper loginHelper;
    private Response tacResponse;
    private String sessionId;
    private JsonPath createdUser;
    private boolean isStaging;
    private String uuid;
    private String username;

    @BeforeEach
    @Step("Create and login user for test ")
    void setUp() throws EbetGatewayException {
        loginHelper = new LoginHelper();
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        uuid = userHelper.getUuid(createdUser);
        username = userHelper.getLogin(createdUser);
        userHelper.getKYCVerifiedStatus(username, uuid);
        tacHelper = new TermsAndConditionsHelper();
        tacResponse = tacHelper.acceptAllDocumentsInTAC(userHelper.getUuid(createdUser));
        sessionId = loginHelper.getSessionId(loginHelper.LoginUserToAccountApp(userHelper.getGermanUserName()));
        isStaging = envConfig.env().equals("staging");

    }

    @DisplayName("Validate panic button for games")
    @Description("Validate panic button for games")
    @Test
    @Tag("RegressionTests")
    void validatePanicButtonForGames() throws EbetGatewayException {

        Response response = new PostRGFESPanicBtnEndPoint().sendRequestGames(sessionId)
                .assertRequestStatusCode().getResponse();

        AccountStatusResponseCAS responseCas = userHelper.getAccountStatusFromCus(uuid);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(responseCas.getActive())
                        .as("Status is not correct.").isTrue(),
                () -> assertThat(responseCas.getDeactivationReason())
                        .as("Activation reason is not correct.").isEqualTo("DEACTIVATION_PANIC_BUTTON_SELF_EXCLUDED")
        );
    }

    @DisplayName("Validate panic button for Mysino")
    @Description("Validate panic button for Mysino")
    @Test
    @Tag("RegressionTests")
    void validatePanicButtonForMysino() throws EbetGatewayException {

        Response response = new PostRGFESPanicBtnEndPoint().sendRequestMysino(sessionId)
                .assertRequestStatusCode().getResponse();

        AccountStatusResponseCAS responseCas = userHelper.getAccountStatusFromCus(uuid);

        org.junit.jupiter.api.Assertions.assertAll(
                () -> assertThat(responseCas.getActive())
                        .as("Status is not correct.").isTrue(),
                () -> assertThat(responseCas.getDeactivationReason())
                        .as("Activation reason is not correct.").isEqualTo("DEACTIVATION_PANIC_BUTTON_SELF_EXCLUDED")
        );
    }
}
