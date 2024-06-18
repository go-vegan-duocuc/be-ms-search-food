package cl.govegan.mssearchfood.utils.page.custompage;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;

public class PageSerializer extends JsonSerializer<PageImpl<?>> {

    @Override
    public void serialize (PageImpl<?> t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeStartObject();
        jg.writeNumberField("totalElements", t.getTotalElements());
        jg.writeNumberField("totalPages", t.getTotalPages());
        jg.writeNumberField("number", t.getNumber());
        jg.writeNumberField("size", t.getSize());
        jg.writeBooleanField("first", t.isFirst());
        jg.writeBooleanField("last", t.isLast());
        jg.writeBooleanField("empty", t.isEmpty());
        jg.writeFieldName("content");
        sp.defaultSerializeValue(t.getContent(), jg);
        jg.writeEndObject();
    }

}
