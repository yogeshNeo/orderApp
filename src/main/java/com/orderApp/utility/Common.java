package com.orderApp.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Common {

    private final ObjectMapper objectMapper;

    public String objectToJsonString(Object object) {
        String data;
        try {
            data = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error while jsonStringToObject {}", e.toString());
            data = null;
        }
        return data;
    }

    public <T> List<T> jsonStringToListObject(String redisResponse, Class<T> classA) {
        try {
            return objectMapper.readValue(redisResponse, TypeFactory.defaultInstance().constructCollectionType(List.class, classA));
        } catch (Exception e) {
            log.error("jsonStringToObject {}", e.toString());
            return Collections.emptyList();
        }
    }

}
