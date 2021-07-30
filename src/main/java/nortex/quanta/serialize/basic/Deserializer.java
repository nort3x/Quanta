package nortex.quanta.serialize.basic;

import nortex.quanta.legecySupport.Function;
import nortex.quanta.utils.Converters;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;

/**
 * <h2>
 * Deserializer
 * </h2>
 * this class will `unpack` data from binary format please do not use this for conversions!<br/>
 * unpacking is sequential meaning that you have to read the data in the same order you packed it
 *
 * @see Serializer
 * @see Converters
 * @author H.ardaki
 */
final public class Deserializer {

    ByteArrayInputStream bis;

    /**
     * @param arr array containing sequential serialized data
     */
    public Deserializer(byte[] arr) {
        if (arr == null)
            throw new NullPointerException();
        bis = new ByteArrayInputStream(arr);
    }


    public synchronized byte readByte() {
        return (byte) bis.read();
    }

    public synchronized boolean readBool() {
        return ((byte) bis.read()) == 1;
    }

    public synchronized int readInt32() {
        byte[] buff = new byte[4];
        bis.readNBytes(buff, 0, buff.length);
        return (int) Converters.bytesToInt32BigEndian(buff);
    }


    public synchronized long readInt64() {
        byte[] buff = new byte[8];
        bis.readNBytes(buff, 0, buff.length);
        return Converters.bytesToInt64BigEndian(buff);
    }


    public synchronized float readFloat32() {
        byte[] buff = new byte[4];
        bis.readNBytes(buff, 0, buff.length);
        return Converters.bytesToFloat32BigEndian(buff);
    }


    public synchronized double readFloat64() {
        byte[] buff = new byte[8];
        bis.readNBytes(buff, 0, buff.length);
        return Converters.bytesToFloat64BigEndian(buff);
    }


    public synchronized byte[] readByteArray() {
        int i = readInt32();
        if (i == 0)
            return null;
        byte[] buff = new byte[i];
        bis.readNBytes(buff, 0, buff.length);
        return buff;
    }

    public synchronized String readString() {
        return new String(readByteArray());
    }



    public synchronized <T> T readObject(Function<byte[],T> convertor){
        return convertor.apply(readByteArray());
    }

    @SuppressWarnings("unchecked")
    public synchronized <T> T[] readObjectArray(Function<byte[],T> convertor,Class<T> clazz){
        int i = readInt32();
        T[] objects = (T[]) Array.newInstance(clazz,i);
        for (int j = 0; j < i; j++) {
            objects[j] = readObject(convertor);
        }
        return  objects;
    }

    public synchronized int[] readInt32Array(){
        int i = readInt32();
        int[] objects = new int[i];
        for (int j = 0; j < i; j++) {
            objects[j] = readObject(Converters::bytesToInt32BigEndian);
        }
        return objects;
    }

    public synchronized String[] readStringArray(){
        return readObjectArray(String::new,String.class);
    }

}
