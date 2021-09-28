package customer.stake.counters.tests;


import configuration.BaseTest;
import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.IntervalEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.pojo.counters.CustomerFiguresResponse;
import customer.stake.rop.GetCustomersFiguresEndpoint;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("GET Endpoint for Customer Stake Service Tests")
public class GetCountersTest extends BaseTest {
    String userId = "100";

    @Disabled("Disabled after decommission of id field in DB")
    @ParameterizedTest(name = "{index} -> Get customers limits with label ={0}")
    @DisplayName("Get call to CSS with labels:")
    @Description("Checking counters for user using all labels")
    @Tag("SmokeTests")
    @CsvFileSource(files = "src/test/resources/getCountersFromCustomerStakeService.csv",numLinesToSkip = 1)
    public void getCustomerCountersTest(LabelEnums label, CounterTypeEnum counter, IntervalEnum interval){
        CustomerFiguresResponse response =new GetCustomersFiguresEndpoint().sendRequest(userId,
                counter,label,interval)
                .assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getCustomer().getId()).isEqualTo(userId);
        Assertions.assertThat(response.getLabel()).isEqualTo(label);
    }


}
