package com.plyushkin.infra.web;

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

abstract class NumberSerdeProvider<T, N extends Number> implements SerdeProvider<T> {
    @Override
    public JsonSerializer<T> serializer() {
        return new JsonSerializer<>() {
            @Override
            public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(asNumber(value).toString());
            }
        };
    }

    @Override
    public JsonDeserializer<T> deserializer() {
        return new JsonDeserializer<>() {
            @Override
            public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return asEntity(getNumberValue(p));
            }
        };
    }

    @Override
    public Formatter<T> formatter() {
        return new Formatter<>() {
            @Override
            public T parse(String text, Locale locale) throws ParseException {
                return asEntity(parseNumber(text));
            }

            @Override
            public String print(T object, Locale locale) {
                return String.valueOf(asNumber(object));
            }
        };
    }

    public abstract T asEntity(N value);

    public abstract N asNumber(T value);

    public abstract N parseNumber(String text);

    public abstract N getNumberValue(JsonParser p);
}
