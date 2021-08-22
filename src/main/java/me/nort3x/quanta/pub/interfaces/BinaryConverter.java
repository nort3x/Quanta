package me.nort3x.quanta.pub.interfaces;

public interface BinaryConverter<T> {
    byte[] serialize(T t);

    T deserialize(byte[] arr);
}
