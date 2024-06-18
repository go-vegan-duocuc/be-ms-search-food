package cl.govegan.mssearchfood.utils.response;

import cl.govegan.mssearchfood.web.response.ApiEntityResponse;
import cl.govegan.mssearchfood.web.response.ApiListResponse;
import cl.govegan.mssearchfood.web.response.ApiPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseBuilder {

    private ResponseBuilder () {
    }

    public static <T> ResponseEntity<ApiPageResponse<T>> buildPageResponse (String message, Page<T> data) {
        return data.getSize() > 0 ? ResponseEntity.ok(ApiPageResponse.<T>builder().status(HttpStatus.OK.value()).message(message).page(data).build())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiPageResponse.<T>builder().status(HttpStatus.NO_CONTENT.value()).message(message).page(null).build());
    }

    public static <T> ResponseEntity<ApiEntityResponse<T>> buildEntityResponse (String message, T data) {
        return data != null ?
                ResponseEntity.ok(ApiEntityResponse.<T>builder().status(HttpStatus.OK.value()).message(message).data(data).build())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiEntityResponse.<T>builder().status(HttpStatus.NO_CONTENT.value()).message(message).data(null).build());
    }

    public static <T> ResponseEntity<ApiListResponse<T>> buildListResponse (String message, List<T> data) {
        return !data.isEmpty() ?
                ResponseEntity.ok(ApiListResponse.<T>builder().status(HttpStatus.OK.value()).message(message).data(data).build())
                : ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiListResponse.<T>builder().status(HttpStatus.NO_CONTENT.value()).message(message).data(null).build());
    }
}
