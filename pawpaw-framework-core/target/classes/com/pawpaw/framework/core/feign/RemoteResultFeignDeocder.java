package com.pawpaw.framework.core.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawpaw.framework.core.web.RemoteResult;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * @author liujixin
 * @date 2019-01-19
 * @description
 */
@Slf4j
public class RemoteResultFeignDeocder extends SpringDecoder {
    private final Charset defaultCharSet;
    private final ObjectMapper objectMapper;


    public RemoteResultFeignDeocder(ObjectFactory<HttpMessageConverters> messageConverters, ObjectMapper ObjectMapper, String charset) {
        super(messageConverters);
        this.defaultCharSet = Charset.forName(charset);
        this.objectMapper = ObjectMapper;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String remoteResp = IOUtils.toString(response.body().asInputStream(), this.defaultCharSet.name());
        RemoteResult<String> o = this.objectMapper.readValue(remoteResp, new TypeReference<RemoteResult<String>>() {
        });
        if (o == null) {
            throw new DecodeException("响应结果不能转换成RemoteResult类型");
        }
        if (!o.isSucc()) {
            StringBuilder sb = new StringBuilder();
            sb.append("调用远程接口失败");
            sb.append(",code=" + o.getCode());
            sb.append(",message=" + o.getMessage());
            throw new IOException(sb.toString());
        }
        Response.Body body = new RemoteResultInnerBody(o.getData(),this.defaultCharSet);
        //构造脱掉外壳的数据传递给父类处理
        Response.Builder builder = Response.builder();
        builder.status(response.status());
        builder.reason(response.reason());
        builder.headers(response.headers());
        builder.body(body);
        //
        return super.decode(builder.build(), type);
    }

}


class RemoteResultInnerBody implements Response.Body {
    byte[] data;
    InputStream stream;

    RemoteResultInnerBody(String dataSource, Charset charset) {
        if (dataSource == null) {
            this.data = new byte[0];
        } else {
            this.data = dataSource.getBytes(charset);
        }
        this.stream = new ByteArrayInputStream(this.data);
    }

    @Override
    public Integer length() {
        return this.data.length;
    }

    @Override
    public boolean isRepeatable() {
        return false;
    }

    @Override
    public InputStream asInputStream() throws IOException {
        return this.stream;
    }

    @Override
    public Reader asReader() throws IOException {
        return new InputStreamReader(this.stream);
    }

    @Override
    public void close() throws IOException {
        this.stream.close();

    }
}