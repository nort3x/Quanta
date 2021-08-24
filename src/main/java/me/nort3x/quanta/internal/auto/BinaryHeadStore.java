package me.nort3x.quanta.internal.auto;

import me.nort3x.quanta.internal.basics.arrays.duals.*;
import me.nort3x.quanta.internal.basics.arrays.objects.StringArrayBinaryHead;
import me.nort3x.quanta.internal.basics.arrays.objects.StringBinaryHead;
import me.nort3x.quanta.internal.basics.arrays.primitives.*;
import me.nort3x.quanta.internal.basics.primitives.*;
import me.nort3x.quanta.internal.interfaces.BinaryHead;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BinaryHeadStore {
    static final private Map<Class<?>, BinaryHead> primitiveHeads = new HashMap<>() {{

        // primitives
        Int32BinaryHead i32h = new Int32BinaryHead();
        put(int.class, i32h);
        put(Integer.class, i32h);
        Int64BinaryHead i64h = new Int64BinaryHead();
        put(long.class, i64h);
        put(Long.class, i64h);

        Float32BinaryHead f32h = new Float32BinaryHead();
        put(float.class, f32h);
        put(Float.class, f32h);
        Float64BinaryHead f64h = new Float64BinaryHead();
        put(double.class, f64h);
        put(Double.class, f64h);

        BoolBinaryHead bh = new BoolBinaryHead();
        put(boolean.class, bh);
        put(Boolean.class, bh);

        ByteBinaryHead byh = new ByteBinaryHead();
        put(byte.class, byh);
        put(Byte.class, byh);


        // array heads
        put(String.class, new StringBinaryHead());
        put(String[].class, new StringArrayBinaryHead());

        put(byte[].class, new ByteArrayBinaryHead());
        put(Byte[].class, new ByteArrayBinaryHeadDual());

        put(boolean[].class, new BoolArrayBinaryHead());
        put(Boolean[].class, new BooleanArrayBinaryHead()); //todo for the everything else

        put(float[].class, new Float32ArrayBinaryHead());
        put(Float[].class, new Float32ArrayBinaryHeadDual());

        put(double[].class, new Float64ArrayBinaryHead());
        put(Double[].class, new Float64ArrayBinaryHeadDual());

        put(int[].class, new Int32ArrayBinaryHead());
        put(Integer[].class, new Int32ArrayBinaryHeadDual());

        put(long[].class, new Int64ArrayBinaryHead());
        put(Long[].class, new Int64ArrayBinaryHeadDual());

        //special types
        CollectionBinaryHead ch = new CollectionBinaryHead();
        put(Collection.class,ch);put(Set.class,ch);put(List.class,ch);
        put(PriorityQueue.class,ch);put(HashSet.class,ch);
        put(LinkedHashSet.class,ch);put(SortedSet.class,ch);
        put(TreeSet.class,ch);put(Deque.class,ch);put(ArrayDeque.class,ch);
        put(ArrayList.class,ch);put(LinkedList.class,ch);
        put(Vector.class,ch);put(Stack.class,ch);

    }};


    static final Map<Class<?>, BinaryHead> customTypesBinaryHeads = new ConcurrentHashMap<>();
    static final Map<Class<?>, BinaryHead> customArrayTypesBinaryHeads = new ConcurrentHashMap<>();

    public static BinaryHead getBinaryHeadOf(Class<?> type) {
        if (primitiveHeads.containsKey(type))
            return primitiveHeads.get(type);
        else if (type.isArray()) {
            if(!customArrayTypesBinaryHeads.containsKey(type)){ // todo: figure out this retardness, caused by Recursive map update
                Class<?> elementType = type.getComponentType();
                customArrayTypesBinaryHeads.put(type,new CustomArrayTypeConverter(getBinaryHeadOf(elementType), elementType));
            }
            return customArrayTypesBinaryHeads.get(type);
        } else {
            if(!customTypesBinaryHeads.containsKey(type)) // todo: figure out this retardness, caused by Recursive map update
                customTypesBinaryHeads.put(type,new CustomTypeConverter(type));
            return customTypesBinaryHeads.get(type);
        }
    }


}
