package com.pawpaw.framework.core.feign;

import com.pawpaw.framework.core.codec.ResultDeocder;
import com.pawpaw.framework.core.common.util.PawpawFrameworkUtil;
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
    private final ResultDeocder resultDeocder;


    public RemoteResultFeignDeocder(ObjectFactory<HttpMessageConverters> messageConverters, ResultDeocder resultDeocder, String charset) {
        super(messageConverters);
        this.resultDeocder = resultDeocder;
        this.defaultCharSet = Charset.forName(charset);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        // 如果不是pawpaw范围内的类，则使用默认的解码器
        if (!PawpawFrameworkUtil.isPawpawPackageClass(type)) {
            return super.decode(response, type);
        }
        // 如果是pawpaw包的话，就使用约定好的格式解码
        String remoteResp = IOUtils.toString(response.body().asInputStream(), this.defaultCharSet.name());
        String dataStr = this.resultDeocder.decode(remoteResp);
        //构造脱掉外壳的数据传递给父类处理
        Response.Body body = new RemoteResultInnerBody(dataStr, this.defaultCharSet);
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