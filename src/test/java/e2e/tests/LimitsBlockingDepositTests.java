package e2e.tests;

import configuration.BaseTest;
import customer.stake.helpers.LoginHelper;
import customer.stake.helpers.PaymentHelper;
import customer.stake.helpers.TermsAndConditionsHelper;
import customer.stake.helpers.UserHelper;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LimitsBlockingDepositTests extends BaseTest {

    private String amlErrorMsg = "Jetzt Konto verifizieren! Damit du einzahlen kannst, musst du dein " +
            "<a style=\"color:#004a67\" href=\"/#account/verification/options\" target=\"_self\">Konto verifizieren</a>.";
    private String aml150ErrorMsg = "Jetzt Konto verifizieren! Damit du insgesamt mehr als 150,00 € einzahlen kannst, " +
            "musst du dein <a style=\"color:#004a67\" href=\"/#account/verification/options\" target=\"_self\">Konto " +
            "verifizieren</a>. Alternativ kannst du deinen Einzahlungsbetrag verringern.";
    private String aml100ErrorMsg ="Verify your account now! In order to deposit more than 100.00€ in total, you need" +
            " to <a style=\"color:#004a67\" href=\"/#account/verification/options\" target=\"_self\">verify your " +
            "account</a>. Alternatively, you can decrease your deposit amount.";

    private UserHelper userHelper;
    private PaymentHelper paymentHelper;
    private String jsession;
    private TermsAndConditionsHelper tacHelper;
    private LoginHelper loginHelper;
    private Response tacResponse;
    private Response loginResponse;
    private String sessionId;
    private String slaveId;
    private JsonPath createdUser;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    @Step("Create and login user for test ")
    public void setUp(){
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

    }

    @Test
    public void checkIfAmlLimitIsBlockingDepositIfHigherThenAMLLimit(){
        Response paymentResponse = paymentHelper.payIn(sessionId,jsession,slaveId,"de",200,"app-tipico-sports");
        Assertions.assertThat(paymentResponse.getStatusCode()).isEqualTo(500);
        if (envConfig.env().equals("staging")){
            Assertions.assertThat(paymentResponse.getBody().jsonPath().getString("metadata.globalMessage.message"))
                    .isEqualTo(aml100ErrorMsg);}
        else {
            Assertions.assertThat(paymentResponse.getBody().jsonPath().getString("metadata.globalMessage.message"))
                    .isEqualTo(aml150ErrorMsg);}
    }

    @Test
    public void checkIfAmlLimitIsNotBlockingDepositIfLowerThenAMLLimit(){
        Response paymentResponse = paymentHelper.payIn(sessionId,jsession,slaveId,"de",100,"app-tipico-sports");
        Assertions.assertThat(paymentResponse.getStatusCode()).isEqualTo(200);
    }
}
