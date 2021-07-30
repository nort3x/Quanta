import nortex.quanta.serialize.auto.BasicSerializer;
import com.google.gson.Gson;
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

        BasicSerializer<TestOBJ> serializer = new BasicSerializer<>(TestOBJ.class);

        byte[] arr = serializer.serialize(f);
        TestOBJ q = serializer.deserialize(arr);
        System.out.println(q.s);

        System.out.println();

    }

}
