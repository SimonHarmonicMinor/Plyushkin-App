package com.plyushkin.infra.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

abstract class LongSerdeProvider<T> implements SerdeProvider<T> {
    @Override
    public JsonSerializer<T> serializer() {
        return new JsonSerializer<>() {
            @Override
            public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeNumber(asLong(value));
            }
        };
    }

    @Override
    public JsonDeserializer<T> deserializer() {
        return new JsonDeserializer<>() {
            @Override
            public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return asEntity(p.getLongValue());
            }
        };
    }

    public abstract T asEntity(long value);

    public abstract long asLong(T value);
}
