package me.nort3x.quanta.internal.utils;

public class Converters {

    public static int bytesToInt16BigEndian(byte[] arr) {
        return ((arr[0] & 0xFF) << 8) |
                ((arr[1] & 0xFF));
    }

    public static byte[] uint16ToBytesBigEndian(int i) {
        return new byte[]{
                (byte) ((i >> 8) & 0xFF),
                (byte) (i & 0xFF)
        };
    }

    public static int bytesToInt32BigEndian(byte[] arr) {
        return ((arr[0] & 0xFF) << 24) |
                ((arr[1] & 0xFF) << 16) |
                ((arr[2] & 0xFF) << 8) |
                ((arr[3] & 0xFF));
    }

    public static byte[] int32ToBytesBigEndian(int l) {
        return new byte[]{
                (byte) ((l >> 24) & 0xFF),
                (byte) ((l >> 16) & 0xFF),
                (byte) ((l >> 8) & 0xFF),
                (byte) (l & 0xff)
        };
    }



    public static long bytesToUInt32BigEndian(byte[] arr) {
        return ((arr[0] & 0xFF) << 24) |
                ((arr[1] & 0xFF) << 16) |
                ((arr[2] & 0xFF) << 8) |
                ((arr[3] & 0xFF));
    }

    public static byte[] uint32ToBytesBigEndian(long l) {
        return new byte[]{
                (byte) ((l >> 24) & 0xFF),
                (byte) ((l >> 16) & 0xFF),
                (byte) ((l >> 8) & 0xFF),
                (byte) (l & 0xff)
        };
    }


    public static long bytesToInt64BigEndian(byte[] b) {
        return ((long) b[0] << 56)
                | ((long) b[1] & 0xff) << 48
                | ((long) b[2] & 0xff) << 40
                | ((long) b[3] & 0xff) << 32
                | ((long) b[4] & 0xff) << 24
                | ((long) b[5] & 0xff) << 16
                | ((long) b[6] & 0xff) << 8
                | ((long) b[7] & 0xff);
    }

    public static byte[] int64ToBytesBigEndian(long l) {
        return new byte[]{
                (byte) ((l >> 56) & 0xFF),
                (byte) ((l >> 48) & 0xFF),
                (byte) ((l >> 40) & 0xFF),
                (byte) ((l >> 32) & 0xFF),
                (byte) ((l >> 24) & 0xFF),
                (byte) ((l >> 16) & 0xFF),
                (byte) ((l >> 8) & 0xFF),
                (byte) (l & 0xff)
        };
    }


    public static float bytesToFloat32BigEndian(byte[] arr) {
        return Float.intBitsToFloat(bytesToInt32BigEndian(arr));
    }

    public static byte[] float32ToBytesBigEndian(float l) {
        return int32ToBytesBigEndian(Float.floatToIntBits(l));
    }

    public static double bytesToFloat64BigEndian(byte[] b) {
        return Double.longBitsToDouble(bytesToInt64BigEndian(b));
    }

    public static byte[] float64ToBytesBigEndian(double d) {
        return int64ToBytesBigEndian(Double.doubleToLongBits(d));
    }


}
