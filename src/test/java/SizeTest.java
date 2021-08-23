import TestObjects.TestObjectOfPrimitives;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import me.nort3x.quanta.pub.auto.NestedConvertor;
import me.nort3x.quanta.pub.auto.PrimitiveConvertor;
import org.junit.jupiter.api.Test;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SizeTest {



    // will generate a test result file which can describe over all comparison between serialization methods
    @Test
    void shouldGenerateTestResultFile() throws IOException {
        List<TestObjectOfPrimitives> objects = new ArrayList<>();
        for (int x = 0; x < 300; x++) {
            TestObjectOfPrimitives testObjectOfPrimitives = TestObjectOfPrimitives.randomTestObject();
            objects.add(testObjectOfPrimitives);
        }

        List<Long> g_res = gsonTestResult(objects);
        List<Long> m_res = messagePackTestResult(objects);
        List<Long> q_res = quantaTestResult(objects);
        List<Long> q2_res = quanta2TestResult(objects);


        System.out.println("Gson average: " + g_res.stream().mapToInt(Long::intValue).average().getAsDouble());
        System.out.println("MessagePack average: " + m_res.stream().mapToInt(Long::intValue).average().getAsDouble());
        System.out.println("Quanta average: " + q_res.stream().mapToInt(Long::intValue).average().getAsDouble());
        System.out.println("Quanta2 average: " + q2_res.stream().mapToInt(Long::intValue).average().getAsDouble());

        FileOutputStream fos = new FileOutputStream("gson_mpack_q1_q2_size.dat");
        for (int i = 0; i < 200; i++) {
            fos.write((g_res.get(i)+"\t"+m_res.get(i)+"\t"+q_res.get(i)+"\t"+q2_res.get(i)+"\n").getBytes());
        }
        fos.close();
    }


    List<Long> quantaTestResult(List<TestObjectOfPrimitives> data) {
        PrimitiveConvertor<TestObjectOfPrimitives> mapper =  new PrimitiveConvertor<>(TestObjectOfPrimitives.class);
        List<Long> arr = new ArrayList<>();
        for (TestObjectOfPrimitives datum : data) {
            arr.add((long) mapper.serialize(datum).length);
        }
        return arr;
    }

    List<Long> quanta2TestResult(List<TestObjectOfPrimitives> data) {
        NestedConvertor<TestObjectOfPrimitives> mapper =  new NestedConvertor<>(TestObjectOfPrimitives.class);
        List<Long> arr = new ArrayList<>();
        for (TestObjectOfPrimitives datum : data) {
            arr.add((long) mapper.serialize(datum).length);
        }
        return arr;
    }

    List<Long> gsonTestResult(List<TestObjectOfPrimitives> data) {
        Gson g = new Gson();
        List<Long> arr = new ArrayList<>();
        for (TestObjectOfPrimitives datum : data) {
            arr.add((long) g.toJson(datum).length());
        }
        return arr;
    }


    List<Long> messagePackTestResult(List<TestObjectOfPrimitives> data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        List<Long> arr = new ArrayList<>();
        for (TestObjectOfPrimitives datum : data) {
            arr.add((long) objectMapper.writeValueAsBytes(datum).length);
        }
        return arr;
    }


}
