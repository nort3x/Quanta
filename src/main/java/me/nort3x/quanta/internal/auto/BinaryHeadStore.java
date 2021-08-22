package me.nort3x.quanta.internal.auto;

import me.nort3x.quanta.internal.basics.arrays.*;
import me.nort3x.quanta.internal.basics.arrays.objects.BooleanArrayBinaryHead;
import me.nort3x.quanta.internal.basics.primitives.*;
import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BinaryHeadStore {
    static final private Map<Class<?>, BinaryHead> primitiveHeads = new HashMap<>(){{

        // primitives
        Int32BinaryHead i32h = new Int32BinaryHead();
        put(int.class,i32h);put(Integer.class,i32h);
        Int64BinaryHead i64h = new Int64BinaryHead();
        put(long.class,i64h);put(Long.class,i64h);

        Float32BinaryHead f32h = new Float32BinaryHead();
        put(float.class,f32h);put(Float.class,f32h);
        Float64BinaryHead f64h = new Float64BinaryHead();
        put(double.class,f64h);put(Double.class,f64h);

        BoolBinaryHead bh = new BoolBinaryHead();
        put(boolean.class,bh);put(Boolean.class,bh);

        ByteBinaryHead byh = new ByteBinaryHead();
        put(byte.class,byh);put(Byte.class,byh);



        // array heads
        put(String.class,new StringBinaryHead());

        put(byte[].class,new ByteArrayBinaryHead());

        put(boolean[].class,new BoolArrayBinaryHead());
        put(Boolean[].class,new BooleanArrayBinaryHead()); //todo for the everything else

        put(float[].class, new Float32ArrayBinaryHead());

        put(double[].class,new Float64ArrayBinaryHead());

        put(int[].class,new Int32ArrayBinaryHead());

        put(long[].class,new Int64ArrayBinaryHead());

    }};


    static Map<Class<?>,BinaryHead> customTypesBinaryHeads = new ConcurrentHashMap<>();

    public static BinaryHead getBinaryHeadOf(Class<?> type){
        if(primitiveHeads.containsKey(type))
            return primitiveHeads.get(type);
        else
            return customTypesBinaryHeads.computeIfAbsent(type, CustomTypeConverter::new);
    }


}
