package com.pawpaw.framework.core.web.convertor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawpaw.framework.core.web.RemoteResult;
import com.pawpaw.framework.core.web.interceptor.ThreadHandlerMapInterceptor;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
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

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        boolean superCanWrite = super.canWrite(clazz, mediaType);
        if (!superCanWrite) {
            return false;
        }
        //这里只需要编码那些pawpaw包里面的controller的响应，不解析其他框架的响应。避免对那些响应造成影响
        Object handler = ThreadHandlerMapInterceptor.ThreadHandlerMappingHolder.get();
        if (handler == null) {
            return false;
        }
        String cName = handler.getClass().getName();
        if (cName.startsWith("com.pawpaw")) {
            return true;
        }
        return false;
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
