package me.nort3x.quanta.internal.auto;

import me.nort3x.quanta.internal.basics.arrays.duals.*;
import me.nort3x.quanta.internal.basics.arrays.objects.StringArrayBinaryHead;
import me.nort3x.quanta.internal.basics.arrays.objects.StringBinaryHead;
import me.nort3x.quanta.internal.basics.arrays.primitives.*;
import me.nort3x.quanta.internal.basics.primitives.*;
import me.nort3x.quanta.internal.interfaces.BinaryHead;

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
        put(String[].class,new StringArrayBinaryHead());

        put(byte[].class,new ByteArrayBinaryHead());
        put(Byte[].class,new ByteArrayBinaryHeadDual());

        put(boolean[].class,new BoolArrayBinaryHead());
        put(Boolean[].class,new BooleanArrayBinaryHead()); //todo for the everything else

        put(float[].class, new Float32ArrayBinaryHead());
        put(Float[].class,new Float32ArrayBinaryHeadDual());

        put(double[].class,new Float64ArrayBinaryHead());
        put(Double[].class,new Float64ArrayBinaryHeadDual());

        put(int[].class,new Int32ArrayBinaryHead());
        put(Integer[].class,new Int32ArrayBinaryHeadDual());

        put(long[].class,new Int64ArrayBinaryHead());
        put(Long[].class,new Int64ArrayBinaryHeadDual());

    }};


    static Map<Class<?>,BinaryHead> customTypesBinaryHeads = new ConcurrentHashMap<>();
    static Map<Class<?>,BinaryHead> customArrayTypesBinaryHeads = new ConcurrentHashMap<>();

    public static BinaryHead getBinaryHeadOf(Class<?> type){
        if(primitiveHeads.containsKey(type))
            return primitiveHeads.get(type);
        else if(type.isArray()){
            return customArrayTypesBinaryHeads.computeIfAbsent(type, givenArrayType->{
               Class<?> elemenet_type =  type.getComponentType();
               return new CustomArrayTypeConverter(getBinaryHeadOf(elemenet_type),elemenet_type);
            });
        }
        else
            return customTypesBinaryHeads.computeIfAbsent(type, CustomTypeConverter::new);
    }


}
