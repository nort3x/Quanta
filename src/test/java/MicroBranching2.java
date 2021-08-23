import TestObjects.TestObjectOfPrimitives;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;
import com.google.gson.Gson;
import me.nort3x.quanta.pub.auto.NestedConvertor;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

public class MicroBranching2 {

    NestedConvertor<TestObjectOfPrimitives> quanta;
    ObjectMapper messagePack;
    Gson gson;
    TestObjectOfPrimitives testObjectOfPrimitives;

    public MicroBranching2(){
        gson = new Gson();
        messagePack = new ObjectMapper(new MessagePackFactory());
        messagePack.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        quanta = new NestedConvertor<>(TestObjectOfPrimitives.class);
        testObjectOfPrimitives =TestObjectOfPrimitives.randomTestObject();
    }


//    @Benchmark
//    void messagePackSerializeAndDeserialize() throws IOException {
//        byte[] ser = messagePack.writeValueAsBytes(testObjectOfPrimitives);
//        TestObjectOfPrimitives re_obj = messagePack.readValue(ser,TestObjectOfPrimitives.class);
//    }
//    @Benchmark
//    void gsonSerializeAndDeserialize() throws IOException {
//        String ser =gson.toJson(testObjectOfPrimitives,TestObjectOfPrimitives.class);
//        TestObjectOfPrimitives re_obj = gson.fromJson(ser,TestObjectOfPrimitives.class);
//    }

    @Benchmark
    void quantaSerializeAndDeserialize() throws IOException {
        byte[] ser = quanta.serialize(testObjectOfPrimitives);
        TestObjectOfPrimitives re_obj = quanta.deserialize(ser);
    }


    public static void main(String[] args) {
        CaliperMain.main(MicroBranching2.class,args);
    }
}
