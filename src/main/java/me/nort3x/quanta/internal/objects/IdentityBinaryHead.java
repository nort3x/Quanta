package me.nort3x.quanta.internal.objects;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;

public class IdentityBinaryHead implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {

    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {

    }
}
