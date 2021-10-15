package me.nort3x.quanta.internal.basics.arrays.duals;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;

public class BooleanArrayBinaryHead implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o, QuantaConfiguration configuration) throws IllegalAccessException {
        boolean[] arr = ds.readBoolArray();
        if(arr==null)
            return;
        Boolean[] array = new Boolean[arr.length];
        for (int i = 0; i < arr.length; i++)
            array[i] = arr[i];
        f.set(o,array);
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o,QuantaConfiguration configuration) throws IllegalAccessException {
        Boolean[] array = (Boolean[]) f.get(o);
        if(array==null){
            sr.writeBoolArray(null);
            return;
        }
        boolean[] arr = new boolean[array.length];
        for (int i = 0; i < arr.length; i++)
            arr[i] = array[i];
        sr.writeBoolArray(arr);
    }
}
