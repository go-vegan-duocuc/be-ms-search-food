package cl.govegan.mssearchfood.utils.custompage;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = PageSerializer.class)
public class CustomPageImpl<T> extends PageImpl<T> {

    public CustomPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CustomPageImpl(List<T> content) {
        super(content);
    }
}
