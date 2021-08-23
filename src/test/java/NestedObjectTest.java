import me.nort3x.quanta.pub.auto.NestedConvertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class NestedObjectTest {
   public static class N1{
        public int a;
        String b;
        N2 c;
        String d;
        N2[] f;
        public N1() {
        }
        public N1(int i, String s, N2 n2, String end,N2[] f) {
            this.a = i;
            this.b = s;
            this.c = n2;
            this.d = end;
            this.f = f;
        }

       @Override
       public boolean equals(Object o) {
           if (this == o) return true;
           if (!(o instanceof N1)) return false;
           N1 n1 = (N1) o;
           return a == n1.a && Objects.equals(b, n1.b) && Objects.equals(c, n1.c) && Objects.equals(d, n1.d) && Arrays.equals(f, n1.f);
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
        NestedConvertor<N1> t1 = new NestedConvertor<>(N1.class);
        N2 n2 = new N2("nort3x");
        N1 n1 = new N1(1,"start",n2,"end",new N2[]{new N2("1"),new N2("2")});

        byte[] serialized  = t1.serialize(n1);
        Files.write(new File("serialized.hex").toPath(),serialized);

        N1 deserializedN1 = t1.deserialize(serialized);

        Assertions.assertEquals(deserializedN1,n1);

    }
}
