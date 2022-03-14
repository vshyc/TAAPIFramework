package limit.service.tests;

import com.tipico.ta.reqtest.extension.ReqtestReporterExtension;
import com.tipico.ta.reqtest.extension.TestCaseId;
import configuration.BaseTest;
import customer.stake.dto.counters.PostCountersResponse;
import customer.stake.dto.limits.LimitCreationData;
import customer.stake.dto.limits.LimitsResponseData;
import customer.stake.enums.CounterType;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Owner;
import customer.stake.enums.Product;
import customer.stake.enums.ValidatorResult;
import customer.stake.helpers.AddCounterHelper;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import customer.stake.rop.PutLimitEndpoint;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


@DisplayName("Tests checking the LS Validators when incrementing the counters")
@ExtendWith(ReqtestReporterExtension.class)
public class PostCountersAndLSValidatorsTests extends BaseTest {
    private UserHelper userHelper;
    private String uuid;
    private String id;
    private JsonPath createdUser;

    @BeforeEach
    @Step("Create a user for test ")
    public void setUp() {
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        uuid = userHelper.getUuid(createdUser);
        id = userHelper.getId(createdUser);
    }

    @Feature("Updates counters with Validation on LS")
    @ParameterizedTest(name = "{index} -> Sending a call to CSS with label={0} , type={1}, " +
            "amount={2} and checking if there is proper Validation result in response ")
    @DisplayName("Check validation response when incrementing the counter")
    @TestCaseId(3649)
    @Tag("RegressionTests")
    @CsvFileSource(files = "src/test/resources/addCounterToCustomerStakeService.csv", numLinesToSkip = 1)
    public void checkIfThereIsProperValidatorResponseWhenIncrementingTheCounter(Label label, CounterType type, double amount){
        PostCountersResponse response = new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid, id, label,
                type, amount);
        Assertions.assertThat(response.getLabel()).isEqualTo(label);
        Assertions.assertThat(response.getAttributes().getAtribute(type).stream().reduce(0d, Double::sum))
                .describedAs("Check if sum off all counters for new user is equal to amount").isEqualTo(amount);
        if ((!(envConfig.env().equals("staging")) && type == CounterType.STAKE_BET))
            Assertions.assertThat(response.getValidationResponse().getLimitForCounter(type).get(0).getValidationResult())
                    .isEqualTo(ValidatorResult.VALID);
        else {
        Assertions.assertThat(response.getValidationResponse().getLimitForCounter(type).get(0).getValidationResult())
                .isEqualTo(ValidatorResult.VALID_WITH_NO_LIMIT_DEFINED);}
    }

    @Feature("Updates counters with Validation on LS")
    @ParameterizedTest(name = "{index} -> Sending a call to CSS with label={0} , type={1}, " +
            "amount={2} for the user with limit  type={3} , owner={4}, " +
            " label={0}, product={5}, value={6} , interval={7}  and checking if there is proper Validation result in response ")
    @DisplayName("Check validation response when incrementing the counter with limit on LS")
    @TestCaseId(3652)
    @Tag("RegressionTests")
    @CsvFileSource(files = "src/test/resources/addCountersAndLimitsForValidationsData.csv", numLinesToSkip = 1)
    public void checkIfThereIsProperValidatorResponseWhenIncrementingTheCounterWithLimit (Label label, CounterType type,
            double amount, LimitType limitType, Owner owner,
            Product product, double limitAmount, Interval interval){
        if (!((!(envConfig.env().equals("staging")) && type == CounterType.STAKE_BET))){
        createLimit(limitType,owner,label,product,limitAmount,interval);}

        PostCountersResponse response = new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid, id, label,
                type, amount);
        Assertions.assertThat(response.getLabel()).isEqualTo(label);
        Assertions.assertThat(response.getAttributes().getAtribute(type).stream().reduce(0d, Double::sum))
                .describedAs("Check if sum off all counters for new user is equal to amount").isEqualTo(amount);
        Assertions.assertThat(response.getValidationResponse().getLimitForCounter(type).get(0).getValidationResult())
                    .isEqualTo(ValidatorResult.VALID);
    }

    @Feature("Updates counters with Validation on LS")
    @ParameterizedTest(name = "{index} -> Sending a call to CSS with label={0} , type={1}, " +
            "amount={2} for the user with limit  type={3} , owner={4}, " +
            " label={0}, product={5}, value={6} , interval={7}  and checking if there is proper Validation result in response ")
    @DisplayName("Check validation response when incrementing the counter to the same value as limit on LS")
    @TestCaseId(3653)
    @Tag("RegressionTests")
    @CsvFileSource(files = "src/test/resources/addCountersAndLimitsWithTheSameValueForValidationData.csv", numLinesToSkip = 1)
    public void checkIfThereIsProperValidatorResponseWhenIncrementingTheCounterWithTheSameValueAsLimit
            (Label label, CounterType type, double amount,
             LimitType limitType, Owner owner, Product product, double limitAmount, Interval interval){
        if (!((!(envConfig.env().equals("staging")) && type == CounterType.STAKE_BET))){
            createLimit(limitType,owner,label,product,limitAmount,interval);}

        PostCountersResponse response = new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid, id, label,
                type, amount);
        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(response.getLabel()).isEqualTo(label);
            softly.assertThat(response.getAttributes().getAtribute(type).stream().reduce(0d, Double::sum))
                    .describedAs("Check if sum off all counters for new user is equal to amount").isEqualTo(amount);
        });
        if ((!(envConfig.env().equals("staging")) && type == CounterType.STAKE_BET)){
            Assertions.assertThat(response.getValidationResponse().getLimitForCounter(type).get(0).getValidationResult())
                .isEqualTo(ValidatorResult.VALID);}
        else {
            Assertions.assertThat(response.getValidationResponse().getLimitForCounter(type).get(0).getValidationResult())
                    .isEqualTo(ValidatorResult.LIMIT_EXACTLY_MATCHED);}
    }

    @Feature("Updates counters with Validation on LS")
    @ParameterizedTest(name = "{index} -> Sending a call to CSS with label={0} , type={1}, " +
            "amount={2} for the user with limit  type={3} , owner={4}, " +
            " label={0}, product={5}, value={6} , interval={7}  and checking if there is proper Validation result in response ")
    @DisplayName("Check validation response when incrementing the counter to the higher value then limit on LS")
    @TestCaseId(3654)
    @Tag("RegressionTests")
    @CsvFileSource(files = "src/test/resources/addCountersAndLimitsWithTheValueHigherThenLimitForValidationData.csv", numLinesToSkip = 1)
    public void checkIfThereIsProperValidatorResponseWhenIncrementingTheCounterWithTheHigherValueThenLimit
            (Label label, CounterType type, double amount,
             LimitType limitType, Owner owner, Product product, double limitAmount, Interval interval){
        if (!((!(envConfig.env().equals("staging")) && type == CounterType.STAKE_BET))){
            createLimit(limitType,owner,label,product,limitAmount,interval);}

        PostCountersResponse response = new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid, id, label,
                type, amount);
        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(response.getLabel()).isEqualTo(label);
            softly.assertThat(response.getAttributes().getAtribute(type).stream().reduce(0d, Double::sum))
                .describedAs("Check if sum off all counters for new user is equal to 0 after the attempt " +
                        "of incrementation but exceeding the limit").isEqualTo(0d);
            softly.assertThat(response.getValidationResponse().getLimitForCounter(type).get(0).getValidationResult())
                .isEqualTo(ValidatorResult.LIMIT_EXCEED);
        });
    }



    @Step("Sending a call to Limit Service to create Limit")
    private LimitsResponseData createLimit(LimitType type, Owner owner,
                                           Label label, Product product,
                                           Double value, Interval interval) {
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

