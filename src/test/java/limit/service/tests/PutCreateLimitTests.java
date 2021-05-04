package limit.service.tests;

import configuration.BaseTest;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.OwnerEnum;
import customer.stake.pojo.limits.GetLimitsResponseData;
import customer.stake.pojo.limits.LimitCreationData;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.rop.PutCreateLimitEndpoint;
import customer.stake.helpers.OauthHelper;
import customer.stake.helpers.UserHelper;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;


public class PutCreateLimitTests extends BaseTest {

    private UserHelper userHelper;
    private String uuid;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void setUp(){
        userHelper = new UserHelper();
        uuid = userHelper.createGermanUserAndGetUuid();
    }
    @ParameterizedTest
    @EnumSource(LabelEnums.class)
    public void createDepositLimitTestWithApplicationTokenTest(@NotNull LabelEnums label){
        LimitCreationData body = LimitCreationData.builder().
                type("deposit")
                .owner(OwnerEnum.PERSONAL)
                .label(label)
                .product("sports")
                .value(900.0)
                .interval("MONTH")
                .build();
        LimitsResponseData response =new PutCreateLimitEndpoint().sendRequestToCreateNewLimit(body,new OauthHelper().getApplicationToken(),uuid)
                .assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getLabel()).isEqualTo(body.getLabel().toString());
        Assertions.assertThat(response.getOwner()).isEqualTo(body.getOwner().toString());
        Assertions.assertThat(response.getProduct()).isEqualTo(body.getProduct());
        Assertions.assertThat(response.getCreatedBy()).isEmpty();
        GetLimitsResponseData getResponse = given().auth().oauth2(new OauthHelper().getApplicationToken())
                .baseUri(envConfig.baseUri()).basePath(envConfig.limitsPath()).when().get("customers/{customerUuid}/limits/",uuid).then()
                .statusCode(200).extract().as(GetLimitsResponseData.class);

        Assertions.assertThat(getResponse.getLimits()).isNotEmpty();
    }


    @ParameterizedTest
    @CsvFileSource(files = "src/main/resources/createLimitTestData.csv", numLinesToSkip = 1)
    public void createDepositLimitTestWithUserTokenTest(String type, OwnerEnum owner,
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
        LimitsResponseData response = new PutCreateLimitEndpoint()
                .sendRequestToCreateNewLimit(body,new OauthHelper().getUserToken(userHelper.getGermanUserName()
                        ,userHelper.getGermanUserPassword()),uuid).assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getLabel()).isEqualTo(body.getLabel().toString());
        Assertions.assertThat(response.getOwner()).isEqualTo(body.getOwner().toString());
        Assertions.assertThat(response.getProduct()).isEqualTo(body.getProduct());
        Assertions.assertThat(response.getCreatedBy()).isNotEmpty();
    }

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
