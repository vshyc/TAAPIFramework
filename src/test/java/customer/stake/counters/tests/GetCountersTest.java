package customer.stake.counters.tests;


import configuration.BaseTest;
import customer.stake.enums.LabelEnums;
import customer.stake.pojo.counters.CustomerFiguresResponse;
import customer.stake.rop.GetCustomersFiguresEndpoint;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class GetCountersTest extends BaseTest {

    @ParameterizedTest
    @EnumSource(LabelEnums.class)
    @DisplayName("Get call to CSS with labels:")
    @Description("Checking counters for user using all labels")
    public void getCustomerCountersTest(@NotNull LabelEnums label){
        int userId = 100;
        CustomerFiguresResponse response =new GetCustomersFiguresEndpoint().sendRequest(userId,label.getLabel())
                .assertRequestStatusCode().getResponseModel();
        Assertions.assertThat(response.getCustomer().getId()).isEqualTo(userId);
        Assertions.assertThat(response.getLabel()).isEqualTo(label.getLabel());
    }


}
