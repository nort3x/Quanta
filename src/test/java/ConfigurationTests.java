import me.nort3x.quanta.internal.utils.TestUtils;
import me.nort3x.quanta.pub.auto.NestedConvertor;
import me.nort3x.quanta.pub.config.QuantaConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConfigurationTests {

    public static class StaticClass{
        static int a=2;

        public StaticClass() {
        }

        public static int getA() {
            return a;
        }


    }


    @Test
    void shouldNotIgnoreStaticFields(){


        QuantaConfiguration q = new QuantaConfiguration()
                .setIgnoreStaticFields(false);

        NestedConvertor<StaticClass> staticClassNestedConvertor = new NestedConvertor<>(StaticClass.class,q);

        byte[] ser =  staticClassNestedConvertor.serialize(new StaticClass());
        System.out.println(TestUtils.bytesToHex(ser));
        ser[3] = 4; // changing a to 4
        StaticClass s = staticClassNestedConvertor.deserialize(ser); // beware a is static and is now changed for everyone
        Assertions.assertEquals(s.getA(),4);

    }
}
