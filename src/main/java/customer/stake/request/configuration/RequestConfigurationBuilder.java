package customer.stake.request.configuration;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class RequestConfigurationBuilder {

    public RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder()
                .setConfig(RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().
                        defaultObjectMapperType(ObjectMapperType.JACKSON_2)))
                .setContentType(ContentType.JSON);
    }

    public static RequestSpecification getDefaultRequestSpecification() {
        return new RequestConfigurationBuilder().getRequestSpecBuilder().build();
    }

}