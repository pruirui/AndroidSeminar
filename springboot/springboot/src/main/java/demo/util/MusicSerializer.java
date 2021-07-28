package demo.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import demo.entity.music;

import java.io.IOException;

public class MusicSerializer extends JsonSerializer<music> {

    @Override
    public void serialize(music value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("music_id", value.getMusic_id());
        gen.writeStringField("name", value.getName());
        gen.writeStringField("url", value.getUrl());
        gen.writeStringField("singer", value.getSinger());
        gen.writeStringField("time", value.getTime().toString());
        gen.writeNumberField("status", value.getStatus());
        gen.writeStringField("u_phone", value.getUser().getPhone());
        gen.writeEndObject();
    }
}
