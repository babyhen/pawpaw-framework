package com.pawpaw.framework.core.factory.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.pawpaw.framework.core.common.IEnumType;

import java.io.IOException;

/**
 * @author liujixin
 * @date 2019-01-19
 * @description
 */

class IEnumTypeJsonSerializer extends StdScalarSerializer<IEnumType> {

    public IEnumTypeJsonSerializer() {
        super(IEnumType.class);
    }

    @Override
    public void serialize(IEnumType value, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonGenerationException {
        gen.writeNumber(value.value());
    }


}