package limit.service.tests;

import configuration.BaseTest;
import customer.stake.enums.CounterType;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.Owner;
import customer.stake.enums.LimitType;
import customer.stake.helpers.AddCounterHelper;
import customer.stake.helpers.GetLimitsHelper;
import customer.stake.dto.counters.PostCountersResponse;
import customer.stake.dto.limits.LimitCreationData;
import customer.stake.dto.limits.LimitsResponseData;
import customer.stake.rop.PutLimitEndpoint;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Epic("Create Limits Epic")
@DisplayName("PUT Endpoint for Limit service Tests")
public class PutCreateLimitTests extends BaseTest {
    private String limitUuid = null;
    private UserHelper userHelper;
    private String uuid;
    private String id;
    private JsonPath createdUser;
    private String headerUUID;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    @Step("Create a user for test ")
    public void setUp() {
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        uuid = userHelper.getUuid(createdUser);
        id = userHelper.getId(createdUser);
        headerUUID = "070c1ad0-d2d0-4aa9-bfb7-4bc3e08b13f0";
    }

    @Feature("Create Limits in Limit service with application token")
    @DisplayName("Create Limits in Limit service with application token")
    @Description("Creating New Limit's in Limit Service if not exist")
    @ParameterizedTest(name = "{index} -> Creating a limit with application token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/test/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitsTestWithApplicationTokenTest(LimitType type, Owner owner,
                                                         Label label, String product,
                                                         Double value, Interval interval) {
        try {
            limitUuid = new GetLimitsHelper().checkIfLimitExistForUser(uuid, owner, type, label).getLimitUUID();
        } catch (NullPointerException e) {
            log.info("Limit don't exist, creating new one");
        }
        if (limitUuid == null) {
            LimitsResponseData response = createLimitWithApplicationToken(type, owner, label, product, value, interval);
            SoftAssertions.assertSoftly(softly-> {
                softly.assertThat(response.getLabel()).isEqualTo(label);
                softly.assertThat(response.getOwner()).isEqualTo(owner);
                softly.assertThat(response.getProduct()).isEqualTo(product);
                softly.assertThat(response.getCreatedBy()).isEmpty();
            });
        } else {
            log.info("Limit exist, skip creating new one");
        }


    }

    @Feature("Create Limits in Limit service with user token")
    @DisplayName("Create Limits in Limit service with user token")
    @Description("Creating New Limit's in Limit Service if not exist")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/test/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitsTestWithUserTokenTest(LimitType type, Owner owner,
                                                  Label label, String product,
                                                  Double value, Interval interval) {

        try {
            limitUuid = new GetLimitsHelper().checkIfLimitExistForUser(uuid, owner, type, label).getLimitUUID();
        } catch (NullPointerException e) {
            log.info("Limit don't exist, creating new one");
        }
        if (limitUuid == null) {
            LimitsResponseData response = createLimitWithUserToken(type, owner, label, product, value, interval);
            SoftAssertions.assertSoftly(softly-> {
                softly.assertThat(response.getLabel()).isEqualTo(label);
                softly.assertThat(response.getOwner()).isEqualTo(owner);
                softly.assertThat(response.getProduct()).isEqualTo(product);
                softly.assertThat(response.getCreatedBy()).isEqualTo(uuid);
            });
        } else {
            log.info("Limit exist, skip creating new one");
        }
    }

    @Feature("Create and update Limit to lower value")
    @DisplayName("Create and update Limit to lower value")
    @Description("Create and update Limit to lower value")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and updated value = {6}")
    @CsvFileSource(files = "src/test/resources/updateLimitTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateLimitToLowerValueTest(LimitType type, Owner owner,
                                                     Label label, String product,
                                                     Double value, Interval interval, Double updatedValue) {
        LimitsResponseData response = createLimitWithUserToken(type, owner, label, product, value, interval);
        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(response.getLabel()).isEqualTo(label);
            softly.assertThat(response.getOwner()).isEqualTo(owner);
            softly.assertThat(response.getProduct()).isEqualTo(product);
            softly.assertThat(response.getCreatedBy()).isEqualTo(uuid);
        });

        LimitsResponseData responseData = updateLimit(type, owner, label, product, updatedValue, interval);
        Assertions.assertThat(responseData.getCurrent().getValue().doubleValue()).describedAs("check if " +
                "value of the limit is updated to lower value").isEqualTo(updatedValue);
        Assertions.assertThat(responseData.getCurrent().getInterval()).isEqualTo(interval);
        Assertions.assertThat(response.getOwner()).isEqualTo(responseData.getOwner());
    }

    @Feature("Create and update Limit to higher value")
    @DisplayName("Create and update Limit to higher value")
    @Description("Create and update Limit to higher value")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and updated value = {6}")
    @CsvFileSource(files = "src/test/resources/updateLimitToHigherValueTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateLimitToHigherValueTest(LimitType type, Owner owner,
                                                      Label label, String product,
                                                      Double value, Interval interval, Double updatedValue) {
        LimitsResponseData response = createLimitWithUserToken(type, owner, label, product, value, interval);
        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(response.getLabel()).isEqualTo(label);
            softly.assertThat(response.getOwner()).isEqualTo(owner);
            softly.assertThat(response.getProduct()).isEqualTo(product);
            softly.assertThat(response.getCreatedBy()).isEqualTo(uuid);
        });
        LimitsResponseData responseData = updateLimit(type, owner, label, product, updatedValue, interval);

        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(responseData.getCurrent().getValue().doubleValue()).describedAs("check if " +
                    "value of the limit is not updated to higher value").isEqualTo(value);
            softly.assertThat(responseData.getFuture().getValue().doubleValue()).describedAs("check if " +
                    "value of the limit is not updated to higher value").isEqualTo(updatedValue);
            softly.assertThat(responseData.getCurrent().getInterval()).isEqualTo(interval);
            softly.assertThat(response.getOwner()).isEqualTo(responseData.getOwner());
        });
    }

    @Feature("Create and update AML Limit to higher value")
    @DisplayName("Create and update AML Limit to higher value")
    @Description("Create and update AML Limit to higher value, the limit should be updated")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and updated value = {6}")
    @CsvFileSource(files = "src/test/resources/updateAMLLimitToHigherValueTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateAMLLimitToHigherValueTest(LimitType type, Owner owner,
                                                         Label label, String product,
                                                         Double value, Interval interval, Double updatedValue) {
        LimitsResponseData response = createLimitWithUserToken(type, owner, label, product, value, interval);

        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(response.getLabel()).isEqualTo(label);
            softly.assertThat(response.getOwner()).isEqualTo(owner);
            softly.assertThat(response.getProduct()).isEqualTo(product);
            softly.assertThat(response.getCreatedBy()).isEqualTo(uuid);
        });

        LimitsResponseData responseData = updateLimit(type, owner, label, product, updatedValue, interval);

        SoftAssertions.assertSoftly(softly-> {
            softly.assertThat(responseData.getCurrent().getValue().doubleValue()).describedAs("check if " +
                    "value of the limit is updated to higher value").isEqualTo(updatedValue);
            softly.assertThat(responseData.getCurrent().getInterval()).isEqualTo(interval);
            softly.assertThat(response.getOwner()).isEqualTo(responseData.getOwner());
        });
    }

    @DisplayName("Check if PUT call to Limit Service with no auth will respond with 401 Error code")
    @Test
    public void createLimitWithNoAuthTest() {
        LimitCreationData body = LimitCreationData.builder().
                type(LimitType.DEPOSIT)
                .owner(Owner.PERSONAL)
                .label(Label.TIPICO)
                .product("sports")
                .value(200d)
                .interval(Interval.MONTH)
                .build();
        new PutLimitEndpoint().sendRequestToCreateNewLimitWithNoAuth(body, uuid).assertNoAuthRequestStatusCode();
    }

    @DisplayName("Check if PUT call to Limit Service with no body will respond with 400 Error code")
    @Test
    public void createLimitWithNoBodyTest() {
        new PutLimitEndpoint().sendRequestWithNoBodyToCreateNewLimit(new OauthHelper().getApplicationToken(), uuid)
                .assertBadRequestStatusCode();
    }

    @DisplayName("Create Limits in Limit service with User token")
    @Description("Creating New Limit's in Limit Service if not exist add counter to Customer-stake-service and check if " +
            "limit remaining value is calculated properly")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} adding a counter with value = {6} and checking if " +
            "remaining value is calculated correctly ")
    @CsvFileSource(files = "src/test/resources/limitsWithCountersData.csv", numLinesToSkip = 1)
    public void checkIfRemainingValueIsCalculatedCorrectlyTest(LimitType limitType, Owner owner,
                                                               Label label, String product,
                                                               Double value, Interval interval,
                                                               Double counterValue, CounterType counterType) {
        if (!(envConfig.env().equals("staging")) && limitType == LimitType.TURNOVER) {
            Assertions.assertThat(true).as("The turnover limit exist on registration so " +
                    "creating it by RGFES is not posible");
        } else {
            createLimitWithApplicationToken(limitType, owner, label, product, value, interval);
            addCounterForLimit(uuid, id, label, counterType, counterValue);
            LimitsResponseData response = getLimit(uuid, owner, limitType, label);
            Assertions.assertThat(response.getRemaining()).isEqualTo(value - counterValue);
        }
    }

    @DisplayName("Check if UUID header is readed properly ")
    @ParameterizedTest(name = "{index} -> Creating a limit with application token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/test/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void checkIfCreatedByIsGetFromUUIDHeader(LimitType type, Owner owner,
                                                    Label label, String product,
                                                    Double value, Interval interval){
        try {
            limitUuid = new GetLimitsHelper().checkIfLimitExistForUser(uuid, owner, type, label).getLimitUUID();
        } catch (NullPointerException e) {
            log.info("Limit don't exist, creating new one");
        }
        if (limitUuid == null) {
            LimitsResponseData response = createLimitWithApplicationToken(type, owner, label, product,
                    value, interval, headerUUID);
            SoftAssertions.assertSoftly(softly-> {
                softly.assertThat(response.getLabel()).isEqualTo(label);
                softly.assertThat(response.getOwner()).isEqualTo(owner);
                softly.assertThat(response.getProduct()).isEqualTo(product);
                softly.assertThat(response.getCreatedBy()).isEqualTo(headerUUID);
            });
        } else {
            log.info("Limit exist, skip creating new one");
        }
    }


    @Step("Sending a call to Limit Service with User Token to create Limit")
    private LimitsResponseData createLimitWithUserToken(LimitType type, Owner owner,
                                                        Label label, String product,
                                                        Double value, Interval interval) {
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToCreateNewLimit(body, new OauthHelper()
                        .getUserToken(userHelper.getGermanUserName(), userHelper.getGermanUserPassword()), uuid)
                .assertRequestStatusCode().getResponseModel();
    }

    @Step("Sending a call to Limit Service with Application Token to create Limit")
    private LimitsResponseData createLimitWithApplicationToken(LimitType type, Owner owner,
                                                               Label label, String product,
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

    @Step("Sending a call to Limit Service with Application Token to create Limit")
    private LimitsResponseData createLimitWithApplicationToken(LimitType type, Owner owner,
                                                               Label label, String product,
                                                               Double value, Interval interval,String headerUUID) {
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToCreateNewLimit(body,
                        new OauthHelper().getApplicationToken(), uuid,headerUUID)
                .assertRequestStatusCode().getResponseModel();
    }

    @Step("Sending a call to Limit Service with User Token to update Limit")
    private LimitsResponseData updateLimit(LimitType type, Owner owner,
                                           Label label, String product,
                                           Double value, Interval interval) {
        LimitCreationData updatedBody = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToUpdateLimit(updatedBody, new OauthHelper()
                        .getUserToken(userHelper.getGermanUserName(), userHelper.getGermanUserPassword()), uuid)
                .assertRequestStatusCode().getResponseModel();

    }

    @Step("Add counter for limit")
    private PostCountersResponse addCounterForLimit(String uuid, String id, Label label, CounterType type, Double amount) {
        return new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid, id, label,
                type, amount);
    }

    @Step("Send request to Get limit")
    private LimitsResponseData getLimit(String uuid, Owner owner, LimitType type, Label label) {
        return new GetLimitsHelper().checkIfLimitExistForUser(uuid, owner, type, label);
    }


}
