package me.nort3x.quanta.internal.basics.arrays.duals;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;

public class Int32ArrayBinaryHeadDual implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {
        int[] arr = ds.readInt32Array();
        if(arr!=null){
            Integer[] array = new Integer[arr.length];
            for (int i = 0; i < arr.length; i++) {
                array[i] = arr[i];
            }
            f.set(o,array);
        }
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {
        Integer[] array = (Integer[])f.get(o);
        if(array==null){
            sr.writeInt32Array(null);
            return;
        }
        int[] arr =new int[array.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array[i];
        }
        sr.writeInt32Array(arr);
    }
}
