package me.nort3x.quanta.internal.basics.arrays.duals;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;

public class Float64ArrayBinaryHeadDual implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {
        double[] arr = ds.readFloat64Array();
        if(arr!=null){
            Double[] array = new Double[arr.length];
            for (int i = 0; i < arr.length; i++) {
                array[i] = arr[i];
            }
            f.set(o,array);
        }
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {
        Double[] array = (Double[])f.get(o);
        if(array==null){
            sr.writeFloat64Array(null);
            return;
        }
        double[] arr =new double[array.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array[i];
        }
        sr.writeFloat64Array(arr);
    }
}
