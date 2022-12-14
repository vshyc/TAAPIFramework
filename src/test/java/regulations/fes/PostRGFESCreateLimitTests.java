package regulations.fes;

import configuration.BaseTest;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Owner;
import customer.stake.enums.Product;
import customer.stake.helpers.LoginHelper;
import customer.stake.helpers.TermsAndConditionsHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.dto.limits.LimitCreationData;
import customer.stake.dto.rgfes.RGFESCreateLimitResponse;
import customer.stake.rop.PostRGFESLimitEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("POST RGFES Endpoint for Limit service Tests")
public class PostRGFESCreateLimitTests extends BaseTest {

    private UserHelper userHelper;
    private TermsAndConditionsHelper tacHelper;
    private LoginHelper loginHelper;
    private Response tacResponse;
    private String sessionId;
    private JsonPath createdUser;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    @Step("Create and login user for test ")
    public void setUp() {
        loginHelper = new LoginHelper();
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        tacHelper = new TermsAndConditionsHelper();
        tacResponse = tacHelper.acceptAllDocumentsInTAC(userHelper.getUuid(createdUser));
        sessionId = loginHelper.getSessionId(loginHelper.LoginUserToAccountApp(userHelper.getGermanUserName()));
    }

    @Feature("Create Limits in Limit service RGFES")
    @DisplayName("Create Limits in Limit service with RGFES")
    @Description("Creating New Limit's in Limit Service if not exist")
    @ParameterizedTest(name = "{index} -> Creating a limit in RGFES and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/test/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitWithRGFESTest(LimitType type, Owner owner,
                                         Label label, Product product,
                                         Double value, Interval interval) {
        if (!(envConfig.env().equals("staging")) && type == LimitType.TURNOVER) {
            Assertions.assertThat(true).as("The turnover limit exist on registration so " +
                    "creating it by RGFES is not posible");
        } else {
            RGFESCreateLimitResponse response = new PostRGFESLimitEndpoint().sendRequest(sessionId, LimitCreationData
                            .builder().type(type).owner(owner).label(label)
                            .product(product).value(value).interval(interval).build()).assertRequestStatusCode()
                    .getResponseModel();
            Assertions.assertThat(response.getLimit(product).getLimit(type).getCurrent().getValue()).isEqualTo(value);
        }
    }
}
