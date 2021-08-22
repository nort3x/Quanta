package me.nort3x.quanta.pub.auto;

import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;

import static me.nort3x.quanta.internal.auto.Scanner.getFields;

public class NestedSerializer<T> {


    private final static Map<Class<?>, ThreeConsumer<Serializer>> defaultSerializerMap = new HashMap<>();
    private final static Map<Class<?>, ThreeConsumer<Deserializer>> defaultDeSerializerMap = new HashMap<>();

    private final Map<Field, BiConsumer<Serializer, Object>> serializationMap = new HashMap<>();
    private final Map<Field, BiConsumer<Deserializer, Object>> deSerializationMap = new HashMap<>();

    private final List<Field> fieldOfTypes;
    private final Class<T> type;

    protected NestedSerializer(Class<T> type) {

        this.type = type;
        fieldOfTypes = getFields(type); // fields of this type as a list

        for (Field fieldOfType : fieldOfTypes) {

            BiConsumer<Serializer, Object> serializerOfField = Optional.ofNullable(defaultSerializerMap.getOrDefault(fieldOfType.getType(), null))
                    .map(serializerThreeConsumer -> (BiConsumer<Serializer, Object>) (serializer, o) -> {
                        try {
                            serializerThreeConsumer.apply(fieldOfType, o, serializer);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    })
                    .orElse(new BiConsumer<Serializer, Object>() {
                        final NestedSerializer converter = NestedSerializerStore.getTypeConvertorOf(fieldOfType.getType());

                        @Override
                        public void accept(Serializer serializer, Object o) {
                            try {
                                serializer.writeByteArray(converter.serialize(fieldOfType.get(o)));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            serializationMap.put(fieldOfType, serializerOfField);

//
//            Optional.ofNullable(defaultSerializerMap.getOrDefault(fieldOfType.getType(), null))
//                    .ifPresentOrElse(serializerThreeConsumer -> serializationMap.put(fieldOfType, (ser, obj) -> {  // if its primitive
//                                try {
//                                    serializerThreeConsumer.apply(fieldOfType, obj, ser);
//                                } catch (IllegalAccessException e) {
//                                    e.printStackTrace();
//                                }
//                            }),
//                            () -> { // if its not!
//                                serializationMap.put(fieldOfType, (serializer, o) -> {
//                                    try {
//                                        serializer.writeByteArray(NestedSerializerStore.getTypeConvertorOf(fieldOfType.getType()).serialize(fieldOfType.get(o)));
//                                    } catch (IllegalAccessException e) {
//                                        e.printStackTrace();
//                                    }
//                                });
//                            }
//                    );


            BiConsumer<Deserializer, Object> deSerializerOfField = Optional.ofNullable(defaultDeSerializerMap.getOrDefault(fieldOfType.getType(), null))
                    .map(serializerThreeConsumer -> (BiConsumer<Deserializer, Object>) (deserializer, o) -> {
                        try {
                            serializerThreeConsumer.apply(fieldOfType, o, deserializer);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    })
                    .orElse(new BiConsumer<Deserializer, Object>() {
                        final NestedSerializer converter = NestedSerializerStore.getTypeConvertorOf(fieldOfType.getType());

                        @Override
                        public void accept(Deserializer deserializer, Object o) {
                            try {
                                fieldOfType.set(o, NestedSerializerStore.getTypeConvertorOf(fieldOfType.getType()).deserialize(deserializer.readByteArray()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            deSerializationMap.put(fieldOfType,deSerializerOfField);

//            Optional.ofNullable(defaultDeSerializerMap.getOrDefault(fieldOfType.getType(), null))
//                    .ifPresentOrElse(deserializerThreeConsumer -> deSerializationMap.put(fieldOfType, (des, obj) -> {  // if its primitive
//                                try {
//                                    deserializerThreeConsumer.apply(fieldOfType, obj, des);
//                                } catch (IllegalAccessException e) {
//                                    e.printStackTrace();
//                                }
//                            }),
//                            () -> { // if its not!
//                                deSerializationMap.put(fieldOfType, new BiConsumer<Deserializer, Object>() {
//                                    @Override
//                                    public void accept(Deserializer deserializer, Object o) {
//                                        try {
//                                            fieldOfType.set(o, NestedSerializerStore.getTypeConvertorOf(fieldOfType.getType()).deserialize(deserializer.readByteArray()));
//                                        } catch (IllegalAccessException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                });
//                            }
//                    );
//

        }


    }


    public byte[] serialize(T objectInstance) {
        Serializer sr = new Serializer();
        fieldOfTypes.stream().sequential()
                .map(x -> serializationMap.getOrDefault(x, null))
                .filter(Objects::nonNull)
                .forEach(x -> {
                    x.accept(sr, objectInstance);
                });
        return sr.toArray();
    }

    public T deserialize(byte[] arr) {
        try {
            Deserializer dr = new Deserializer(arr);
            Object instance = type.newInstance();
            fieldOfTypes.stream().sequential()
                    .map(x -> deSerializationMap.getOrDefault(x, null))
                    .filter(Objects::nonNull)
                    .forEach(x -> {
                        x.accept(dr, instance);
                    });
            return type.cast(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> NestedSerializer<T> of(Class<T> clazz) {
        return (NestedSerializer<T>) NestedSerializerStore.getTypeConvertorOf(clazz);
    }

    public static void clearAllCaches() {
        NestedSerializerStore.dropCache();
    }

    @FunctionalInterface
    private interface ThreeConsumer<T> {
        void apply(Field f, Object instance, T t) throws IllegalAccessException;
    }

    // primitive type serializer/deserializer are added here
    static {

        // default serializers
        defaultSerializerMap.put(Boolean.class, (field, obj, sr) -> sr.writeBool(field.getBoolean(obj)));
        defaultSerializerMap.put(Integer.class, (field, obj, sr) -> sr.writeInt32(field.getInt(obj)));
        defaultSerializerMap.put(Float.class, (field, obj, sr) -> sr.writeFloat32(field.getFloat(obj)));
        defaultSerializerMap.put(Long.class, (field, obj, sr) -> sr.writeInt64(field.getLong(obj)));
        defaultSerializerMap.put(Double.class, (field, obj, sr) -> sr.writeFloat64(field.getDouble(obj)));
        defaultSerializerMap.put(Byte.class, (field, obj, sr) -> sr.writeByte(field.getByte(obj)));

        // primitives
        defaultSerializerMap.put(boolean.class, (field, obj, sr) -> sr.writeBool(field.getBoolean(obj)));
        defaultSerializerMap.put(int.class, (field, obj, sr) -> sr.writeInt32(field.getInt(obj)));
        defaultSerializerMap.put(float.class, (field, obj, sr) -> sr.writeFloat32(field.getFloat(obj)));
        defaultSerializerMap.put(long.class, (field, obj, sr) -> sr.writeInt64(field.getLong(obj)));
        defaultSerializerMap.put(double.class, (field, obj, sr) -> sr.writeFloat64(field.getDouble(obj)));
        defaultSerializerMap.put(byte.class, (field, obj, sr) -> sr.writeByte(field.getByte(obj)));

        // arrays
        defaultSerializerMap.put(String.class, (field, obj, sr) -> sr.writeString((String) field.get(obj)));
        defaultSerializerMap.put(String[].class, (field, obj, sr) -> sr.writeStringArray((String[]) field.get(obj)));
        defaultSerializerMap.put(byte[].class, (field, obj, sr) -> sr.writeByteArray((byte[]) field.get(obj)));
        defaultSerializerMap.put(int[].class, (field, obj, sr) -> sr.writeIntArray((int[]) field.get(obj)));

        // default deserializers
        defaultDeSerializerMap.put(Boolean.class, (field, obj, sr) -> field.set(obj, sr.readBool()));
        defaultDeSerializerMap.put(Integer.class, (field, obj, sr) -> field.set(obj, sr.readInt32()));
        defaultDeSerializerMap.put(Float.class, (field, obj, sr) -> field.set(obj, sr.readFloat32()));
        defaultDeSerializerMap.put(Long.class, (field, obj, sr) -> field.set(obj, sr.readInt64()));
        defaultDeSerializerMap.put(Double.class, (field, obj, sr) -> field.set(obj, sr.readFloat64()));
        defaultDeSerializerMap.put(Byte.class, (field, obj, sr) -> field.set(obj, sr.readByte()));

        // primitives
        defaultDeSerializerMap.put(boolean.class, (field, obj, sr) -> field.set(obj, sr.readBool()));
        defaultDeSerializerMap.put(int.class, (field, obj, sr) -> field.set(obj, sr.readInt32()));
        defaultDeSerializerMap.put(float.class, (field, obj, sr) -> field.set(obj, sr.readFloat32()));
        defaultDeSerializerMap.put(long.class, (field, obj, sr) -> field.set(obj, sr.readInt64()));
        defaultDeSerializerMap.put(double.class, (field, obj, sr) -> field.set(obj, sr.readFloat64()));
        defaultDeSerializerMap.put(byte.class, (field, obj, sr) -> field.set(obj, sr.readByte()));

        defaultDeSerializerMap.put(String.class, (field, obj, sr) -> field.set(obj, sr.readString()));
        defaultDeSerializerMap.put(String[].class, (field, obj, sr) -> field.set(obj, sr.readStringArray()));
        defaultDeSerializerMap.put(byte[].class, (field, obj, sr) -> field.set(obj, sr.readByteArray()));
        defaultDeSerializerMap.put(int[].class, (field, obj, sr) -> field.set(obj, sr.readInt32Array()));

    }


}
