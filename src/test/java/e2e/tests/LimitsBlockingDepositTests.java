package e2e.tests;

import com.tipico.ta.reqtest.extension.ReqtestReporterExtension;
import com.tipico.ta.reqtest.extension.TestCaseId;
import configuration.BaseTest;
import customer.stake.enums.CounterType;
import customer.stake.enums.Label;
import customer.stake.exeptions.EbetGatewayException;
import customer.stake.helpers.AddCounterHelper;
import customer.stake.helpers.LoginHelper;
import customer.stake.helpers.PaymentHelper;
import customer.stake.helpers.TermsAndConditionsHelper;
import customer.stake.helpers.UserHelper;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DisplayName("E2E Tests for Limits blocking proper deposits")
@ExtendWith(ReqtestReporterExtension.class)
public class LimitsBlockingDepositTests extends BaseTest {

    private final String amlErrorMsg = "Jetzt Konto verifizieren! Damit du einzahlen kannst, musst du dein " +
            "<a style=\"color:#004a67\" href=\"/#account/verification/options\" target=\"_self\">Konto verifizieren</a>.";
    private final String aml150ErrorMsg = "Jetzt Konto verifizieren! Damit du insgesamt mehr als 150,00 € einzahlen kannst, " +
            "musst du dein <a style=\"color:#004a67\" href=\"/#account/verification/options\" target=\"_self\">Konto " +
            "verifizieren</a>. Alternativ kannst du deinen Einzahlungsbetrag verringern.";
    private final String aml100ErrorMsg = "Verify your account now! In order to deposit more than 100.00€ in total, you need" +
            " to <a style=\"color:#004a67\" href=\"/#account/verification/options\" target=\"_self\">verify your " +
            "account</a>. Alternatively, you can decrease your deposit amount.";
    private final String sddErrorMsg = "Simple EDD Confirmation Required";
    private final String over1kLimitErrorMsg = "Sie haben Ihr Einzahlungslimit von 1.000,00 € pro Monat erreicht. Bitte ändern" +
            " Sie Ihren Einzahlungsbetrag auf 1.000,00 € oder <a href=\"/#account/depositlimit?" +
            "fromRegistration=0\">passen Sie Ihr Limit</a> entsprechend an.";


    private UserHelper userHelper;
    private PaymentHelper paymentHelper;
    private AddCounterHelper addCounterHelper;
    private String jsession;
    private TermsAndConditionsHelper tacHelper;
    private LoginHelper loginHelper;
    private Response tacResponse;
    private Response loginResponse;
    private String sessionId;
    private String slaveId;
    private JsonPath createdUser;
    private String uuid;
    private String username;
    private String id;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    @Step("Create and login user for test ")
    public void setUp() {
        loginHelper = new LoginHelper();
        userHelper = new UserHelper();
        paymentHelper = new PaymentHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        tacHelper = new TermsAndConditionsHelper();
        tacResponse = tacHelper.acceptAllDocumentsInTAC(userHelper.getUuid(createdUser));
        loginResponse = loginHelper.LoginUserToAccountApp(userHelper.getGermanUserName());
        sessionId = loginHelper.getSessionId(loginResponse);
        jsession = loginHelper.getJSessionId(loginResponse);
        slaveId = loginHelper.getSlaveId(loginResponse);
        uuid = userHelper.getUuid(createdUser);
        username = userHelper.getLogin(createdUser);
        id = userHelper.getId(createdUser);
        addCounterHelper = new AddCounterHelper();
    }

    @Test
    @Tag("RegressionTests")
    @TestCaseId(3445)
    @DisplayName("Check if AML Limit is blocking deposit higher then 100 on staging or 150 on TTS")
    public void checkIfAmlLimitIsBlockingDepositIfHigherThenAMLLimit() {
        Response paymentResponse = paymentHelper.payIn(sessionId, jsession, slaveId, "de",
                200, "app-tipico-sports");
        Assertions.assertThat(paymentResponse.getStatusCode()).isEqualTo(500);
        if (envConfig.env().equals("staging")) {
            Assertions.assertThat(paymentResponse.getBody().jsonPath().getString("metadata.globalMessage.message"))
                    .isEqualTo(aml100ErrorMsg);
        } else {
            Assertions.assertThat(paymentResponse.getBody().jsonPath().getString("metadata.globalMessage.message"))
                    .isEqualTo(aml150ErrorMsg);
        }
    }

    @Test
    @Tag("RegressionTests")
    @TestCaseId(3454)
    @DisplayName("Check if AML Limit is not blocking deposits below limit")
    public void checkIfAmlLimitIsNotBlockingDepositIfLowerThenAMLLimit() {
        Response paymentResponse = paymentHelper.payIn(sessionId, jsession, slaveId, "de",
                100, "app-tipico-sports");
        Assertions.assertThat(paymentResponse.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Tag("RegressionTests")
    @TestCaseId(3455)
    @DisplayName("Check if KYC'd user can deposit over AML limit")
    public void checkIf1kLimitIsNotBlockingDepositForKycedCustomersWithDepositHigherThenAMLLimit() throws EbetGatewayException {
        userHelper.getKYCVerifiedStatus(username, uuid);
        Response paymentResponse = paymentHelper.payIn(sessionId, jsession, slaveId, "de",
                500, "app-tipico-sports");
        Assertions.assertThat(paymentResponse.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Tag("RegressionTests")
    @TestCaseId(3456)
    @DisplayName("Check if 1k deposit Limit is blocking deposits over the limit")
    public void checkIf1kLimitIstBlockingDepositForKycedCustomersWithDepositHigherThen1k() throws EbetGatewayException {
        userHelper.getKYCVerifiedStatus(username, uuid);
        Response paymentResponse = paymentHelper.payIn(sessionId, jsession, slaveId, "de",
                1500, "app-tipico-sports");
        Assertions.assertThat(paymentResponse.getStatusCode()).isEqualTo(500);
        if (envConfig.env().equals("staging")) {
            Assertions.assertThat(paymentResponse.getBody().jsonPath().getString("message"))
                    .isEqualTo("Deposit Alert Confirmation Required");
        } else {
            Assertions.assertThat(paymentResponse.getBody().jsonPath().getString("message"))
                    .isEqualTo(sddErrorMsg);
        }
    }

    @Test
    @Tag("RegressionTests")
    @TestCaseId(3457)
    @DisplayName("Check if 1k deposit Limit is blocking deposits lower then the limit with counters in CSS table")
    public void checkIf1kLimitIstBlockingDepositForKycedCustomersWithLowerHigherThen1kButWithCounters() throws EbetGatewayException {
        userHelper.getKYCVerifiedStatus(username, uuid);
        addCounterHelper.addSingleCounterToCustomerStakeService(uuid, id, Label.tipico, CounterType.PAYIN, 400);
        Response paymentResponse = paymentHelper.payIn(sessionId, jsession, slaveId, "de",
                900, "app-tipico-sports");
        Assertions.assertThat(paymentResponse.getStatusCode()).isEqualTo(500);
        if (envConfig.env().equals("staging")) {
            Assertions.assertThat(paymentResponse.getBody().jsonPath().getString("metadata.globalMessage.message"))
                    .isEqualTo(getDeposit1kErrorMsg(400));
        } else {
            Assertions.assertThat(paymentResponse.getBody().jsonPath().getString("message"))
                    .isEqualTo(sddErrorMsg);
        }
    }

    private String getDeposit1kErrorMsg(double counterAmount) {
        double finalValue = 1000.00 - counterAmount;
        return "Sie haben Ihr Einzahlungslimit von 1.000,00 € pro Monat erreicht. Bitte ändern" +
                " Sie Ihren Einzahlungsbetrag auf " + String.format("%,.2f", finalValue).replace('.', ',')
                + " € oder <a href=\"/#account/depositlimit?" +
                "fromRegistration=0\">passen Sie Ihr Limit</a> entsprechend an.";
    }
}
