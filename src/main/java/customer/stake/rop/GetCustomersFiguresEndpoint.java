package customer.stake.rop;

import customer.stake.enums.CounterType;
import customer.stake.enums.Interval;
import customer.stake.enums.Label;
import customer.stake.helpers.HelpersConfig;
import customer.stake.dto.counters.CustomerFiguresResponse;
import customer.stake.properties.EnvConfig;
import customer.stake.request.configuration.RequestConfigurationBuilder;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class GetCustomersFiguresEndpoint extends BaseEndpoint<GetCustomersFiguresEndpoint, CustomerFiguresResponse> {

    private EnvConfig envConfig = HelpersConfig.createConfiguration();
    private String user = envConfig.basicUser();
    private String password = envConfig.basicPassword();

    @Override
    protected Type getModelType() {
        return CustomerFiguresResponse.class;
    }


    public GetCustomersFiguresEndpoint sendRequest(String id, CounterType type, Label label) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().basic(user, password).when().get("/risks/{id}/{type}?label={label}", id, type.getType(),
                        label.getLabel());
        return this;
    }

    public GetCustomersFiguresEndpoint sendRequest(String id, CounterType type, Label label, Interval interval) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().basic(user, password).when().get("/risks/{id}/{type}?label={label}&interval={interval}", id, type.getType(),
                        label.getLabel(), interval.getInterval());
        return this;
    }

    public GetCustomersFiguresEndpoint sendRequestWithUuid(String uuid, CounterType type, Label label, Interval interval) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().basic(user, password).when().get("/risks/uuid/{uuid}/{type}?label={label}&interval={interval}", uuid, type.getType(),
                        label.getLabel(), interval.getInterval());
        return this;
    }

    public GetCustomersFiguresEndpoint sendRequestWithUuidAndDate(String uuid, CounterType type, Label label,
                                                                  String dateFrom, String dateTo) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().basic(user, password).when().get("/risks/uuid/{uuid}/{type}?label={label}" +
                                "&dateFrom={dateFrom}&dateTo={dateTo}", uuid, type.getType(),
                        label.getLabel(), dateFrom, dateTo);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}
