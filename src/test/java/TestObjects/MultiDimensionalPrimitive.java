package TestObjects;

import java.util.Arrays;

public class MultiDimensionalPrimitive {
    int[][] a;
    String[][] b;
    public MultiDimensionalPrimitive(){
        a = new int[][]{
                new int[]{1,0},
                new int[]{0,1}
        };
        b = new String[][]{
                new String[]{"food","pizza"},
                new String[]{"fruit","apple"}
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiDimensionalPrimitive)) return false;
        MultiDimensionalPrimitive that = (MultiDimensionalPrimitive) o;
        return Arrays.deepEquals(a, that.a) && Arrays.deepEquals(b, that.b);
    }

}
