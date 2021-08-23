package TestObjects;

import java.util.Arrays;

public class MultiDimensionalOfCustoms {
    NestedTestObject[][][] a;
    public MultiDimensionalOfCustoms(){
        a = new NestedTestObject[][][]{
                new NestedTestObject[][]{
                        new NestedTestObject[]{
                                new NestedTestObject(),new NestedTestObject()
                        }
                },new NestedTestObject[][]{
                        new NestedTestObject[]{
                                new NestedTestObject(),
                                new NestedTestObject(),
                                new NestedTestObject()
                        },
                new NestedTestObject[]{
                        new NestedTestObject(),new NestedTestObject()
                }
        }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiDimensionalOfCustoms)) return false;
        MultiDimensionalOfCustoms that = (MultiDimensionalOfCustoms) o;
        return Arrays.deepEquals(a, that.a);
    }
}
