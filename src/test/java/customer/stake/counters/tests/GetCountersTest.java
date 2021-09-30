package customer.stake.counters.tests;

import configuration.BaseTest;
import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.IntervalEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.pojo.counters.CustomerFiguresResponse;
import customer.stake.rop.GetCustomersFiguresEndpoint;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("GET Endpoint for Customer Stake Service Tests")
public class GetCountersTest extends BaseTest {
    String userId = "100";
    String userUuid = "17467f0e-3192-408a-85cc-422643f2f858";

    @ParameterizedTest(name = "{index} -> Get customers limits with label ={0},counter type = {1} and interval = {2}" +
            " on id field should return 404 code")
    @DisplayName("Get call to CSS with labels on id endpoint")
    @Description("Checking counters for user using all labels, counter types and intervals on id field should return " +
            "400 status code")
    @Tag("SmokeTests")
    @CsvFileSource(files = "src/test/resources/getCountersFromCustomerStakeService.csv", numLinesToSkip = 1)
    public void getCustomerCountersTest(LabelEnums label, CounterTypeEnum counter, IntervalEnum interval) {
         new GetCustomersFiguresEndpoint().sendRequest(userId, counter, label, interval).assertNotFoundStatusCode();
    }

    @ParameterizedTest(name = "{index} -> Get customers limits with label = {0},counter type = {1} and interval = {2}")
    @DisplayName("Get call to CSS with labels on uuid endpoint")
    @Description("Checking counters for user using all labels and diff interval")
    @Tag("SmokeTests")
    @CsvFileSource(files = "src/test/resources/getCountersFromCustomerStakeService.csv", numLinesToSkip = 1)
    public void getCustomerCountersWithUuidTest(LabelEnums label, CounterTypeEnum counter, IntervalEnum interval) {
        CustomerFiguresResponse response = new GetCustomersFiguresEndpoint().sendRequestWithUuid(userUuid,
                        counter, label, interval)
                .assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getCustomer().getUuid()).isEqualTo(userUuid);
        Assertions.assertThat(response.getLabel()).isEqualTo(label);
    }

    @ParameterizedTest(name = "{index} -> Get customers limits with label = {0},counter type = {1}, interval = {2}" +
            " dateFrom = {3} and dateTo = {4}")
    @DisplayName("Get call to CSS with labels and dates on uuid endpoint ")
    @Description("Checking counters for user using all labels and diff interval")
    @Tag("SmokeTests")
    @CsvFileSource(files = "src/test/resources/getCountersFromCustomerStakeServiceWithDates.csv", numLinesToSkip = 1)
    public void getCustomerCountersWithUuidTest(LabelEnums label, CounterTypeEnum counter, String dateFrom, String dateTo) {
        CustomerFiguresResponse response = new GetCustomersFiguresEndpoint().sendRequestWithUuidAndDate(userUuid,
                        counter, label, dateFrom, dateTo)
                .assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getCustomer().getUuid()).isEqualTo(userUuid);
        Assertions.assertThat(response.getLabel()).isEqualTo(label);
    }


}
