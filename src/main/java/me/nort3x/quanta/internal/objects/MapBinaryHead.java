package me.nort3x.quanta.internal.objects;

import me.nort3x.quanta.internal.auto.BinaryHeadStore;
import me.nort3x.quanta.internal.auto.DummyClass;
import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Set;

public class MapBinaryHead implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {

        Class<?> keyType = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
        Class<?> valueType = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[1];

        try {
            Map map = (Map) f.getType().newInstance();

            int size = ds.readInt32();

            BinaryHead keyHead = BinaryHeadStore.getBinaryHeadOf(keyType);
            BinaryHead valueHead = BinaryHeadStore.getBinaryHeadOf(valueType);
            DummyClass objectKey = new DummyClass();
            DummyClass objectValue = new DummyClass();

            for (int i = 0; i < size; i++) {
                keyHead.readAndSet(ds,DummyClass.getField(),objectKey);
                valueHead.readAndSet(ds,DummyClass.getField(),objectValue);
                map.put(objectKey.t,objectValue.t);
            }
            f.set(o,map);

        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {

        Class<?> keyType = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
        Class<?> valueType = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[1];

        Map map = (Map) f.get(o);
        Set<Map.Entry> entries = map.entrySet();

        sr.writeInt32(entries.size());

        BinaryHead keyHead = BinaryHeadStore.getBinaryHeadOf(keyType);
        BinaryHead valueHead = BinaryHeadStore.getBinaryHeadOf(valueType);

        DummyClass objectKey = new DummyClass();
        DummyClass objectValue = new DummyClass();
        for (Map.Entry entry : entries) {
            objectKey.t = entry.getKey();
            keyHead.getAndWrite(sr,DummyClass.getField(),objectKey);
            objectValue.t = entry.getValue();
            valueHead.getAndWrite(sr,DummyClass.getField(),objectValue);
        }

    }
}
