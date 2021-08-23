package me.nort3x.quanta.pub.basic;

import me.nort3x.quanta.internal.utils.Converters;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
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

    public synchronized <T> void writeObject(T o, Function<T,byte[]> convertor){
        writeByteArray(convertor.apply(o));
    }

    public synchronized void writeString(String s) {
        if (s == null) {
            writeByteArray(null);
            return;
        }
        writeByteArray(s.getBytes());
    }

    public synchronized <T> void writeObjectArray(T[] os,Function<T,byte[]> convertor){
        if (os == null) {
            writeInt32(0);
        } else {
            writeInt32(os.length);
            for (T t :
                    os) {
                writeObject(t, convertor);
            }
        }
    }

    public synchronized void writeInt32Array(int[] arr){
        if (arr == null) {
            writeInt32(0);
        } else {
            writeInt32(arr.length);
            for (int i :
                    arr) {
                writeInt32(i);
            }
        }
    }

    public synchronized void writeInt64Array(long[] arr){
        if (arr == null) {
            writeInt32(0);
        } else {
            writeInt32(arr.length);
            for (long l : arr) {
                writeInt64(l);
            }
        }
    }

    public synchronized void writeBoolArray(boolean[] arr){
        if (arr == null) {
            writeInt32(0);
        } else {
            writeInt32(arr.length);
            for (boolean b : arr) {
                writeBool(b);
            }
        }
    }

    public synchronized void writeFloat32Array(float[] arr){
        if (arr == null) {
            writeInt32(0);
        } else {
            writeInt32(arr.length);
            for (float f : arr) {
                writeFloat32(f);
            }
        }
    }

    public synchronized void writeFloat64Array(double[] arr){
        if (arr == null) {
            writeInt32(0);
        } else {
            writeInt32(arr.length);
            for (double d : arr) {
                writeFloat64(d);
            }
        }
    }

    public synchronized void writeByteArrayArray(byte[][] arr){
        if (arr == null) {
            writeInt32(0);
        } else {
            writeInt32(arr.length);
            for (byte[] bytes : arr) {
                writeByteArray(bytes);
            }
        }
    }

    public synchronized void writeStringArray(String[] arr){
        writeObjectArray(arr,String::getBytes);
    }

    public synchronized byte[] toArray() {
        return bos.toByteArray();
    }
}
