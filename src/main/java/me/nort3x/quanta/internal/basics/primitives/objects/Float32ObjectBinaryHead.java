package me.nort3x.quanta.internal.basics.primitives.objects;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;

public class Float32ObjectBinaryHead implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o,QuantaConfiguration configuration) throws IllegalAccessException {
        f.set(o,Float.valueOf(ds.readFloat32()));
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o, QuantaConfiguration configuration) throws IllegalAccessException {
        sr.writeFloat32((Float) f.get(o));
    }
}
