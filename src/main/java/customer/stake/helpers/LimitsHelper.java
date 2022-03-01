package customer.stake.helpers;

import customer.stake.dto.counters.PostCountersResponse;
import customer.stake.dto.limits.LimitCreationData;
import customer.stake.dto.limits.LimitsResponseData;
import customer.stake.dto.limits.history.LimitsHistory;
import customer.stake.enums.CounterType;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.enums.LimitType;
import customer.stake.enums.Owner;
import customer.stake.enums.Product;
import customer.stake.rop.GetRGLSLimitHistoryEndpoint;
import customer.stake.rop.PutLimitEndpoint;
import io.qameta.allure.Step;

public class LimitsHelper {


    @Step("Sending a call to Limit Service with User Token to create Limit")
    public LimitsResponseData createLimitWithUserToken(UserHelper userHelper,String uuid,LimitType type, Owner owner,
                                                        Label label, Product product,
                                                        Double value, Interval interval) {
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToCreateNewLimit(body, new OauthHelper()
                        .getUserToken(userHelper.getGermanUserName(), userHelper.getGermanUserPassword()), uuid)
                .assertRequestStatusCode().getResponseModel();
    }

    @Step("Sending a call to Limit Service with Application Token to create Limit")
    public LimitsResponseData createLimitWithApplicationToken(String uuid,LimitType type, Owner owner,
                                                               Label label, Product product,
                                                               Double value, Interval interval) {
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToCreateNewLimit(body, new OauthHelper().getApplicationToken(), uuid)
                .assertRequestStatusCode().getResponseModel();
    }

    @Step("Sending a call to Limit Service with Application Token to create Limit")
    public LimitsResponseData createLimitWithApplicationToken(String uuid,LimitType type, Owner owner,
                                                               Label label, Product product,
                                                               Double value, Interval interval,String headerUUID) {
        LimitCreationData body = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToCreateNewLimit(body,
                        new OauthHelper().getApplicationToken(), uuid,headerUUID)
                .assertRequestStatusCode().getResponseModel();
    }

    @Step("Sending a call to Limit Service with User Token to update Limit")
    public LimitsResponseData updateLimit(UserHelper userHelper,String uuid,LimitType type, Owner owner,
                                           Label label, Product product,
                                           Double value, Interval interval) {
        LimitCreationData updatedBody = LimitCreationData.builder().
                type(type)
                .owner(owner)
                .label(label)
                .product(product)
                .value(value)
                .interval(interval)
                .build();
        return new PutLimitEndpoint().sendRequestToUpdateLimit(updatedBody, new OauthHelper()
                        .getUserToken(userHelper.getGermanUserName(), userHelper.getGermanUserPassword()), uuid)
                .assertRequestStatusCode().getResponseModel();

    }

    @Step("Add counter for limit")
    public PostCountersResponse addCounterForLimit(String uuid, String id, Label label, CounterType type, Double amount) {
        return new AddCounterHelper().addSingleCounterToCustomerStakeService(uuid, id, label,
                type, amount);
    }

    @Step("Send request to Get limit")
    public LimitsResponseData getLimit(String uuid, Owner owner, LimitType type, Label label) {
        return new GetLimitsHelper().checkIfLimitExistForUser(uuid, owner, type, label);
    }

    @Step("Check if limit exists in history response")
    private LimitsHistory checkIfLimitExistForUser(String uuid, Owner limitOwner, LimitType limitType, Label label) {

        GetRGLSLimitHistoryEndpoint responseData = new GetRGLSLimitHistoryEndpoint().sendRequest(uuid);
        LimitsHistory data;

        data = (responseData.getResponse().getStatusCode() == 200) ?
                responseData.getResponseModel().getLimitsHistory().stream()
                        .filter(response -> uuid.equals(response.getCustomerUUID()))
                        .filter(response -> limitOwner.equals(response.getOwner()))
                        .filter(response -> limitType.equals(response.getType()))
                        .filter(response -> label.equals(response.getLabel()))
                        .findAny()
                        .orElse(null) : null;
        return data;
    }
}
