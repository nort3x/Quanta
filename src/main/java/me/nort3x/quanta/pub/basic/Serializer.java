package me.nort3x.quanta.pub.basic;

import me.nort3x.quanta.internal.utils.Converters;

import java.io.ByteArrayOutputStream;
import java.util.function.Function;

/**
 * <h2>
 * Serializer
 * </h2>
 * this class will `pack` data to binary format please do not use this for conversions!
 * creation of package is sequential meaning that you have to read the data in the same order you packed it (at {@link Deserializer})
 *
 * @see Deserializer
 * @see Converters
 * @author H.ardaki
 */
final public class Serializer {

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public synchronized void writeByte(byte b) {
        bos.write(b);
    }

    public synchronized void writeBool(boolean b) {
        if (b) {
            writeByte((byte) 1);
        } else {
            writeByte((byte) 0);
        }
    }

    public synchronized void writeInt32(int i) {
        bos.write(Converters.int32ToBytesBigEndian(i),0,4);
    }

    public synchronized void writeInt64(long i) {
        bos.write(Converters.int64ToBytesBigEndian(i),0,8);
    }


    public synchronized void writeFloat32(float i) {
        bos.write(Converters.float32ToBytesBigEndian(i),0,4);
    }


    public synchronized void writeFloat64(double i) {
        bos.write(Converters.float64ToBytesBigEndian(i),0,8);
    }


    public synchronized void writeByteArray(byte[] arr) {
        if (arr == null) {
            writeInt32(0);
        } else {
            writeInt32(arr.length);
            bos.write(arr,0,arr.length);
        }
    }

    public synchronized void writeString(String s) {
        if (s == null) {
            writeByteArray(null);
            return;
        }
        writeByteArray(s.getBytes());
    }

    public synchronized <T> void writeObject(T o, Function<T,byte[]> convertor){
        writeByteArray(convertor.apply(o));
    }
    public synchronized <T> void writeObjectArray(T[] os,Function<T,byte[]> convertor){
        writeInt32(os.length);
        for (T t:
             os) {
            writeObject(t,convertor);
        }
    }

    public synchronized void writeIntArray(int[] arr){
        writeInt32(arr.length);
        for (int i :
                arr) {
            writeObject(i,Converters::int32ToBytesBigEndian);
        }
    }

    public synchronized void writeStringArray(String[] arr){
        writeObjectArray(arr,String::getBytes);
    }

    public synchronized byte[] toArray() {
        return bos.toByteArray();
    }
}
