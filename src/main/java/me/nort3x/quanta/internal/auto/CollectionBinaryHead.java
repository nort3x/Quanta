package me.nort3x.quanta.internal.auto;

import me.nort3x.quanta.internal.interfaces.BinaryHead;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

// todo unfinished
public class CollectionBinaryHead implements BinaryHead {

    static Field dummyField = DummyClass.getField();

    @Override
    public void readAndSet(Deserializer ds, Field f, Object o) throws IllegalAccessException {
        int size = ds.readInt32();
        if (size == 0)
            return;
        CollectionID collectionID = CollectionID.byteID(ds.readInt32());
        try {
            Collection collection = collectionID.type == Collection.class ? new ArrayList() : (Collection) collectionID.type.newInstance();

            Class<?> type = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
            BinaryHead bh = BinaryHeadStore.getBinaryHeadOf(type);

            DummyClass dm = new DummyClass();
            for (int i = 0; i < size; i++) {
                bh.readAndSet(ds,dummyField,dm);
                collection.add(dm.t);
            }
            f.set(o,collection);

        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    @Override
    @SuppressWarnings("All")
    public void getAndWrite(Serializer sr, Field f, Object o) throws IllegalAccessException {
        Collection c = (Collection) f.get(o);
        if (c == null) {
            sr.writeInt32(0);
            return;
        }

        CollectionID collectionID = CollectionID.byType(c.getClass());

        sr.writeInt32(c.size());
        sr.writeInt32(collectionID.id);

        Class<?> type = (Class<?>) ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
        BinaryHead bh = BinaryHeadStore.getBinaryHeadOf(type);

        DummyClass dm = new DummyClass();
        for (Object o1 : c) {
            dm.t = o1;
            bh.getAndWrite(sr, dummyField, dm);
        }
    }


    private enum CollectionID {
        ARRAYLIST(1, ArrayList.class),
        LINKED_LIST(2, LinkedList.class),
        VECTOR(3, Vector.class),
        STACK(4, Stack.class),
        PRIORITY_QUEUE(5, PriorityQueue.class),
        ARRAY_DEQUEUE(6, ArrayDeque.class),
        HASHSET(7, HashSet.class),
        LINKED_HASHSET(8, LinkedHashSet.class),
        TREESET(9, TreeSet.class),
        GENERICCOLLECTION(10, null);
        int id;
        Class<?> type;

        CollectionID(int id, Class<?> type) {
            this.id = id;
            this.type = type;
        }

        static CollectionID byType(Class<?> type) {
            return Arrays.stream(values()).sequential().filter(x -> x.type.equals(type)).findFirst().orElse(GENERICCOLLECTION);
        }

        static CollectionID byteID(int i) {
            return Arrays.stream(values()).sequential().filter(x -> x.id == i).findFirst().orElse(GENERICCOLLECTION);
        }
    }

}
