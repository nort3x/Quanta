package nortex.quanta.serialize.auto;

import nortex.quanta.legecySupport.BiConsumer;
import nortex.quanta.serialize.basic.Deserializer;
import nortex.quanta.serialize.basic.Serializer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicSerializer<T> {

    private static final String booleanType,int32Type,int64Type,byteType,float32Type,float64Type, stringType,byteArrayType,intArrayType,stringArrayType;
    static{
        booleanType = boolean.class.getTypeName();
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


    private List<Field> fields;
    Map<Field, BiConsumer<Serializer,Object>> serializerMap;
    Map<Field, BiConsumer<Deserializer,Object>> deSerializerMap;
    Class<T> clazz;
    public BasicSerializer(Class<T> clazz){
        fields = SerialBuilder.generateSerialDeserializeTool(clazz);
        serializerMap = mapSerialFunctions(fields);
        deSerializerMap = mapDeSerialFunctions(fields);
        this.clazz = clazz;
    }

    public byte[] serialize(T t){
        Serializer s = new Serializer();
        serializerMap.forEach((x,y)->{
            y.accept(s,t);
        });
        return s.toArray();
    }

    public T deserialize(byte[] arr) {
        Deserializer d = new Deserializer(arr);
        try {
            T t = clazz.newInstance();;
            deSerializerMap.forEach((x,y)->{
                y.accept(d,t);
            });
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



    private Map<Field, BiConsumer<Serializer,Object>> mapSerialFunctions(List<Field> lf){
        HashMap<Field,BiConsumer<Serializer,Object>> serialMap = new HashMap<>();
        lf.forEach(f->{
            String name = f.getType().getName();
            if(name.equals(booleanType))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeBool(f.getBoolean(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(int32Type))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeInt32(f.getInt(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(int64Type))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeInt64(f.getLong(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(byteType))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeByte(f.getByte(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

            else if(name.equals(float32Type))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeFloat32(f.getFloat(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(float64Type))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeFloat64(f.getDouble(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

            else if(name.equals(stringType))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeString((String) f.get(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

            else if(name.equals(byteArrayType))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeByteArray((byte[]) f.get(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(stringArrayType))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeStringArray((String[]) f.get(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(intArrayType))
                serialMap.put(f,(x,y)->{
                    try {
                        x.writeIntArray((int[]) f.get(y));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        });

        return serialMap;

    }
    private Map<Field, BiConsumer<Deserializer,Object>> mapDeSerialFunctions(List<Field> lf){
        HashMap<Field,BiConsumer<Deserializer,Object>> serialMap = new HashMap<>();
        lf.forEach(f->{
            String name = f.getType().getName();
            if(name.equals(booleanType))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readBool());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(int32Type))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readInt32());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(int64Type))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readInt64());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(byteType))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readByte());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

            else if(name.equals(float32Type))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readFloat32());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(float64Type))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readFloat64());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

            else if(name.equals(stringType))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

            else if(name.equals(byteArrayType))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readByteArray());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(stringArrayType))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readStringArray());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
            else if(name.equals(intArrayType))
                serialMap.put(f,(x,y)->{
                    try {
                        f.set(y,x.readInt32Array());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
        });

        return serialMap;

    }

}
