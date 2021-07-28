package demo.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import demo.entity.music_list;

import java.io.IOException;

public class MusicListSerializer extends JsonSerializer<music_list> {

    @Override
    public void serialize(music_list value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("list_id", value.getList_id());
        gen.writeStringField("list_name", value.getList_name());
        gen.writeStringField("time", value.getTime().toString());
        gen.writeStringField("u_phone", value.getUser().getPhone());
        gen.writeEndObject();

    }
}
