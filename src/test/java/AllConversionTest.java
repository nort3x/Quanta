import TestObjects.*;
import me.nort3x.quanta.internal.utils.TestUtils;
import me.nort3x.quanta.pub.auto.NestedConvertor;
import me.nort3x.quanta.pub.auto.PrimitiveConvertor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AllConversionTest {


    @Test
    void shouldConvertPrimitiveObjects(){
        PrimitiveConvertor<TestObjectOfPrimitives> convertor = new PrimitiveConvertor<>(TestObjectOfPrimitives.class);
        TestObjectOfPrimitives obj = TestObjectOfPrimitives.randomTestObject();
        byte[] ser = convertor.serialize(obj);
        System.out.println("Binary: "+new String(ser));
        System.out.println("Hex: "+ TestUtils.bytesToHex(ser));
        TestObjectOfPrimitives re_obj = convertor.deserialize(ser);
        Assertions.assertEquals(re_obj,obj);

    }

    @Test
    void shouldConvertPrimitiveObjectsWithNestedConverter(){
        NestedConvertor<TestObjectOfPrimitives> convertor = new NestedConvertor<>(TestObjectOfPrimitives.class);
        TestObjectOfPrimitives obj = TestObjectOfPrimitives.randomTestObject();
        byte[] ser = convertor.serialize(obj);
        System.out.println("Binary: "+new String(ser));
        System.out.println("Hex: "+ TestUtils.bytesToHex(ser));
        TestObjectOfPrimitives re_obj = convertor.deserialize(ser);
        Assertions.assertEquals(re_obj,obj);

    }

    @Test
    void shouldConvertNestedObjectsWithNestedConverter(){
        NestedConvertor<NestedTestObject> convertor = new NestedConvertor<>(NestedTestObject.class);
        NestedTestObject obj = new NestedTestObject();
        byte[] ser = convertor.serialize(obj);
        System.out.println("Binary: "+new String(ser));
        System.out.println("Hex: "+ TestUtils.bytesToHex(ser));
        NestedTestObject re_obj = convertor.deserialize(ser);
        Assertions.assertEquals(re_obj,obj);
    }

    @Test
    void shouldConvertArrayObjectWithNestedConverter(){
        NestedConvertor<AllArraysObject> convertor = new NestedConvertor<>(AllArraysObject.class);
        AllArraysObject obj = new AllArraysObject();
        byte[] ser = convertor.serialize(obj);
        System.out.println("Binary: "+new String(ser));
        System.out.println("Hex: "+ TestUtils.bytesToHex(ser));
        AllArraysObject re_obj = convertor.deserialize(ser);
        Assertions.assertEquals(re_obj,obj);
    }

    @Test
    void shouldConvertMultiDimensionalPrimitives(){
        NestedConvertor<MultiDimensionalPrimitive> convertor = new NestedConvertor<>(MultiDimensionalPrimitive.class);
        MultiDimensionalPrimitive obj = new MultiDimensionalPrimitive();
        byte[] ser = convertor.serialize(obj);
        System.out.println("Binary: "+new String(ser));
        System.out.println("Hex: "+ TestUtils.bytesToHex(ser));
        MultiDimensionalPrimitive re_obj = convertor.deserialize(ser);
        Assertions.assertEquals(re_obj,obj);
    }


    @Test
    void shouldConvertMultiDimensionalCustoms(){
        NestedConvertor<MultiDimensionalOfCustoms> convertor = new NestedConvertor<>(MultiDimensionalOfCustoms.class);
        MultiDimensionalOfCustoms obj = new MultiDimensionalOfCustoms();
        byte[] ser = convertor.serialize(obj);
        System.out.println("Binary: "+new String(ser));
        System.out.println("Hex: "+ TestUtils.bytesToHex(ser));
        MultiDimensionalOfCustoms re_obj = convertor.deserialize(ser);
        Assertions.assertEquals(re_obj,obj);
    }

    @Test
    void shouldConvertTypesWithCollections(){
        NestedConvertor<ObjectWithCollection> convertor = new NestedConvertor<>(ObjectWithCollection.class);
        ObjectWithCollection obj = new ObjectWithCollection();
        byte[] ser = convertor.serialize(obj);
        System.out.println("Binary: "+new String(ser));
        System.out.println("Hex: "+ TestUtils.bytesToHex(ser));
        ObjectWithCollection re_obj = convertor.deserialize(ser);
        Assertions.assertEquals(re_obj,obj);
    }




    @Test
    void shouldSerializeSinaModel(){
        NestedConvertor<SinaModelObject> smc = new NestedConvertor<>(SinaModelObject.class);

        SinaModelObject sm = new SinaModelObject();
        sm.points.add(new ModelPoint(1,2));
        sm.points.add(new ModelPoint(2,2));
        sm.map.put(1,new ModelPoint(1,1));

        byte[] sered = smc.serialize(sm);
        SinaModelObject reConstructed = smc.deserialize(sered);

        System.out.println(TestUtils.bytesToHex(sered));

        Assertions.assertEquals(reConstructed,sm);

    }

    @Test
    void shouldSerializeEnumTypes(){
        EnumedType et = new EnumedType(EnumedType.ItsAnEnum.enum3);
        NestedConvertor<EnumedType> convertor = new NestedConvertor<>(EnumedType.class);
        byte[] h = convertor.serialize(et);
        System.out.println(TestUtils.bytesToHex(h));
        Assertions.assertEquals(convertor.deserialize(h).getAnEnum(), EnumedType.ItsAnEnum.enum3);
    }


}
