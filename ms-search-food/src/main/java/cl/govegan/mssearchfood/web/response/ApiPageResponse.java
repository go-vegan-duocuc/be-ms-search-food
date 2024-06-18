package cl.govegan.mssearchfood.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@SuperBuilder
public class ApiPageResponse<T> extends ApiResponse {
    private Page<T> page;
}