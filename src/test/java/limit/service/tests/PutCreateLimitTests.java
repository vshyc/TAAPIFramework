package limit.service.tests;

import configuration.BaseTest;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import customer.stake.helpers.GetLimitsHelper;
import customer.stake.pojo.limits.LimitCreationData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.rop.PutLimitEndpoint;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    @Step("Create a user for test ")
    public void setUp(){
        userHelper = new UserHelper();
        uuid = userHelper.createGermanUserAndGetUuid();
    }

    @Feature("Create Limits in Limit service with application token")
    @DisplayName("Create Limits in Limit service with application token")
    @Description("Creating New Limit's in Limit Service if not exist")
    @ParameterizedTest(name = "{index} -> Creating a limit with application token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/main/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitsTestWithApplicationTokenTest(String type, OwnerEnum owner,
                                                               LabelEnums label,String product,
                                                               Double value,String interval){
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
    @CsvFileSource(files = "src/main/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitsTestWithUserTokenTest(String type, OwnerEnum owner,
                                                        LabelEnums label,String product,
                                                        Double value,String interval){

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
    @CsvFileSource(files = "src/main/resources/updateLimitTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateLimitToLowerValueTest(String type, OwnerEnum owner,
                                         LabelEnums label,String product,
                                         Double value,String interval,Double updatedValue){
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
    @CsvFileSource(files = "src/main/resources/updateLimitToHigherValueTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateLimitToHigherValueTest(String type, OwnerEnum owner,
                                         LabelEnums label,String product,
                                         Double value,String interval,Double updatedValue){
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
    @CsvFileSource(files = "src/main/resources/updateLimitTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateAMLLimitToHigherValueTest(String type, OwnerEnum owner,
                                         LabelEnums label,String product,
                                         Double value,String interval,Double updatedValue){
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


    @Step("Sending a call to Limit Service with User Token to create Limit")
    private LimitsResponseData createLimitWithUserToken(String type, OwnerEnum owner,
                                           LabelEnums label,String product,
                                           Double value,String interval){
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
       return new PutLimitEndpoint().sendRequestToCreateNewLimit(body,new OauthHelper().getUserToken(userHelper.getGermanUserName()
                        ,userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
    }
    @Step("Sending a call to Limit Service with Application Token to create Limit")
    private LimitsResponseData createLimitWithApplicationToken(String type, OwnerEnum owner,
                                           LabelEnums label,String product,
                                           Double value,String interval){
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToCreateNewLimit(body,new OauthHelper().getApplicationToken(),uuid).assertRequestStatusCode().getResponseModel();
    }

    @Step("Sending a call to Limit Service with User Token to update Limit")
    private LimitsResponseData updateLimit(String type, OwnerEnum owner,
                                           LabelEnums label,String product,
                                           Double value,String interval){
        LimitCreationData updatedBody = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToUpdateLimit(updatedBody,new OauthHelper().getUserToken(userHelper.getGermanUserName(),
                        userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();

    }


}
