package me.nort3x.quanta.internal.basics.arrays.duals;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;

public class Float32ArrayBinaryHeadDual implements BinaryHead {
    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {
        float[] arr = ds.readFloat32Array();
        if(arr!=null){
            Float[] array = new Float[arr.length];
            for (int i = 0; i < arr.length; i++) {
                array[i] = arr[i];
            }
            f.set(o,array);
        }
    }

    @Override
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {
        Float[] array = (Float[])f.get(o);
        if(array==null){
            sr.writeFloat32Array(null);
            return;
        }
        float[] arr =new float[array.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array[i];
        }
        sr.writeFloat32Array(arr);
    }
}
