package limit.service.tests;

import configuration.BaseTest;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import customer.stake.helpers.GetLimitsHelper;
import customer.stake.pojo.limits.LimitCreationData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.rop.PutCreateLimitEndpoint;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Epic("Create Limits Epic")
@Feature("Put endpoint tests Features")
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
    @Feature("Put endpoint tests Features")
    @DisplayName("Create Limits in Limit service with application token")
    @Story("Create Limits in Limit service with application token with parameters ")
    @Description("Creating New Limit's in Limit Service if not exist")
    @ParameterizedTest(name = "{index} -> Creating a limit with application token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/main/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitsTestWithApplicationTokenTest(String type, OwnerEnum owner,
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
        try {
            limitUuid = new GetLimitsHelper().checkIfLimitExistForUser(uuid,owner,type).getLimitUUID();
        }catch (NullPointerException e){
            log.info("Limit don't exist, creating new one");
        }
        if (limitUuid == null) {
            LimitsResponseData response = new PutCreateLimitEndpoint().sendRequestToCreateNewLimit(body, new OauthHelper().getApplicationToken(), uuid)
                    .assertRequestStatusCode().getResponseModel();
            Assertions.assertThat(response.getLabel()).isEqualTo(body.getLabel().toString());
            Assertions.assertThat(response.getOwner()).isEqualTo(body.getOwner().toString());
            Assertions.assertThat(response.getProduct()).isEqualTo(body.getProduct());
            Assertions.assertThat(response.getCreatedBy()).isEmpty();
        }else {
            log.info("Limit exist, skip creating new one");
        }


    }

    @DisplayName("Create Limits in Limit service with user token")
    @Story("Create Limits in Limit service with user token with parameters ")
    @Description("Creating New Limit's in Limit Service if not exist")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} and interval={5}")
    @CsvFileSource(files = "src/main/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createLimitsTestWithUserTokenTest(String type, OwnerEnum owner,
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
        try {
            limitUuid = new GetLimitsHelper().checkIfLimitExistForUser(uuid,owner,type).getLimitUUID();
        }catch (NullPointerException e){
           log.info("Limit don't exist, creating new one");
        }
        if (limitUuid == null){
        LimitsResponseData response = new PutCreateLimitEndpoint()
                .sendRequestToCreateNewLimit(body,new OauthHelper().getUserToken(userHelper.getGermanUserName()
                        ,userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getLabel()).isEqualTo(body.getLabel().toString());
        Assertions.assertThat(response.getOwner()).isEqualTo(body.getOwner().toString());
        Assertions.assertThat(response.getProduct()).isEqualTo(body.getProduct());
        Assertions.assertThat(response.getCreatedBy()).isNotEmpty();}
        else {
            log.info("Limit exist, skip creating new one");
        }
    }

    @DisplayName("Create and update Limit to lower value")
    @Story("Create and update Limit to lower value")
    @Description("Create and update Limit to lower value")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and updated value = {6}")
    @CsvFileSource(files = "src/main/resources/updateLimitTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateLimitTest(String type, OwnerEnum owner,
                                         LabelEnums label,String product,
                                         Double value,String interval,Double updatedValue){
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        LimitsResponseData response = new PutCreateLimitEndpoint()
                .sendRequestToCreateNewLimit(body,new OauthHelper().getUserToken(userHelper.getGermanUserName()
                        ,userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getLabel()).isEqualTo(body.getLabel().toString());
        Assertions.assertThat(response.getOwner()).isEqualTo(body.getOwner().toString());
        Assertions.assertThat(response.getProduct()).isEqualTo(body.getProduct());
        Assertions.assertThat(response.getCreatedBy()).isNotEmpty();
        LimitCreationData updatedBody = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(updatedValue)
                .interval(interval)
                .build();
        LimitsResponseData responseData = new  PutCreateLimitEndpoint()
                .sendRequestToUpdateLimit(updatedBody,new OauthHelper().getUserToken(userHelper.getGermanUserName(),
                        userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(responseData.getCurrent().getValue().doubleValue()).describedAs("check if " +
                "value of the limit is updated to lower value").isEqualTo(updatedBody.getValue());
        Assertions.assertThat(responseData.getCurrent().getInterval()).isEqualTo(updatedBody.getInterval());
        Assertions.assertThat(response.getOwner()).isEqualTo(responseData.getOwner());
    }

    @DisplayName("Create and update Limit to higher value")
    @Story("Create and update Limit to higher value")
    @Description("Create and update Limit to higher value")
    @ParameterizedTest(name = "{index} -> Creating a limit with User token and with type={0} , owner={1}, " +
            "label={2}, product={3}, value={4} , interval={5} and updated value = {6}")
    @CsvFileSource(files = "src/main/resources/updateLimitToHigherValueTestData.csv", numLinesToSkip = 1)
    public void createAndUpdateLimitToHigherValueTest(String type, OwnerEnum owner,
                                         LabelEnums label,String product,
                                         Double value,String interval,Double updatedValue){
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        LimitsResponseData response = new PutCreateLimitEndpoint()
                .sendRequestToCreateNewLimit(body,new OauthHelper().getUserToken(userHelper.getGermanUserName()
                        ,userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getLabel()).isEqualTo(body.getLabel().toString());
        Assertions.assertThat(response.getOwner()).isEqualTo(body.getOwner().toString());
        Assertions.assertThat(response.getProduct()).isEqualTo(body.getProduct());
        Assertions.assertThat(response.getCreatedBy()).isNotEmpty();
        LimitCreationData updatedBody = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(updatedValue)
                .interval(interval)
                .build();
        LimitsResponseData responseData = new  PutCreateLimitEndpoint()
                .sendRequestToUpdateLimit(updatedBody,new OauthHelper().getUserToken(userHelper.getGermanUserName(),
                        userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(responseData.getCurrent().getValue().doubleValue()).describedAs("check if " +
                "value of the limit is not updated to higher value").isEqualTo(body.getValue());
        Assertions.assertThat(responseData.getFuture().getValue().doubleValue()).describedAs("check if " +
                "value of the limit is not updated to higher value").isEqualTo(updatedBody.getValue());
        Assertions.assertThat(responseData.getCurrent().getInterval()).isEqualTo(updatedBody.getInterval());
        Assertions.assertThat(response.getOwner()).isEqualTo(responseData.getOwner());
    }
}
