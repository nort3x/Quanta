import me.nort3x.quanta.pub.auto.NestedSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class NestedObjectTest {
   public static class N1{
        public int a;
        String b;
        N2 c2;
        String d;
        public N1() {
        }
        public N1(int i, String s, N2 n2, String end) {
            this.a = i;
            this.b = s;
            this.c2 = n2;
            this.d = end;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof N1)) return false;
            N1 n1 = (N1) o;
            return a == n1.a && Objects.equals(b, n1.b) && Objects.equals(c2, n1.c2) && Objects.equals(d, n1.d);
        }

    }
    public static class N2{
        String q;
        public N2(){

        }
        public N2(String q) {
            this.q = q;
        }
        public void setQ(String q) {
            this.q = q;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof N2)) return false;
            N2 n2 = (N2) o;
            return Objects.equals(q, n2.q);
        }

    }

    @Test
    void shouldSerializeAndDeserializeNestedObjects() throws IOException, NoSuchFieldException {
        NestedSerializer<N1> t1 = NestedSerializer.of(N1.class);

        N2 n2 = new N2("nort3x");
        N1 n1 = new N1(1,"start",n2,"end");

        byte[] serialized  = t1.serialize(n1);
        Files.write(new File("serialized.hex").toPath(),serialized);

        N1 deserializedN1 = t1.deserialize(serialized);

        Assertions.assertEquals(deserializedN1,n1);

    }
}
