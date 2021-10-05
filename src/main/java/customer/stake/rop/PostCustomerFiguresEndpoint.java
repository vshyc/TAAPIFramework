package customer.stake.rop;

import customer.stake.helpers.HelpersConfig;
import customer.stake.dto.counters.PostCountersRequest;
import customer.stake.dto.counters.PostCountersResponse;
import customer.stake.properties.EnvConfig;
import customer.stake.request.configuration.RequestConfigurationBuilder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class PostCustomerFiguresEndpoint extends BaseEndpoint<PostCustomerFiguresEndpoint, PostCountersResponse> {
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    @Override
    protected Type getModelType() {
        return PostCountersResponse.class;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }

    public PostCustomerFiguresEndpoint sendRequest(PostCountersRequest body) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification()).auth().preemptive()
                .basic(envConfig.basicUser(), envConfig.basicPassword())
                .body(body).when().post("/risks");
        return this;

    }
}

