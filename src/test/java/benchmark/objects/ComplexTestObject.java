package benchmark.objects;

import java.io.Serializable;

public class ComplexTestObject implements Serializable {
    String a_start ;
    SimpleTestObject[] b_objects;
    String c_end;

    public ComplexTestObject(){}

    public ComplexTestObject(String a_start, SimpleTestObject[] b_objects, String c_end) {
        this.a_start = a_start;
        this.b_objects = b_objects;
        this.c_end = c_end;
    }

    public static ComplexTestObject getOne(){
        return new ComplexTestObject("start",
                        new SimpleTestObject[]{ SimpleTestObject.getOne(), SimpleTestObject.getOne(), SimpleTestObject.getOne()}
                ,"end");
    }

}
