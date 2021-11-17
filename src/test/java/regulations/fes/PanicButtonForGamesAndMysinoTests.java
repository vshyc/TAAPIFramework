package regulations.fes;

import static org.assertj.core.api.Assertions.assertThat;

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
import java.sql.SQLException;
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

    @BeforeEach
    @Step("Create and login user for test ")
    void setUp() throws EbetGatewayException {
        userHelper = new UserHelper();
        var createdUser = userHelper.createGermanUserInWebTestApi();
        uuid = userHelper.getUuid(createdUser);
        userHelper.getKYCVerifiedStatus(userHelper.getLogin(createdUser), uuid);
        new TermsAndConditionsHelper().acceptAllDocumentsInTAC(userHelper.getUuid(createdUser));
        var loginHelper = new LoginHelper();
        sessionId = loginHelper.getSessionId(loginHelper.LoginUserToAccountApp(userHelper.getGermanUserName()));
        dbVerification = new OASISDBConnector();
    }

    @DisplayName("Validate panic button for games")
    @Description("Validate panic button for games")
    @Test
    @Tag("RegressionTests")
    void validatePanicButtonForGames() throws EbetGatewayException, SQLException {

        new PostRGFESPanicBtnEndPoint().sendRequestGames(sessionId).assertRequestStatusCode();

        AccountStatusResponseCAS responseCas = userHelper.getAccountStatusFromCus(uuid);

        assertThat(responseCas.getActive()).isTrue();
        assertThat(responseCas.getDeactivationReason()).isEqualTo("DEACTIVATION_PANIC_BUTTON_SELF_EXCLUDED");
        assertThat(responseCas.getMinimalDeactivationDuration()).isEqualTo("PT24H");
        assertThat(isUserWrittenToOasis()).isTrue();

    }

    @DisplayName("Validate panic button for Mysino")
    @Description("Validate panic button for Mysino")
    @Test
    @Tag("RegressionTests")
    void validatePanicButtonForMysino() throws EbetGatewayException, SQLException {

        new PostRGFESPanicBtnEndPoint().sendRequestMysino(sessionId).assertRequestStatusCode();

        AccountStatusResponseCAS responseCas = userHelper.getAccountStatusFromCus(uuid);

        assertThat(responseCas.getActive()).isTrue();
        assertThat(responseCas.getDeactivationReason()).isEqualTo("DEACTIVATION_PANIC_BUTTON_SELF_EXCLUDED");
        assertThat(responseCas.getMinimalDeactivationDuration()).isEqualTo("PT24H");
        assertThat(isUserWrittenToOasis()).isTrue();
    }

    private boolean isUserWrittenToOasis() throws SQLException {
        return dbVerification.executeDmlStatement(
                String.format("Select * from oasis_customer_data_written where customerUuid='%s'", uuid)).next();

    }
}
