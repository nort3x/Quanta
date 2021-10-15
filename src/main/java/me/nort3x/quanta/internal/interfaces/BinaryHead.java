package me.nort3x.quanta.internal.interfaces;

import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;


/**
 * BinaryHead for specific type can be used to read and write to Serializer and Deserializer
 * it behaves like a Three-Consumer, give it bus(Serializer or Deserializer), instance of object, and a field of that object
 * which field type correspond to this binary head, then binary head will read from bus fill the field or read the field and write to bus
 */
public interface BinaryHead {
    /**
     * will read Deserialization bus according to its specific type
     * and set the field with read data on given instance
     * @param ds Deserialization bus to read from
     * @param f field that should be filled by this operation
     * @param o instance of object that should be field filled on
     * @throws IllegalAccessException
     */
    void readAndSet(Deserializer ds, Field f, Object o, QuantaConfiguration quantaConfiguration) throws IllegalAccessException;

    /**
     * @param sr
     * @param f
     * @param o
     * @throws IllegalAccessException
     * @see BinaryHead#readAndSet(Deserializer, Field, Object,QuantaConfiguration)
     */
    void getAndWrite(Serializer sr,Field f,Object o,QuantaConfiguration quantaConfiguration) throws IllegalAccessException;
}
