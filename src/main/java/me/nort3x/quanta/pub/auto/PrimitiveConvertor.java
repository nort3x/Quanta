package me.nort3x.quanta.pub.auto;

import me.nort3x.quanta.internal.auto.Scanner;
import me.nort3x.quanta.legacy_support.BiConsumer;
import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.pub.interfaces.BinaryConverter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimitiveConvertor<T> implements BinaryConverter<T> {

    private static final String booleanType,int32Type,int64Type,byteType,float32Type,float64Type, stringType,byteArrayType,intArrayType,stringArrayType;
    static{
        booleanType = boolean.class.getName();
        int32Type = int.class.getName();
        int64Type = long.class.getName();
        byteType = byte.class.getName();
        float32Type = float.class.getName();
        float64Type = double.class.getName();
        stringType = String.class.getName();
        byteArrayType = byte[].class.getName();
        intArrayType = int[].class.getName();
        stringArrayType = String[].class.getName();
    }


    private final List<Field> fields;
    Map<Field, BiConsumer<Serializer,Object>> serializerMap;
    Map<Field, BiConsumer<Deserializer,Object>> deSerializerMap;
    Class<T> clazz;
    public PrimitiveConvertor(Class<T> clazz){
        fields = Scanner.getFields(clazz);
        serializerMap = mapSerialFunctions(fields);
        deSerializerMap = mapDeSerialFunctions(fields);
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t){
        Serializer s = new Serializer();

        for (BiConsumer<Serializer,Object> effect : serializerMap.values()) {
            effect.accept(s,t);
        }

        return s.toArray();
    }

    @Override
    public T deserialize(byte[] arr) {
        Deserializer d = new Deserializer(arr);
        try {
            T t = clazz.newInstance();
            for (BiConsumer<Deserializer,Object> effect : deSerializerMap.values()) {
                effect.accept(d,t);
            }
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



    private Map<Field, BiConsumer<Serializer,Object>> mapSerialFunctions(List<Field> lf){
        HashMap<Field,BiConsumer<Serializer,Object>> serialMap = new HashMap<>();
        for (final Field f : lf) {
                String name = f.getType().getName();
                if (name.equals(booleanType))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeBool(f.getBoolean(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(int32Type))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeInt32(f.getInt(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(int64Type))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeInt64(f.getLong(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(byteType))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeByte(f.getByte(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                else if (name.equals(float32Type))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeFloat32(f.getFloat(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(float64Type))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeFloat64(f.getDouble(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                else if (name.equals(stringType))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeString((String) f.get(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                else if (name.equals(byteArrayType))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeByteArray((byte[]) f.get(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(stringArrayType))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeStringArray((String[]) f.get(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(intArrayType))
                    serialMap.put(f, new BiConsumer<Serializer, Object>() {
                        @Override
                        public void accept(Serializer x, Object y) {
                            try {
                                x.writeInt32Array((int[]) f.get(y));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            }

        return serialMap;

    }
    private Map<Field, BiConsumer<Deserializer,Object>> mapDeSerialFunctions(List<Field> lf){
        final HashMap<Field,BiConsumer<Deserializer,Object>> serialMap = new HashMap<>();
        for (final Field f : lf) {
                String name = f.getType().getName();
                if (name.equals(booleanType))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readBool());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(int32Type))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readInt32());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(int64Type))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readInt64());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(byteType))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readByte());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                else if (name.equals(float32Type))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readFloat32());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(float64Type))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readFloat64());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                else if (name.equals(stringType))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readString());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                else if (name.equals(byteArrayType))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readByteArray());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(stringArrayType))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readStringArray());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                else if (name.equals(intArrayType))
                    serialMap.put(f, new BiConsumer<Deserializer, Object>() {
                        @Override
                        public void accept(Deserializer x, Object y) {
                            try {
                                f.set(y, x.readInt32Array());
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            }


        return serialMap;

    }

}
