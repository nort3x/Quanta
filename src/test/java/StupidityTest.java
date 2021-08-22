import me.nort3x.quanta.internal.auto.CustomTypeConverter;
import me.nort3x.quanta.internal.utils.TestUtils;
import me.nort3x.quanta.pub.auto.SmarterSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class StupidityTest {

    public static class C{
       public int i;
    }


    @Test void dontBeStupid() throws NoSuchFieldException, IllegalAccessException {
        C c = new C();
        c.i = 1;
        SmarterSerializer<C> smarterSerializer = new SmarterSerializer<>(C.class);
        System.out.println(TestUtils.bytesToHex(smarterSerializer.serialize(c)));
    }
}
