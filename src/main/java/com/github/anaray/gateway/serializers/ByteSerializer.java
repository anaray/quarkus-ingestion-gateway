package com.github.anaray.gateway.serializers;

import io.quarkus.reactivemessaging.http.runtime.serializers.Serializer;
import io.vertx.core.buffer.Buffer;

public class ByteSerializer implements Serializer<byte[]> {

    @Override
    public boolean handles(Object payload) {
        return payload instanceof byte[];
    }

    @Override
    public Buffer serialize(byte[] payload) {
        return Buffer.buffer(payload);
    }

}
