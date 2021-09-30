package customer.stake.rop;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;

import java.lang.reflect.Type;

public abstract class BaseEndpoint<E, M> {

    protected Response response;


    protected abstract Type getModelType();

    protected abstract int getSuccessStatusCode();

    public M getResponseModel() {
        return response.as(getModelType());
    }

    public E assertRequestStatusCode() {
        return assertStatusCode(getSuccessStatusCode());
    }

    public E assertNoContentStatusCode() {return assertStatusCode(HttpStatus.SC_NO_CONTENT); }

    public E assertNoAuthRequestStatusCode() {
        return assertStatusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    public E assertUnsupportedMediaTypeRequestStatusCode() {
        return assertStatusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    public E assertBadRequestStatusCode() {
        return assertStatusCode(HttpStatus.SC_BAD_REQUEST);
    }
    public E assertNotFoundStatusCode() {
        return assertStatusCode(HttpStatus.SC_NOT_FOUND);
    }

    public E assertStatusCode(int statusCode) {
        Assertions.assertThat(response.getStatusCode()).as("Status Code").isEqualTo(statusCode);
        return (E) this;
    }

    public Response getResponse() {
        return response;
    }


}
