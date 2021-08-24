package TestObjects;

import java.util.LinkedList;
import java.util.Objects;

public class ObjectWithCollection {
    LinkedList<TestObjectOfPrimitives> objectOfPrimitives;
    public ObjectWithCollection(){
        objectOfPrimitives = new LinkedList<>();
        objectOfPrimitives.add(TestObjectOfPrimitives.randomTestObject());
        objectOfPrimitives.add(TestObjectOfPrimitives.randomTestObject());
        objectOfPrimitives.add(TestObjectOfPrimitives.randomTestObject());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectWithCollection)) return false;
        ObjectWithCollection that = (ObjectWithCollection) o;
        return Objects.equals(objectOfPrimitives, that.objectOfPrimitives);
    }

}
