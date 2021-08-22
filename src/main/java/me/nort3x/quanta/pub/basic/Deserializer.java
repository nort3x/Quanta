package me.nort3x.quanta.pub.basic;

import me.nort3x.quanta.internal.utils.Converters;

import java.io.ByteArrayInputStream;
import java.util.function.Function;

/**
 * <h2>
 * Deserializer
 * </h2>
 * this class will `unpack` data from binary format please do not use this for conversions!<a></a>
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
        return Converters.bytesToInt32BigEndian(readNBytes(4));
    }


    public synchronized long readInt64() {
        return Converters.bytesToInt64BigEndian(readNBytes(8));
    }


    public synchronized float readFloat32() {
        return Converters.bytesToFloat32BigEndian(readNBytes(4));
    }


    public synchronized double readFloat64() {
        return Converters.bytesToFloat64BigEndian(readNBytes(8));
    }


    /**
     * @return next byte array from buffer
     * @throws RuntimeException if String is truncated and was not able to provide requested length
     */
    public synchronized byte[] readByteArray() {
        int i = readInt32();
        if (i == 0)
            return null;
        return readNBytes(i);
    }


    /**
     * @return next String from buffer
     * @throws RuntimeException if String is truncated and was not able to provide requested length
     */
    public synchronized String readString() {
        return new String(readByteArray());
    }


    /**
     * @return next Object from buffer
     * @param convertor deserializer of possible object  , can also throw RuntimeException if its malformed
     * @throws RuntimeException if array is truncated and was not able to provide requested length or when its malformed
     */
    public synchronized <T> T readObject(Function<byte[],T> convertor){
        return convertor.apply(readByteArray());
    }

    /**
     * repetitive overload of {@link Deserializer#readObject(Function)}
     * @param convertor deserializer of possible object  , can also throw RuntimeException if its malformed
     * @return next array of T
     * @throws RuntimeException if array is truncated and was not able to provide requested length or when its malformed (if any in array)
     */
    @SuppressWarnings("unchecked")
    public synchronized <T> T[] readObjectArray(Function<byte[],T> convertor){
        int i = readInt32();
        T[] objects = (T[]) new Object[i];
        for (int j = 0; j < i; j++) {
            objects[j] = readObject(convertor);
        }
        return  objects;
    }


    /**
     * @return next int32 (int) from buffer
     * @throws RuntimeException if array is truncated and was not able to provide requested length
     */
    public synchronized int[] readInt32Array(){
        int i = readInt32();
        int[] objects = new int[i];
        for (int j = 0; j < i; j++) {
            objects[j] = readObject(Converters::bytesToInt32BigEndian);
        }
        return objects;
    }

    /**
     * @return next string array from buffer
     * @throws RuntimeException if array is truncated and was not able to provide requested length
     */
    public synchronized String[] readStringArray(){
        return readObjectArray(String::new);
    }

    /**
     * @param n number of bytes requested
     * @return byte array containing exactly number of bytes requested
     * @throws RuntimeException if array is truncated and was not able to provide requested length
     */
    byte[] readNBytes(int n){
        byte[] arr = new byte[n];
        if(bis.read(arr,0,n)!=n)
            throw new RuntimeException("buffer is truncated respecting to what is expected");
        return arr;
    }
}
