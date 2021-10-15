package me.nort3x.quanta.internal.basics.arrays.objects;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;

public class StringArrayBinaryHead implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o, QuantaConfiguration configuration) throws IllegalAccessException {
        f.set(o,ds.readStringArray());
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o,QuantaConfiguration configuration) throws IllegalAccessException {
        sr.writeStringArray((String[])(f.get(o)));
    }
}
