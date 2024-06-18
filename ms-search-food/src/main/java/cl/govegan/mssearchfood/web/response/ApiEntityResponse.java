package cl.govegan.mssearchfood.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@SuperBuilder
public class ApiEntityResponse<T> extends ApiResponse {
    private T data;
}
