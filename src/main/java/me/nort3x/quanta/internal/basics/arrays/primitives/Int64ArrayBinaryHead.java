package me.nort3x.quanta.internal.basics.arrays.primitives;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;

public class Int64ArrayBinaryHead implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o,QuantaConfiguration configuration) throws IllegalAccessException {
        f.set(o,ds.readInt64Array());
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o, QuantaConfiguration configuration) throws IllegalAccessException {
        sr.writeInt64Array((long[]) f.get(o));
    }
}
