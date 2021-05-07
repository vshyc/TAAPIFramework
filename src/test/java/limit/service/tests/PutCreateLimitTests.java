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
import org.junit.jupiter.api.Test;
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

    @DisplayName("Create and update Limit")
    @Story("Create and update Limit ")
    @Description("Create and update Limit")
    @Test
    public void createAndUpdateLimitTest(){
        LimitCreationData body = LimitCreationData.builder().
                type("deposit")
                .owner(OwnerEnum.PERSONAL)
                .label(LabelEnums.TIPICO)
                .product("sports")
                .value(900.0)
                .interval("MONTH")
                .build();
        LimitsResponseData response = new PutCreateLimitEndpoint()
                .sendRequestToCreateNewLimit(body,new OauthHelper().getUserToken(userHelper.getGermanUserName()
                        ,userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getLabel()).isEqualTo(body.getLabel().toString());
        Assertions.assertThat(response.getOwner()).isEqualTo(body.getOwner().toString());
        Assertions.assertThat(response.getProduct()).isEqualTo(body.getProduct());
        Assertions.assertThat(response.getCreatedBy()).isNotEmpty();
        LimitCreationData updatedBody = LimitCreationData.builder().
                type("deposit")
                .owner(OwnerEnum.PERSONAL)
                .label(LabelEnums.TIPICO)
                .product("sports")
                .value(800.0)
                .interval("MONTH")
                .build();
        LimitsResponseData responseData = new  PutCreateLimitEndpoint()
                .sendRequestToUpdateLimit(updatedBody,new OauthHelper().getUserToken(userHelper.getGermanUserName(),
                        userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(responseData.getCurrent().getValue().doubleValue()).isEqualTo(updatedBody.getValue());


    }
}
