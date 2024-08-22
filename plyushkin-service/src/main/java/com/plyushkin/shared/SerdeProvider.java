package com.plyushkin.shared;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.format.Formatter;

public interface SerdeProvider<T> {
    JsonDeserializer<T> deserializer();

    JsonSerializer<T> serializer();

    Formatter<T> formatter();

    Class<T> type();
}
