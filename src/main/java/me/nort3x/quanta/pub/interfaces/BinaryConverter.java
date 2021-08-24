package me.nort3x.quanta.pub.interfaces;

/**
 * any class which can do BinaryConversion between native objects to array of bytes will inherited this interface
 * @param <T>
 */
public interface BinaryConverter<T> {

    /**
     * @param t object instance to be serialized
     * @return byte-array representation of instance t
     */
    byte[] serialize(T t);

    /**
     * @param arr allocated byte-array that is translatable to Type T
     * @return instance to allocated T that corresponds to given array
     */
    T deserialize(byte[] arr);

}
