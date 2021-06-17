package customer.stake.helpers;

import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.enums.LimitTypeEnum;
import customer.stake.pojo.counters.Attribute;
import customer.stake.pojo.counters.Customer;
import customer.stake.pojo.counters.PostCountersRequest;
import customer.stake.pojo.counters.PostCountersResponse;
import customer.stake.rop.PostCustomerFiguresEndpoint;

import java.util.Collections;

public class AddCounterHelper {

    public PostCountersResponse addSingleCounterToCustomerStakeService(String uuid, String id, LabelEnums label,
                                                                       CounterTypeEnum type, double amount) {
        Attribute attribute = new Attribute(type, amount);

        PostCountersRequest body = new PostCountersRequest(new Customer(uuid, id), label,
                Collections.singletonList(attribute));

        return new PostCustomerFiguresEndpoint().sendRequest(body).assertRequestStatusCode()
                .getResponseModel();
    }
}
