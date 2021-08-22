import me.nort3x.quanta.pub.basic.Deserializer;
import me.nort3x.quanta.pub.basic.Serializer;
import me.nort3x.quanta.internal.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ManualSerialization {

    @DisplayName("this test will approve core functionality and inner sync of serializer and deserializer")
    @Test void shouldBeAbleToMakeAQuanta(){

        Serializer s = new Serializer();

        s.writeByte((byte) 1);
        s.writeByte((byte) 2);
        s.writeByteArray("Quanta".getBytes());
        s.writeFloat64(1.2);
        s.writeFloat32(1.2f);
        s.writeBool(true);
        s.writeBool(false);
        s.writeInt32(20);
        s.writeInt64(200);
        s.writeStringArray("1,2,3,4".split(","));
        s.writeIntArray(new int[]{1, 2, 3, 4});

        // s.writeObject(YourObject,YourConverter);  // just to introduce API
        // s.writeObjectArray(YourObject,YourConverter);

        byte[] buff = s.toArray();

        System.out.print("Serialized Message as String : ");
        System.out.println(new String(buff));

        System.out.print("Serialized Message as Hex : ");
        System.out.println(TestUtils.bytesToHex(buff));


        Deserializer d = new Deserializer(buff);
        byte b1 = d.readByte();
        byte b2 = d.readByte();
        byte[] byteArr = d.readByteArray();
        double aDouble = d.readFloat64();
        float aFloat = d.readFloat32();
        boolean bool_1 = d.readBool();
        boolean bool_2 = d.readBool();
        int aInt = d.readInt32();
        long aLong = d.readInt64();
        String[] strings = d.readStringArray();
        int[] ints = d.readInt32Array();

        Assertions.assertEquals(b1, (byte) 1);
        Assertions.assertEquals(b2, (byte) 2);
        Assertions.assertArrayEquals(byteArr, "Quanta".getBytes());
        Assertions.assertEquals(aDouble, 1.2, 0.000001);
        Assertions.assertEquals(aFloat, 1.2f, 0.000001f);
        Assertions.assertTrue(bool_1 && !bool_2);
        Assertions.assertEquals(aInt, 20);
        Assertions.assertEquals(aLong, 200);
        Assertions.assertArrayEquals(strings, "1,2,3,4".split(","));
        Assertions.assertArrayEquals(ints, new int[]{1, 2, 3, 4});
    }
}
