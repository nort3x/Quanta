package me.nort3x.quanta.internal.objects;

import me.nort3x.quanta.internal.auto.BinaryHeadStore;
import me.nort3x.quanta.internal.auto.DummyClass;
import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

// todo unfinished
public class CollectionBinaryHead implements BinaryHead {

    static Field dummyField = DummyClass.getField();

    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {
        int size = ds.readInt32();
        if (size == 0)
            return;
        try {
            Collection collection = (Collection) f.getType().newInstance();

            Class<?> type = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
            BinaryHead bh = BinaryHeadStore.getBinaryHeadOf(type);

            DummyClass dm = new DummyClass();
            for (int i = 0; i < size; i++) {
                bh.readAndSet(ds,dummyField,dm);
                collection.add(dm.t);
            }
            f.set(o,collection);

        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    @Override
    @SuppressWarnings("All")
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {
        Collection c = (Collection) f.get(o);
        if (c == null) {
            sr.writeInt32(0);
            return;
        }


        sr.writeInt32(c.size());

        Class<?> type = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
        BinaryHead bh = BinaryHeadStore.getBinaryHeadOf(type);

        DummyClass dm = new DummyClass();
        for (Object o1 : c) {
            dm.t = o1;
            bh.getAndWrite(sr, dummyField, dm);
        }
    }

}
