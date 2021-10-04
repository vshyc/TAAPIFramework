package customer.stake.counters.tests;

import configuration.BaseTest;
import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.helpers.AddCounterHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.pojo.counters.PostCountersResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("POST Endpoint for Customer Stake Service Tests")
public class PostCreateUserCountersTests extends BaseTest {

    private UserHelper userHelper;
    private String uuid;
    private String id;
    private JsonPath createdUser;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    @Step("Create a user for test ")
    public void setUp() {
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        uuid = userHelper.getUuid(createdUser);
        id = userHelper.getId(createdUser);
    }

    @DisplayName("Add Customer counters")
    @Description("Add Customer counters to CSS")
    @ParameterizedTest(name = "{index} -> Adding a counter for new created user with label = {0}  , " +
            "type = {1} and amount = {2} ")
    @CsvFileSource(files = "src/test/resources/addCounterToCustomerStakeService.csv", numLinesToSkip = 1)
    public void addPayInCountersToCustomerStakeService(LabelEnums label, CounterTypeEnum type, double amount) {
        PostCountersResponse response = new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid, id, label,
                type, amount);

        Assertions.assertThat(response.getLabel()).isEqualTo(label);
        Assertions.assertThat(response.getAttributes().getAtribute(type).stream().reduce(0d, Double::sum))
                .describedAs("Check if sum off all counters for new user is equal to amount").isEqualTo(amount);
    }
}
