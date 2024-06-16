package cl.govegan.mssearchfood.utils.custompage;

import java.util.List;

import org.springframework.data.domain.PageRequest;

public class PaginationUtils {

   public static <T> CustomPageImpl<T> createCustomPage(List<T> list, int page, int size) {
      int start = Math.min((int) PageRequest.of(page, size).getOffset(), list.size());
      int end = Math.min((start + PageRequest.of(page, size).getPageSize()), list.size());
      List<T> subList = list.subList(start, end);
      return new CustomPageImpl<>(subList, PageRequest.of(page, size), list.size());
   }

}
