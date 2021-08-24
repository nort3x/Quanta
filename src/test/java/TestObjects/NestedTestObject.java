package TestObjects;

import java.util.Objects;

public class NestedTestObject {
    String a;
    TestObjectOfPrimitives t1;
    String t2;
    TestObjectOfPrimitives t3;
    String z;

    public NestedTestObject(){
        t1 = TestObjectOfPrimitives.randomTestObject();
        t3 = TestObjectOfPrimitives.randomTestObject();

        a = "beginning";
        z = "end";
        t2 = "middle";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NestedTestObject)) return false;
        NestedTestObject that = (NestedTestObject) o;
        return Objects.equals(t1, that.t1) && Objects.equals(t2, that.t2);
    }
}
