package com.plyushkin.infra.web;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;

interface SerdeProvider<T> {
    JsonDeserializer<T> deserializer();

    JsonSerializer<T> serializer();

    Class<T> type();
}
