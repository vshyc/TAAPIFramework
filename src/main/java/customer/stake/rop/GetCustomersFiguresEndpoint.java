package customer.stake.rop;

import customer.stake.helpers.HelpersConfig;
import customer.stake.pojo.counters.CustomerFiguresResponse;
import customer.stake.properties.EnvConfig;
import customer.stake.request.configuration.RequestConfigurationBuilder;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;

import static io.restassured.RestAssured.given;

public class GetCustomersFiguresEndpoint extends BaseEndpoint<GetCustomersFiguresEndpoint, CustomerFiguresResponse>{

    private EnvConfig envConfig = HelpersConfig.createConfiguration();
    private String user = envConfig.basicUser();
    private String password = envConfig.basicPassword();

    @Override
    protected Type getModelType() {
        return CustomerFiguresResponse.class;
    }


    public GetCustomersFiguresEndpoint sendRequest(int id,String label) {
        response = given().spec(RequestConfigurationBuilder.getDefaultRequestSpecification())
                .auth().basic(user,password).log().all().when().get("/risks/{id}?label={label}",id,label);
        return this;
    }

    @Override
    protected int getSuccessStatusCode() {
        return HttpStatus.SC_OK;
    }
}
