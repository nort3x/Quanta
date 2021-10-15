package me.nort3x.quanta.pub.auto;

import me.nort3x.quanta.internal.auto.BinaryHeadStore;
import me.nort3x.quanta.internal.auto.DummyClass;
import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.config.QuantaConfiguration;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.interfaces.BinaryConverter;

import java.lang.reflect.Field;

public class NestedConvertor<T> implements BinaryConverter<T> {
    BinaryHead typeBH;
    static Field dummyClassField;

    static {
        try {
            dummyClassField = DummyClass.class.getField("t");
        } catch (NoSuchFieldException e) {
            e.printStackTrace(); // should never happen!
        }
    }

    private final QuantaConfiguration config;
    public NestedConvertor(Class<T> type, QuantaConfiguration quantaConfiguration) {
        typeBH = BinaryHeadStore.getBinaryHeadOf(type,quantaConfiguration);
        config = quantaConfiguration;
    }

    public NestedConvertor(Class<T> type) {
        this(type, QuantaConfiguration.getDefault());
    }

    @Override
    public T deserialize(byte[] arr) {
        DummyClass<T> instance = new DummyClass<>();
        try {
            typeBH.readAndSet(new Deserializer(arr,config),dummyClassField,instance,config);
            return instance.t;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public byte[] serialize(T obj) {
        DummyClass<T> instance = new DummyClass<>();
        instance.t = obj;
        try {
            Serializer sr = new Serializer();
            typeBH.getAndWrite(sr,dummyClassField, instance,config);
            return sr.toArray();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
