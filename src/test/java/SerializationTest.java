import me.nort3x.quanta.pub.auto.PrimitiveConvertor;
import org.junit.jupiter.api.Test;

public class SerializationTest {
    @Test
    void shouldConvertNumbers() {



    }


    public static class TestOBJ {
        private int i;
        public float j;
        protected String s;
        transient String shouldNotShowingThis;
        public TestOBJ(){}
    }

    @Test
    void shouldSerializeAnObject() {

        TestOBJ f = new TestOBJ();
        f.i = 2;
        f.s = "hellow";

        PrimitiveConvertor<TestOBJ> serializer = new PrimitiveConvertor<>(TestOBJ.class);

        byte[] arr = serializer.serialize(f);
        TestOBJ q = serializer.deserialize(arr);
        System.out.println(q.s);

        System.out.println();

    }

}
