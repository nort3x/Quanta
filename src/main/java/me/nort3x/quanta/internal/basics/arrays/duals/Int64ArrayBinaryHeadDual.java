package me.nort3x.quanta.internal.basics.arrays.duals;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;

public class Int64ArrayBinaryHeadDual implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {
        long[] arr = ds.readInt64Array();
        if(arr!=null){
            Long[] array = new Long[arr.length];
            for (int i = 0; i < arr.length; i++) {
                array[i] = arr[i];
            }
            f.set(o,array);
        }
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {
        Long[] array = (Long[])f.get(o);
        if(array==null){
            sr.writeInt64Array(null);
            return;
        }
        long[] arr =new long[array.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array[i];
        }
        sr.writeInt64Array(arr);
    }
}
