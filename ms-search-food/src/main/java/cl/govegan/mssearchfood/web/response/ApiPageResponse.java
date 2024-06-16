package cl.govegan.mssearchfood.web.response;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiPageResponse<T> {
    private int status;
    private String message;
    private Page<T> page;
}