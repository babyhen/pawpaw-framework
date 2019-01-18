package com.pawpaw.framework.core.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author liujixin
 * @date 2019-01-18
 * @description
 */
public class PawpawGlobalMessageConverter extends MappingJackson2HttpMessageConverter {

    public PawpawGlobalMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    /**
     * 这里把返回的对象加上公共的{@link RemoteResult}
     */
    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (object instanceof RemoteResult) {
            super.writeInternal(object, type, outputMessage);
        } else {
            RemoteResult rr = new RemoteResult(object);
            super.writeInternal(rr, type, outputMessage);
        }

    }
}
