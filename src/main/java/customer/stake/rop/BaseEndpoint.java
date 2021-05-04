package customer.stake.rop;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.lang.reflect.Type;

public abstract class BaseEndpoint<E,M> {

    protected Response response;


    protected abstract Type getModelType();


    protected abstract int getSuccessStatusCode();

    public M getResponseModel(){
        return response.as(getModelType());
    }

    public E assertRequestStatusCode(){
        return assertStatusCode(getSuccessStatusCode());
    }

    public E assertStatusCode(int statusCode){
        Assertions.assertThat(response.getStatusCode()).as("Status Code").isEqualTo(statusCode);
        return (E) this;
    }



}
