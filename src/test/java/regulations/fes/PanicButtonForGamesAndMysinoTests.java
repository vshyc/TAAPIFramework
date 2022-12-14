package regulations.fes;

import com.tipico.ta.reqtest.extension.TestCaseId;
import configuration.BaseTest;
import customer.stake.db.OASISDBConnector;
import customer.stake.dto.cus.AccountStatusResponseCAS;
import customer.stake.exeptions.EbetGatewayException;
import customer.stake.helpers.LoginHelper;
import customer.stake.helpers.TermsAndConditionsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.rop.PostRGFESPanicBtnEndPoint;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import java.sql.SQLException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Validate panic button end point for Games and Mysino")
class PanicButtonForGamesAndMysinoTests extends BaseTest {

    private String sessionId;
    private String uuid;
    private OASISDBConnector dbVerification;
    private UserHelper userHelper;
    private SoftAssertions sa;

    @BeforeEach
    @Step("Create and login user for test ")
    void setUp() throws EbetGatewayException {
        userHelper = new UserHelper();
        JsonPath createdUser = userHelper.createGermanUserInWebTestApi();
        uuid = userHelper.getUuid(createdUser);
        userHelper.getKYCVerifiedStatus(userHelper.getLogin(createdUser), uuid);
        new TermsAndConditionsHelper().acceptAllDocumentsInTAC(userHelper.getUuid(createdUser));
        LoginHelper loginHelper = new LoginHelper();
        sessionId = loginHelper.getSessionId(loginHelper.LoginUserToAccountApp(userHelper.getGermanUserName()));
        dbVerification = new OASISDBConnector();
        sa = new SoftAssertions();
    }

    @DisplayName("Validate panic button for games")
    @Description("Validate panic button for games")
    @Test
    @Tag("RegressionTests")
    @Tag("PanicButton")
    @TestCaseId(3687)
    void validatePanicButtonForGames() throws EbetGatewayException, SQLException {

        new PostRGFESPanicBtnEndPoint().sendRequestGames(sessionId).assertRequestStatusCode();

        AccountStatusResponseCAS responseCas = userHelper.getAccountStatusFromCus(uuid);

        sa.assertThat(responseCas.getCustomerId()).as("Validate that the response is for the currect customer").isEqualTo(uuid);
        sa.assertThat(responseCas.getActive()).as("Validate account status is active.").isTrue();
        sa.assertThat(responseCas.getDeactivationReason()).as("Validate deactivation reason is Panic button.")
                .isEqualTo("DEACTIVATION_PANIC_BUTTON_SELF_EXCLUDED");
        sa.assertThat(responseCas.getMinimalDeactivationDuration()).as("Validate deactivation period is 24 hours").isEqualTo("PT24H");
        sa.assertThat(isUserWrittenToOasis()).as("Validate user is written in Oasis db").isTrue();
        sa.assertAll();
    }

    @DisplayName("Validate panic button for Mysino")
    @Description("Validate panic button for Mysino")
    @Test
    @Tag("RegressionTests")
    @Tag("PanicButton")
    @TestCaseId(3688)
    void validatePanicButtonForMysino() throws EbetGatewayException, SQLException {

        new PostRGFESPanicBtnEndPoint().sendRequestMysino(sessionId).assertRequestStatusCode();

        AccountStatusResponseCAS responseCas = userHelper.getAccountStatusFromCus(uuid);

        sa.assertThat(responseCas.getCustomerId()).as("Validate that the response is for the currect customer").isEqualTo(uuid);
        sa.assertThat(responseCas.getActive()).as("Validate account status is active.").isTrue();
        sa.assertThat(responseCas.getDeactivationReason()).as("Validate deactivation reason is Panic button.")
                .isEqualTo("DEACTIVATION_PANIC_BUTTON_SELF_EXCLUDED");
        sa.assertThat(responseCas.getMinimalDeactivationDuration()).as("Validate deactivation period is 24 hours").isEqualTo("PT24H");
        sa.assertThat(isUserWrittenToOasis()).as("Validate user is written in Oasis db").isTrue();
        sa.assertAll();
    }

    private boolean isUserWrittenToOasis() throws SQLException {
        return dbVerification.executeDmlStatement(
                String.format("Select * from oasis_customer_data_written where customerUuid='%s'", uuid),
                OASISDBConnector.getJdbcConnectionPool()).next();
    }
}
