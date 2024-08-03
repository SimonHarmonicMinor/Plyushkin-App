package com.plyushkin.infra.web;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.format.Formatter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

abstract class StringSerdeProvider<T> implements SerdeProvider<T> {
    @Override
    public JsonDeserializer<T> deserializer() {
        return new JsonDeserializer<>() {
            @Override
            public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                return asEntity(p.getValueAsString());
            }
        };
    }

    @Override
    public JsonSerializer<T> serializer() {
        return new JsonSerializer<>() {
            @Override
            public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(asString(value));
            }
        };
    }

    @Override
    public Formatter<T> formatter() {
        return new Formatter<>() {
            @Override
            public T parse(String text, Locale locale) throws ParseException {
                return asEntity(text);
            }

            @Override
            public String print(T object, Locale locale) {
                return asString(object);
            }
        };
    }

    public abstract T asEntity(String rawValue);

    public abstract String asString(T value);
}
