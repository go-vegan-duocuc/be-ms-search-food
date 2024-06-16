package cl.govegan.mssearchfood.utils.response;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.rpc.context.AttributeContext;

import cl.govegan.mssearchfood.web.response.ApiPageResponse;

public class ResponseBuilder {

   private ResponseBuilder() {
   }
   
   public static <T> ResponseEntity<ApiPageResponse<T>> buildPageResponse (String successMsg, String failureMsg, Page<T> data) {

      return ResponseEntity.ok(new ApiPageResponse<T>(HttpStatus.OK.value(), successMsg, data));
   }

}
