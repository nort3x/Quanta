package me.nort3x.quanta.internal.basics.primitives;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;

public class ByteBinaryHead implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o, QuantaConfiguration configuration) throws IllegalAccessException {
        f.set(o,ds.readByte());
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o,QuantaConfiguration configuration) throws IllegalAccessException {
        sr.writeByte(f.getByte(o));
    }
}
