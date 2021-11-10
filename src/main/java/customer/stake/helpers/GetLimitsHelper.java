package customer.stake.helpers;

import customer.stake.enums.Label;
import customer.stake.enums.Owner;
import customer.stake.enums.LimitType;
import customer.stake.dto.limits.LimitsResponseData;
import customer.stake.rop.GetLimitEndpoint;
import io.qameta.allure.Step;


public class GetLimitsHelper {

    @Step("Check if limit exists in LimitService")
    public LimitsResponseData checkIfLimitExistForUser(String uuid, Owner limitOwner, LimitType limitType, Label label) {

        GetLimitEndpoint responseData = new GetLimitEndpoint().sendRequest(uuid);
        LimitsResponseData data;

        data = (responseData.getResponse().getStatusCode() == 200) ?
                responseData.getResponseModel().getLimits().stream()
                        .filter(response -> limitOwner.equals(response.getOwner()))
                        .filter(response -> limitType.equals(response.getType()))
                        .filter(response -> label.equals(response.getLabel()))
                        .findAny()
                        .orElse(null) : null;
        return data;
    }


}
