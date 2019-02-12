package com.pawpaw.framework.core.factory.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * 内部包装了{@link ObjectMapper}
 *
 * @author liujixin
 * @date 2019-02-11
 * @description
 */
public class PawpawObjectMapper {

    private ObjectMapper om;

    /**
     * 同包下可以访问，避免被外部调用
     */
    PawpawObjectMapper(ObjectMapper objectMapper) {
        this.om = objectMapper;

    }

    public ObjectMapper getRawObjectMapper() {
        return this.om;
    }

    public byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
        return this.om.writeValueAsBytes(value);
    }

    public String writeValueAsString(Object value) throws JsonProcessingException {
        return this.om.writeValueAsString(value);
    }

    public <T> T readValue(String content, TypeReference valueTypeRef) throws IOException {
        return this.om.readValue(content, valueTypeRef);
    }

}

