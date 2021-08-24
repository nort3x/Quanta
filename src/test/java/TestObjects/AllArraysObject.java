package TestObjects;

import java.util.Arrays;

public class AllArraysObject {
    int[] a;
    Integer[] b;
    float[] c;
    Float[] d;
    double[] e;
    Double[] f;
    long[] g;
    Long[] h;
    boolean[] k;
    Boolean[] j;

    byte[] l;
    Byte[] m;
    String[] n;

    NestedTestObject[] a1;


    public AllArraysObject(){
        a = new int[]{1,2,3};
        b = new Integer[]{1,2,3};

        c = new float[]{1,2,3};
        d = new Float[]{1f,2f,3f};

        e = new double[]{1,2,3};
        f = new Double[]{1d,2d,4d};

        g = new long[]{1,2,3};
        h = new Long[]{1L, 2L, 3L};
        k = new boolean[]{true,false,true};
        j = new Boolean[]{true,false,true};
        l = new byte[]{1,2,3};
        m = new Byte[]{1,2,3};
        n = new String[]{"hi","bye","apple"};
        a1 = new NestedTestObject[]{new NestedTestObject(),new NestedTestObject(),new NestedTestObject()};

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllArraysObject)) return false;
        AllArraysObject that = (AllArraysObject) o;
        return Arrays.equals(a, that.a) && Arrays.equals(b, that.b) && Arrays.equals(c, that.c) && Arrays.equals(d, that.d) && Arrays.equals(e, that.e) && Arrays.equals(f, that.f) && Arrays.equals(g, that.g) && Arrays.equals(h, that.h) && Arrays.equals(k, that.k) && Arrays.equals(j, that.j) && Arrays.equals(l, that.l) && Arrays.equals(m, that.m) && Arrays.equals(n, that.n) && Arrays.equals(a1, that.a1);
    }
}
