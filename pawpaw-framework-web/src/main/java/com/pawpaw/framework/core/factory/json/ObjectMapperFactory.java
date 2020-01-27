package com.pawpaw.framework.core.factory.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.pawpaw.framework.core.codec.RemoteResultCode;

import java.io.IOException;

/**
 * {@link PawpawObjectMapper}的工厂类
 *
 * @author liujixin
 * @date 2019-01-20
 * @description
 */
public class ObjectMapperFactory {


    public static PawpawObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        //配置om
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //对IEnumType的支持
        SimpleModule module = new SimpleModule();
        module.addSerializer(RemoteResultCode.class, new RemoteResultCodeJsonSerializer());
        module.addDeserializer(RemoteResultCode.class, new RemoteResultCodeJsonDeserializer());
        om.registerModule(module);
        return new PawpawObjectMapper(om);
    }
}


/**
 * @author liujixin
 * @date 2019-01-19
 * @description
 */

class RemoteResultCodeJsonSerializer extends StdScalarSerializer<RemoteResultCode> {

    public RemoteResultCodeJsonSerializer() {
        super(RemoteResultCode.class);
    }

    @Override
    public void serialize(RemoteResultCode value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonGenerationException {
        gen.writeNumber(value.value());
    }


}

class RemoteResultCodeJsonDeserializer extends StdScalarDeserializer<RemoteResultCode> {

    public RemoteResultCodeJsonDeserializer() {
        super(RemoteResultCode.class);
    }

    @Override
    public RemoteResultCode deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        int code = p.getIntValue();
        return RemoteResultCode.fromValue(code);
    }


}
