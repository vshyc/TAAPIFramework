package regulations.fes;

import com.tipico.ta.reqtest.extension.TestCaseId;
import configuration.BaseTest;
import customer.stake.dto.rgfes.RGFESGetLimitHistoryResponse;
import customer.stake.dto.rgfes.RGFESGetLimitHistoryWithLCRResponse;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Product;
import customer.stake.helpers.LoginHelper;
import customer.stake.helpers.TermsAndConditionsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.rop.GetRGFESLimitHistoryEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("RGFES Geting Limits History Tests")
public class GetRGFESLimitHistoryTests extends BaseTest {

    private UserHelper userHelper;
    private TermsAndConditionsHelper tacHelper;
    private LoginHelper loginHelper;
    private Response tacResponse;
    private String sessionId;
    private JsonPath createdUser;
    protected final Logger log = LoggerFactory.getLogger(getClass());
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
        isStaging = envConfig.env().equals("staging");
    }

    @Feature("Limit History")
    @DisplayName("Get Limit History")
    @Description("Get Limit History")
    @Test
    @Tag("RegressionTests")
    @TestCaseId(3466)
    public void getLimitHistoryTest(){
        RGFESGetLimitHistoryResponse response = new GetRGFESLimitHistoryEndpoint().sendRequest(sessionId)
                .assertRequestStatusCode().getResponseModel();
        if (isStaging){
            SoftAssertions.assertSoftly(softly-> {
                softly.assertThat(response.getHistory().get(0).getLimitType()).isEqualTo(LimitType.DEPOSIT);
                softly.assertThat(response.getHistory().get(0).getValue()).isEqualTo(1000d);
                softly.assertThat(response.getHistory().get(0).getInterval()).isEqualTo(Interval.MONTH);
                softly.assertThat(response.getHistory().get(0).getProduct()).isEqualTo(Product.SPORTS);
                softly.assertThat(response.getHistory().get(0).getLabel()).isEqualTo(Label.TIPICO);
            });
        }
    }

    @Feature("Limit History")
    @DisplayName("Get Limit History With LCR")
    @Description("Get Limit History With Licene Region Context")
    @ParameterizedTest(name = "{index} -> Getting a Limit History for product={0} , label={1}, " +
            "and limitType={2}")
    @Tag("RegressionTests")
    @TestCaseId(3467)
    @CsvFileSource(files = "src/test/resources/getLimitHistoryWithLRC.csv", numLinesToSkip = 1)
    public void getLimitHistoryWithLRC(Product product, Label label, LimitType type){
        RGFESGetLimitHistoryWithLCRResponse response = new GetRGFESLimitHistoryEndpoint()
                .sendRequest(sessionId,product,label,type).assertRequestStatusCode()
                .getModelTypeForLimitHistoryWithLRC();
        if (type == LimitType.DEPOSIT && isStaging) {
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(response.getLimitHistoryProduct(product).getLimitHistoryType(type).get(0).getValue())
                        .isEqualTo(1000d);
                softly.assertThat(response.getLimitHistoryProduct(product).getLimitHistoryType(type).get(0).getInterval())
                        .isEqualTo(Interval.MONTH);
            });
        }
    }
}
