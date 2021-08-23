import me.nort3x.quanta.internal.utils.TestUtils;
import me.nort3x.quanta.pub.auto.NestedConvertor;
import org.junit.jupiter.api.Test;

public class StupidityTest {

    public static class C{
       public int i;
    }


    @Test void dontBeStupid() throws NoSuchFieldException, IllegalAccessException {
        C c = new C();
        c.i = 1;
        NestedConvertor<C> nestedConvertor = new NestedConvertor<>(C.class);
        System.out.println(TestUtils.bytesToHex(nestedConvertor.serialize(c)));
    }
}
