package customer.stake.helpers;

import customer.stake.enums.OwnerEnum;
import customer.stake.pojo.limits.LimitsResponseData;
import customer.stake.properties.EnvConfig;
import customer.stake.rop.GetLimitEndpoint;
import io.qameta.allure.Step;


public class GetLimitsHelper {

    @Step("Check if limit exists in LimitService")
    public LimitsResponseData checkIfLimitExistForUser(String uuid, OwnerEnum limitOwner, String limitType) {

        GetLimitEndpoint responseData = new GetLimitEndpoint().sendRequest(uuid);
        LimitsResponseData data;

        data =  (responseData.getResponse().getStatusCode() == 200) ?
         responseData.getResponseModel().getLimits().stream()
                .filter(response -> limitOwner.toString().equals(response.getOwner()))
                .filter(response -> limitType.equals(response.getType()))
                .findAny()
                .orElse(null) :  null;
        return data;
    }


}
