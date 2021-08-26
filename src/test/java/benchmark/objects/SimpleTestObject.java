package benchmark.objects;

import java.io.Serializable;

public class SimpleTestObject implements Serializable {
    int a;
    Integer b;
    String[] c;
    float[] d;

    public SimpleTestObject(int a, Integer b, String[] c, float[] d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public SimpleTestObject(){}

    public static SimpleTestObject getOne(){
        return new SimpleTestObject(1,2,
                "some,Strings,here,as,shown".split(","),
                new float[]{1,2,3,4,5,6});
    }
}
