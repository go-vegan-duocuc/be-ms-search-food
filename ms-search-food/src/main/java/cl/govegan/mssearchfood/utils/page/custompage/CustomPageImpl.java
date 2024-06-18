package cl.govegan.mssearchfood.utils.page.custompage;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonSerialize(using = PageSerializer.class)
public class CustomPageImpl<T> extends PageImpl<T> {

    public CustomPageImpl (List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CustomPageImpl (List<T> content) {
        super(content);
    }
}
