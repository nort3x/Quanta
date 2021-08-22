package me.nort3x.quanta.internal.interfaces;

import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;

public interface BinaryHead {
    void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException;
    void getAndWrite(Serializer sr,Field f,Object o) throws IllegalAccessException;
}
