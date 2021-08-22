package me.nort3x.quanta.pub.auto;

import me.nort3x.quanta.pub.auto.NestedSerializer;

import java.util.concurrent.ConcurrentHashMap;

public class NestedSerializerStore {
    private static final ConcurrentHashMap<Class, NestedSerializer> alreadyKnownSchemas = new ConcurrentHashMap<>();
    public static  NestedSerializer getTypeConvertorOf(Class<?> type){
        return  alreadyKnownSchemas.computeIfAbsent(type, NestedSerializer::new);
    }

    public static void dropCache() {
        alreadyKnownSchemas.clear();
    }
}
