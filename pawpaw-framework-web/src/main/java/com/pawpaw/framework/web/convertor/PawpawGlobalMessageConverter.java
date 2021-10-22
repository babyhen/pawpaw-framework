package com.pawpaw.framework.web.convertor;

import com.pawpaw.framework.core.codec.RemoteResult;
import com.pawpaw.framework.core.codec.ResultEncoder;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;
import com.pawpaw.framework.web.interceptor.ThreadHandlerMapInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author liujixin
 * @date 2019-01-18
 * @description
 */
public class PawpawGlobalMessageConverter extends MappingJackson2HttpMessageConverter {

    private final ResultEncoder resultEncoder;

    public PawpawGlobalMessageConverter(PawpawObjectMapper objectMapper, ResultEncoder resultEncoder) {
        super(objectMapper.getRawObjectMapper());
        this.resultEncoder = resultEncoder;
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
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Object controller = hm.getBean();
            if (controller == null) {
                return false;
            }
            String cName = controller.getClass().getName();
            if (isPawpawPackageClass(cName)) {
                return true;
            }
        }

        return false;
    }


    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        RemoteResult<? extends Object> encoded = this.resultEncoder.encode(object);
        super.writeInternal(encoded, type, outputMessage);
    }


    private boolean isPawpawPackageClass(String className) {
        return StringUtils.startsWith(className, "com.pawpaw");
    }



}
