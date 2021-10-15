package me.nort3x.quanta.internal.objects;

import me.nort3x.quanta.internal.auto.BinaryHeadStore;
import me.nort3x.quanta.internal.auto.Scanner;
import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomTypeConverter implements BinaryHead {

    private final Class<?> type;
    private final List<Field> fieldList;

    private final Map<Field, BinaryHead> heads;

    public CustomTypeConverter(Class<?> type,QuantaConfiguration configuration) {
        this.type = type;
        heads = new HashMap<>();
        fieldList = Scanner.getFields(type,configuration);
        for (Field field : fieldList) {
            heads.put(field, BinaryHeadStore.getBinaryHeadOf(field.getType(),configuration));
        }

    }

    protected Class<?> getType(){
        return type;
    }

    @Override
    public void readAndSet(Deserializer ds, Field f, Object o,QuantaConfiguration configuration) throws IllegalAccessException {
        try {
            Object fieldObject = type.getConstructor().newInstance();
            for (Field field : fieldList) {
                heads.get(field).readAndSet(ds,field,fieldObject,configuration);
            }
            f.set(o,fieldObject);
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o,QuantaConfiguration configuration) throws IllegalAccessException {
        Object fieldObject = f.get(o);
        for (Field field : fieldList) {
            heads.get(field).getAndWrite(sr,field,fieldObject,configuration);
        }
    }
}
