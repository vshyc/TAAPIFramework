package customer.stake.counters.tests;


import configuration.BaseTest;
import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.pojo.counters.CustomerFiguresResponse;
import customer.stake.rop.GetCustomersFiguresEndpoint;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Locale;

@DisplayName("GET Endpoint for Customer Stake Service Tests")
public class GetCountersTest extends BaseTest {

    @ParameterizedTest(name = "{index} -> Get customers limits with label ={0}")
    @EnumSource(LabelEnums.class)
    @DisplayName("Get call to CSS with labels:")
    @Description("Checking counters for user using all labels")
    @Tag("SmokeTests")
    public void getCustomerCountersTest(LabelEnums label){
        String userId = "100";
        CustomerFiguresResponse response =new GetCustomersFiguresEndpoint().sendRequest(userId,
                CounterTypeEnum.PAYIN,label)
                .assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getCustomer().getId()).isEqualTo(userId);
        Assertions.assertThat(response.getLabel()).isEqualTo(label);
    }


}
