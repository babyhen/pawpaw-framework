package com.pawpaw.framework.core.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawpaw.framework.core.web.RemoteResult;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * @author liujixin
 * @date 2019-01-19
 * @description
 */
@Slf4j
public class RemoteResultFeignDeocder implements Decoder {
    private final Charset defaultCharSet;
    private final ObjectMapper objectMapper;


    public RemoteResultFeignDeocder(ObjectMapper ObjectMapper, String charset) {
        this.defaultCharSet = Charset.forName(charset);
        this.objectMapper = ObjectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String remoteResp = IOUtils.toString(response.body().asInputStream(), this.defaultCharSet.name());
        TypeReference tr = new TypeReference4Reflect(type);
        Object o = this.objectMapper.readValue(remoteResp, tr);
        if (!(o instanceof RemoteResult)) {
            throw new DecodeException("响应结果不能转换成RemoteResult类型");
        }
        RemoteResult rr = (RemoteResult) o;
        if (!rr.isSucc()) {
            StringBuilder sb = new StringBuilder();
            sb.append("调用远程接口失败");
            sb.append(",code=" + rr.getCode());
            sb.append(",message=" + rr.getMessage());
            throw new IOException(sb.toString());
        }
        return rr.getData();
    }

}


class TypeReference4Reflect<T> extends TypeReference<T> {
    private Type type;

    public TypeReference4Reflect(Type type) {
        this.type = type;

    }


    @Override
    public Type getType() {
        return this.type;
    }

}