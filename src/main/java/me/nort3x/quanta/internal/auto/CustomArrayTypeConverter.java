package me.nort3x.quanta.internal.auto;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class CustomArrayTypeConverter implements BinaryHead {

    Field dummyField;
    {
        try {
            dummyField = DummyClass.class.getField("t");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    BinaryHead bh;
    Class<?> type;
    public CustomArrayTypeConverter(BinaryHead bh, Class<?> elemenet_type) {
        this.bh = bh;
        this.type =elemenet_type;
    }


    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {
        int numberOfObjects = ds.readInt32();
        if (numberOfObjects == 0)
            return;

        Object[] arr = (Object[]) Array.newInstance(type, numberOfObjects);
        DummyClass<Object> dummyClass = new DummyClass<>();
        for (int i = 0; i < numberOfObjects; i++) {
            bh.readAndSet(ds,dummyField,dummyClass);
            arr[i] = dummyClass.t;
        }
        f.set(o,arr);
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {
        Object[] arr = (Object[]) f.get(o);
        if(arr==null) {
            sr.writeInt32(0);
            return;
        }
        sr.writeInt32(arr.length);
        DummyClass<Object> dummyClass = new DummyClass<>();
        for (Object value : arr) {
            dummyClass.t = value;
            bh.getAndWrite(sr, dummyField, dummyClass);
        }
    }
}
