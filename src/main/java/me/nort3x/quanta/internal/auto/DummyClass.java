package me.nort3x.quanta.internal.auto;

import java.lang.reflect.Field;

public class DummyClass<T> {
    public T t;

    private static Field f;
    static {
        try {
            f = DummyClass.class.getField("t");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    public static Field getField(){
        return f;
    }
}
