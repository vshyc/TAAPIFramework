package customer.stake.counters.tests;

import com.tipico.ta.reqtest.extension.ReqtestReporterExtension;
import com.tipico.ta.reqtest.extension.TestCaseId;
import configuration.BaseTest;
import configuration.SmokeRegressionTag;
import customer.stake.enums.CounterType;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.dto.counters.CustomerFiguresResponse;
import customer.stake.rop.GetCustomersFiguresEndpoint;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("GET Endpoint for Customer Stake Service Tests")
@ExtendWith(ReqtestReporterExtension.class)
public class GetCountersTest extends BaseTest {
    String userId = "100";
    String userUuid = "17467f0e-3192-408a-85cc-422643f2f858";

    @ParameterizedTest(name = "{index} -> Get customers limits with label ={0},counter type = {1} and interval = {2}" +
            " on id field should return 404 code")
    @DisplayName("Get call to CSS with labels on id endpoint")
    @Description("Checking counters for user using all labels, counter types and intervals on id endpoint should return " +
            "404 status code")
    @SmokeRegressionTag
    @TestCaseId(3469)
    @CsvFileSource(files = "src/test/resources/getCountersFromCustomerStakeService.csv", numLinesToSkip = 1)
    public void getCustomerCountersTest(Label label, CounterType counter, Interval interval) {
         new GetCustomersFiguresEndpoint().sendRequest(userId, counter, label, interval).assertNotFoundStatusCode();
    }

    @ParameterizedTest(name = "{index} -> Get customers limits with label = {0},counter type = {1} and interval = {2}")
    @DisplayName("Get call to CSS with labels on uuid endpoint")
    @Description("Checking counters for user using all labels and diff interval")
    @SmokeRegressionTag
    @TestCaseId(3470)
    @CsvFileSource(files = "src/test/resources/getCountersFromCustomerStakeService.csv", numLinesToSkip = 1)
    public void getCustomerCountersWithUuidTest(Label label, CounterType counter, Interval interval) {
        CustomerFiguresResponse response = new GetCustomersFiguresEndpoint().sendRequestWithUuid(userUuid,
                        counter, label, interval)
                .assertRequestStatusCode().getResponseModel();
        SoftAssertions.assertSoftly(softly->{
            softly.assertThat(response.getCustomer().getUuid()).isEqualTo(userUuid);
            softly.assertThat(response.getLabel()).isEqualTo(label);
        });
    }

    @ParameterizedTest(name = "{index} -> Get customers limits with label = {0},counter type = {1}, interval = {2}" +
            " dateFrom = {3} and dateTo = {4}")
    @DisplayName("Get call to CSS with labels and dates on uuid endpoint ")
    @Description("Checking counters for user using all labels and diff interval")
    @SmokeRegressionTag
    @TestCaseId(3471)
    @CsvFileSource(files = "src/test/resources/getCountersFromCustomerStakeServiceWithDates.csv", numLinesToSkip = 1)
    public void getCustomerCountersWithUuidTest(Label label, CounterType counter, String dateFrom, String dateTo) {
        CustomerFiguresResponse response = new GetCustomersFiguresEndpoint().sendRequestWithUuidAndDate(userUuid,
                        counter, label, dateFrom, dateTo)
                .assertRequestStatusCode().getResponseModel();
        SoftAssertions.assertSoftly(softly-> {
                softly.assertThat(response.getCustomer().getUuid()).isEqualTo(userUuid);
                softly.assertThat(response.getLabel()).isEqualTo(label);
        });
    }


}
