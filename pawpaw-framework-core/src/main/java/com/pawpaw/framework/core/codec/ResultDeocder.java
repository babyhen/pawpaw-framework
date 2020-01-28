package com.pawpaw.framework.core.codec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pawpaw.framework.core.factory.json.PawpawObjectMapper;

import java.io.IOException;

/**
 * 把最外层的{@link RemoteResult}  包给解出来
 *
 * @author liujixin
 * @date 2019-02-12
 */
public class ResultDeocder {
    private final PawpawObjectMapper objectMapper;

    public ResultDeocder(PawpawObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String decode(String rawData) throws IOException {
        RemoteResult<Object> o = this.objectMapper.readValue(rawData, new TypeReference<RemoteResult<Object>>() {
        });
        if (o == null) {
            throw new DecodeErrorException("响应结果不能转换成RemoteResult类型");
        }
        if (!o.isSucc()) {
            StringBuilder sb = new StringBuilder();
            sb.append("调用远程接口失败");
            sb.append(",code=" + o.getCode());
            sb.append(",message=" + o.getMessage());
            throw new IOException(sb.toString());
        }
        Object data = o.getData();
        String dataStr = this.objectMapper.writeValueAsString(data);
        return dataStr;

    }
}
