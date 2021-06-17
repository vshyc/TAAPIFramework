package limit.service.tests;

import configuration.BaseTest;
import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.IntervalEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import customer.stake.enums.LimitTypeEnum;
import customer.stake.helpers.AddCounterHelper;
import customer.stake.helpers.GetLimitsHelper;
import customer.stake.pojo.counters.PostCountersResponse;
import customer.stake.pojo.limits.LimitCreationData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.rop.PutLimitEndpoint;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
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
    private String limitUuid =null;
    private UserHelper userHelper;
    private String uuid;
    private String id;
    private JsonPath createdUser;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    @Step("Create a user for test ")
    public void setUp(){
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        uuid = userHelper.getUuid(createdUser);
        id = userHelper.getId(createdUser);
    }

    @Feature("Create Limits in Limit service with application token")
    @DisplayName("Create Limits in Limit service with application token")
    @Description("Creating New Limit's in Limit Service if not exist")
    @ParameterizedTest(name = "{index} -> Creating a limit with application token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/test/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitsTestWithApplicationTokenTest(LimitTypeEnum type, OwnerEnum owner,
                                                         LabelEnums label, String product,
                                                         Double value, IntervalEnum interval){
        try {
            limitUuid = new GetLimitsHelper().checkIfLimitExistForUser(uuid,owner,type,label).getLimitUUID();
        }catch (NullPointerException e){
            log.info("Limit don't exist, creating new one");
        }
        if (limitUuid == null) {
            LimitsResponseData response = createLimitWithApplicationToken(type,owner,label,product,value,interval);
            Assertions.assertThat(response.getLabel()).isEqualTo(label);
            Assertions.assertThat(response.getOwner()).isEqualTo(owner);
            Assertions.assertThat(response.getProduct()).isEqualTo(product);
            Assertions.assertThat(response.getCreatedBy()).isEmpty();
        }else {
            log.info("Limit exist, skip creating new one");
        }


    }
    @Feature("Create Limits in Limit service with user token")
    @DisplayName("Create Limits in Limit service with user token")
    @Description("Creating New Limit's in Limit Service if not exist")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/test/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitsTestWithUserTokenTest(LimitTypeEnum type, OwnerEnum owner,
                                                  LabelEnums label, String product,
                                                  Double value, IntervalEnum interval){

        try {
            limitUuid = new GetLimitsHelper().checkIfLimitExistForUser(uuid,owner,type,label).getLimitUUID();
        }catch (NullPointerException e){
           log.info("Limit don't exist, creating new one");
        }
        if (limitUuid == null){
        LimitsResponseData response = createLimitWithUserToken(type,owner,label,product,value,interval);
        Assertions.assertThat(response.getLabel()).isEqualTo(label);
        Assertions.assertThat(response.getOwner()).isEqualTo(owner);
        Assertions.assertThat(response.getProduct()).isEqualTo(product);
        Assertions.assertThat(response.getCreatedBy()).isEqualTo(uuid);}
        else {
            log.info("Limit exist, skip creating new one");
        }
    }
    @Feature("Create and update Limit to lower value")
    @DisplayName("Create and update Limit to lower value")
    @Description("Create and update Limit to lower value")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and updated value = {6}")
    @CsvFileSource(files = "src/test/resources/updateLimitTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateLimitToLowerValueTest(LimitTypeEnum type, OwnerEnum owner,
                                                     LabelEnums label, String product,
                                                     Double value, IntervalEnum interval, Double updatedValue){
        LimitsResponseData response = createLimitWithUserToken(type,owner,label,product,value,interval);
        Assertions.assertThat(response.getLabel()).isEqualTo(label);
        Assertions.assertThat(response.getOwner()).isEqualTo(owner);
        Assertions.assertThat(response.getProduct()).isEqualTo(product);
        Assertions.assertThat(response.getCreatedBy()).isEqualTo(uuid);

        LimitsResponseData responseData = updateLimit(type,owner,label,product,updatedValue,interval);
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
    public void createAndUpdateLimitToHigherValueTest(LimitTypeEnum type, OwnerEnum owner,
                                                      LabelEnums label, String product,
                                                      Double value, IntervalEnum interval, Double updatedValue){
        LimitsResponseData response = createLimitWithUserToken(type,owner,label,product,value,interval);

        Assertions.assertThat(response.getLabel()).isEqualTo(label);
        Assertions.assertThat(response.getOwner()).isEqualTo(owner);
        Assertions.assertThat(response.getProduct()).isEqualTo(product);
        Assertions.assertThat(response.getCreatedBy()).isEqualTo(uuid);

        LimitsResponseData responseData = updateLimit(type,owner,label,product,updatedValue,interval);

        Assertions.assertThat(responseData.getCurrent().getValue().doubleValue()).describedAs("check if " +
                "value of the limit is not updated to higher value").isEqualTo(value);
        Assertions.assertThat(responseData.getFuture().getValue().doubleValue()).describedAs("check if " +
                "value of the limit is not updated to higher value").isEqualTo(updatedValue);
        Assertions.assertThat(responseData.getCurrent().getInterval()).isEqualTo(interval);
        Assertions.assertThat(response.getOwner()).isEqualTo(responseData.getOwner());
    }

    @Feature("Create and update AML Limit to higher value")
    @DisplayName("Create and update AML Limit to higher value")
    @Description("Create and update AML Limit to higher value, the limit should be updated")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and updated value = {6}")
    @CsvFileSource(files = "src/test/resources/updateAMLLimitToHigherValueTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateAMLLimitToHigherValueTest(LimitTypeEnum type, OwnerEnum owner,
                                                         LabelEnums label, String product,
                                                         Double value, IntervalEnum interval, Double updatedValue){
        LimitsResponseData response = createLimitWithUserToken(type,owner,label,product,value,interval);

        Assertions.assertThat(response.getLabel()).isEqualTo(label);
        Assertions.assertThat(response.getOwner()).isEqualTo(owner);
        Assertions.assertThat(response.getProduct()).isEqualTo(product);
        Assertions.assertThat(response.getCreatedBy()).isEqualTo(uuid);

        LimitsResponseData responseData = updateLimit(type,owner,label,product,updatedValue,interval);

        Assertions.assertThat(responseData.getCurrent().getValue().doubleValue()).describedAs("check if " +
                "value of the limit is updated to higher value").isEqualTo(updatedValue);
        Assertions.assertThat(responseData.getCurrent().getInterval()).isEqualTo(interval);
        Assertions.assertThat(response.getOwner()).isEqualTo(responseData.getOwner());
    }

    @DisplayName("Check if PUT call to Limit Service with no auth will respond with 401 Error code")
    @Test
    public void createLimitWithNoAuthTest(){
        LimitCreationData body = LimitCreationData.builder().
                type(LimitTypeEnum.DEPOSIT)
                .owner(OwnerEnum.PERSONAL)
                .label(LabelEnums.TIPICO)
                .product("sports")
                .value(200d)
                .interval(IntervalEnum.MONTH)
                .build();
        new PutLimitEndpoint().sendRequestToCreateNewLimitWithNoAuth(body,uuid).assertNoAuthRequestStatusCode();
    }

    @DisplayName("Check if PUT call to Limit Service with no body will respond with 400 Error code")
    @Test
    public void createLimitWithNoBodyTest(){
        new PutLimitEndpoint().sendRequestWithNoBodyToCreateNewLimit(new OauthHelper().getApplicationToken(),uuid)
                .assertBadRequestStatusCode();
    }

    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} adding a counter with value = {6} and checking if " +
            "remaining value is calculated correctly ")
    @CsvFileSource(files = "src/test/resources/limitsWithCountersData.csv", numLinesToSkip = 1)
    public void checkIfRemainingValueIsCalculatedCorrectlyTest(LimitTypeEnum LimitType, OwnerEnum owner,
                                                               LabelEnums label, String product,
                                                               Double value, IntervalEnum interval,
                                                               Double counterValue, CounterTypeEnum counterType){
        createLimitWithApplicationToken(LimitType, owner,label,product,value,interval);
        addCounterForLimit(uuid,id,label, counterType,counterValue);
        LimitsResponseData response = getLimit(uuid,owner,LimitType,label);
        Assertions.assertThat(response.getRemaining()).isEqualTo(value-counterValue);

    }

    @Test
    public void multilineString(){
        String hmmm = """why not working;/this""";
        System.out.println(hmmm);

    }


    @Step("Sending a call to Limit Service with User Token to create Limit")
    private LimitsResponseData createLimitWithUserToken(LimitTypeEnum type, OwnerEnum owner,
                                                        LabelEnums label, String product,
                                                        Double value, IntervalEnum interval){
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
       return new PutLimitEndpoint().sendRequestToCreateNewLimit(body,new OauthHelper()
               .getUserToken(userHelper.getGermanUserName(),userHelper.getGermanUserPassword()),uuid)
               .assertRequestStatusCode().getResponseModel();
    }
    @Step("Sending a call to Limit Service with Application Token to create Limit")
    private LimitsResponseData createLimitWithApplicationToken(LimitTypeEnum type, OwnerEnum owner,
                                                               LabelEnums label, String product,
                                                               Double value, IntervalEnum interval){
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToCreateNewLimit(body,new OauthHelper().getApplicationToken(),uuid)
                .assertRequestStatusCode().getResponseModel();
    }

    @Step("Sending a call to Limit Service with User Token to update Limit")
    private LimitsResponseData updateLimit(LimitTypeEnum type, OwnerEnum owner,
                                           LabelEnums label, String product,
                                           Double value, IntervalEnum interval){
        LimitCreationData updatedBody = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToUpdateLimit(updatedBody,new OauthHelper()
                .getUserToken(userHelper.getGermanUserName(), userHelper.getGermanUserPassword()),uuid)
                .assertRequestStatusCode().getResponseModel();

    }

    @Step("Add counter for limit")
    private PostCountersResponse addCounterForLimit(String uuid, String id, LabelEnums label, CounterTypeEnum type , Double amount){
        return new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid,id,label,
                type,amount);
    }

    @Step("Send request to Get limit")
    private LimitsResponseData getLimit(String uuid, OwnerEnum owner, LimitTypeEnum type, LabelEnums label){
        return new GetLimitsHelper().checkIfLimitExistForUser(uuid,owner,type,label);
    }


}
