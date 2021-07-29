import java.util.Arrays;
import java.util.Random;

public  class TestObject {

    int i;
    int j;
    byte k;
    String s;
    byte[] someData;
    float constFloat = 1f / 3;
    String[] aDataTable;

    public TestObject() {
    }

    static Random r = new Random();

    public static TestObject randomTestObject() {
        TestObject ans = new TestObject();
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