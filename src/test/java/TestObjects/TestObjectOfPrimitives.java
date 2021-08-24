package TestObjects;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public  class TestObjectOfPrimitives {

    int i;
    int j;
    byte k;
    String s;
    byte[] someData;
    float constFloat = 1f / 3;
    String[] aDataTable;

    public TestObjectOfPrimitives() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestObjectOfPrimitives)) return false;
        TestObjectOfPrimitives that = (TestObjectOfPrimitives) o;
        return i == that.i && j == that.j && k == that.k && Float.compare(that.constFloat, constFloat) == 0 && Objects.equals(s, that.s) && Arrays.equals(someData, that.someData) && Arrays.equals(aDataTable, that.aDataTable);
    }


    static Random r = new Random();

    public static TestObjectOfPrimitives randomTestObject() {
        TestObjectOfPrimitives ans = new TestObjectOfPrimitives();
        ans.i = (int) (Math.random() * Integer.MAX_VALUE);
        ans.j = (int) (-Math.random() * Integer.MAX_VALUE);
        ans.k = (byte) r.nextInt();
        ans.someData = new byte[2+ (int) (700 * r.nextDouble())];
        r.nextBytes(ans.someData);
        ans.s = new String(Arrays.copyOfRange(ans.someData,0,ans.someData.length/2)); // not random but hey!
        ans.aDataTable = "i love efficiency".split(" ");

        return ans;

    }

}