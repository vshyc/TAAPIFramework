package limit.service.tests;

import com.tipico.ta.reqtest.extension.ReqtestReporterExtension;
import configuration.BaseTest;
import customer.stake.dto.counters.PostCountersResponse;
import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.ValidatorResultEnum;
import customer.stake.helpers.AddCounterHelper;
import customer.stake.helpers.UserHelper;
import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@DisplayName("Tests checking the LS Validators")
@ExtendWith(ReqtestReporterExtension.class)
public class PostCountersAndLSValidatorsTests extends BaseTest {
    private UserHelper userHelper;
    private String uuid;
    private String id;
    private JsonPath createdUser;

    @BeforeEach
    @Step("Create a user for test ")
    public void setUp() {
        userHelper = new UserHelper();
        createdUser = userHelper.createGermanUserInWebTestApi();
        uuid = userHelper.getUuid(createdUser);
        id = userHelper.getId(createdUser);
    }

    @Test
    @DisplayName("Check validation response when incrementing the counter")
    public void checkIfThereIsProperValidatorResponseWhenIncrementingTheCounter(){
        PostCountersResponse response = new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid, id, LabelEnums.tipico,
                CounterTypeEnum.LOSS_BET, 100d);
        Assertions.assertThat(response.getLabel()).isEqualTo(LabelEnums.tipico);
        Assertions.assertThat(response.getAttributes().getAtribute(CounterTypeEnum.LOSS_BET).stream().reduce(0d, Double::sum))
                .describedAs("Check if sum off all counters for new user is equal to amount").isEqualTo(100d);
        Assertions.assertThat(response.getValidationResponse().getLimitForCounter(CounterTypeEnum.LOSS_BET).get(0).getValidationResult())
                .isEqualTo(ValidatorResultEnum.VALID_WITH_NO_VALIDATOR_DEFINED);
    }

}

