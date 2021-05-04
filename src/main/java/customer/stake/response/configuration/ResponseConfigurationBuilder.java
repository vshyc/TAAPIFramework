package customer.stake.response.configuration;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class ResponseConfigurationBuilder {

    public ResponseSpecBuilder getResponseSpecBuilder() {
        return new ResponseSpecBuilder().expectStatusCode(HttpStatus.SC_OK);
    }

    public static ResponseSpecification getDefaultResponseSpecification() {
        return new ResponseConfigurationBuilder().getResponseSpecBuilder().build();
    }

}