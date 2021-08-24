package me.nort3x.quanta.pub.exceptions;

/**
 * will thrown if and only if operation is not possible due truncated data,
 * if this exception is thrown in production state its indicating that you are not serializing and deserializing
 * in an standard way
 */
/*
    example of TruncatedException case

    class Obj{
        String s;
    }

    // this arr is representation of a primitive type:
    // 1. boolArray{false,false,false,true}
    // 2. int32 1
    // 3. float32 1.4E-45 (doubt anyone meant that)
    // why it cannot be any dynamic size type?! because if you interpret first 4 byte to size (int 1) there isn't anything for being read
    // and truncated error will be thrown

    byte[] arr = new byte[]{0,0,0,1};
    deserializer.deserialize(arr);



 */
public class TruncatedException extends RuntimeException{
    public TruncatedException() {
    }

    public TruncatedException(String message) {
        super(message);
    }

    public TruncatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TruncatedException(Throwable cause) {
        super(cause);
    }

    public TruncatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
