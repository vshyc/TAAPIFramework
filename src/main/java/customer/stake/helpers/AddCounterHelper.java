package customer.stake.helpers;

import customer.stake.enums.CounterTypeEnum;
import customer.stake.enums.LabelEnums;
import customer.stake.dto.counters.Attribute;
import customer.stake.dto.counters.Customer;
import customer.stake.dto.counters.PostCountersRequest;
import customer.stake.dto.counters.PostCountersResponse;
import customer.stake.rop.PostCustomerFiguresEndpoint;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddCounterHelper {

    public PostCountersResponse addSingleCounterToCustomerStakeService(String uuid, String id, LabelEnums label,
                                                                       CounterTypeEnum type, double amount) {
        Attribute attribute = new Attribute(type, amount);

        PostCountersRequest body = new PostCountersRequest(new Customer(uuid, id), label,
                Collections.singletonList(attribute));

        return new PostCustomerFiguresEndpoint().sendRequest(body).assertRequestStatusCode()
                .getResponseModel();
    }
    public PostCountersResponse addMultipleBettingCounterToCustomerStakeService(String uuid, String id, LabelEnums label,
                                                                         Double betAmount) {

        PostCountersRequest body = new PostCountersRequest(new Customer(uuid, id), label,
                createBetAttributeList(betAmount));

        return new PostCustomerFiguresEndpoint().sendRequest(body).assertRequestStatusCode()
                .getResponseModel();
    }

    public PostCountersResponse addMultiplePayinCounterToCustomerStakeService(String uuid, String id, LabelEnums label,
                                                                                Double payinAmount) {

        PostCountersRequest body = new PostCountersRequest(new Customer(uuid, id), label,
                createPayinAttributeList(payinAmount));

        return new PostCustomerFiguresEndpoint().sendRequest(body).assertRequestStatusCode()
                .getResponseModel();
    }

    private @NotNull List<Attribute> createBetAttributeList(Double amount){
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(CounterTypeEnum.STAKE_BET,amount));
        attributes.add(new Attribute(CounterTypeEnum.STAKE,amount));
        attributes.add(new Attribute(CounterTypeEnum.LOSS_BET,amount));
        attributes.add(new Attribute(CounterTypeEnum.LOSS_STAKE,amount));
        return attributes;
    }

    private @NotNull List<Attribute> createPayinAttributeList(Double amount){
        List<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute(CounterTypeEnum.PAYIN,amount));
        attributes.add(new Attribute(CounterTypeEnum.PAYIN_BET,amount));
        return attributes;
    }
}
