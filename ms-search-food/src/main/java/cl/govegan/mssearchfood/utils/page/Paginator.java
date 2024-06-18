package cl.govegan.mssearchfood.utils.page;

import org.springframework.data.domain.Pageable;

public class Paginator {
    private Paginator () {
        throw new IllegalStateException("Utility class");
    }

    public static Pageable getPageable (int page, int size) {
        return org.springframework.data.domain.PageRequest.of(page, size);
    }
}
