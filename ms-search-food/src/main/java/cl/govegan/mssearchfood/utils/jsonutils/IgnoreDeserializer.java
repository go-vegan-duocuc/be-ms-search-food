package cl.govegan.mssearchfood.utils.jsonutils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class IgnoreDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize (JsonParser p, DeserializationContext ctxt) {
        return null;
    }
}
