package me.nort3x.quanta.internal.auto;

import me.nort3x.quanta.internal.basics.arrays.duals.*;
import me.nort3x.quanta.internal.basics.arrays.objects.StringArrayBinaryHead;
import me.nort3x.quanta.internal.basics.arrays.objects.StringBinaryHead;
import me.nort3x.quanta.internal.basics.arrays.primitives.*;
import me.nort3x.quanta.internal.basics.primitives.*;
import me.nort3x.quanta.internal.basics.primitives.objects.*;
import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.internal.objects.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BinaryHeadStore {
    static final private Map<Class<?>, BinaryHead> primitiveHeads = new HashMap<>() {{

        // primitives
        put(int.class, new Int32BinaryHead());
        put(Integer.class, new Int32ObjectBinaryHead());

        put(long.class, new Int64BinaryHead());
        put(Long.class, new Int64ObjectBinaryHead());

        put(float.class, new Float32BinaryHead());
        put(Float.class, new Float32ObjectBinaryHead());

        put(double.class, new Float64BinaryHead());
        put(Double.class, new Float64ObjectBinaryHead());

        put(boolean.class, new BoolBinaryHead());
        put(Boolean.class, new BooleanObjectBinaryHead());

        put(byte.class, new ByteBinaryHead());
        put(Byte.class, new ByteObjectBinaryHead());


        // arrays
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
        put(Object.class,new IdentityBinaryHead());

        // collections
        CollectionBinaryHead ch = new CollectionBinaryHead();
        put(Collection.class,ch);put(Set.class,ch);put(List.class,ch);
        put(PriorityQueue.class,ch);put(HashSet.class,ch);
        put(LinkedHashSet.class,ch);put(SortedSet.class,ch);
        put(TreeSet.class,ch);put(Deque.class,ch);put(ArrayDeque.class,ch);
        put(ArrayList.class,ch);put(LinkedList.class,ch);
        put(Vector.class,ch);put(Stack.class,ch);

        // maps
        MapBinaryHead mb = new MapBinaryHead();
        put(Map.class,mb);
        put(AbstractMap.class,mb);
        put(WeakHashMap.class,mb);
        put(HashMap.class,mb);
        put(Hashtable.class,mb);
        put(LinkedHashMap.class,mb);
        put(TreeMap.class,mb);
        put(SortedMap.class,mb);
        put(NavigableMap.class,mb);
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
