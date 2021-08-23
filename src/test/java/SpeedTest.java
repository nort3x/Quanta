import TestObjects.TestObjectOfPrimitives;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import me.nort3x.quanta.pub.auto.NestedConvertor;
import me.nort3x.quanta.pub.auto.PrimitiveConvertor;
import me.nort3x.quanta.internal.utils.SexyTimer;
import org.junit.jupiter.api.Test;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpeedTest {




    // will generate a test result file which can describe over all comparison between serialization methods
    @Test
    void shouldGenerateTestResultFile() throws IOException {
        TestObjectOfPrimitives t =  TestObjectOfPrimitives.randomTestObject();

        List<Long> g_res = gsonTestResult(t,200);
        List<Long> m_res = messagePackTestResult(t,200);
        List<Long> q_res = quantaTestResult(t,200);
        List<Long> q2_res = quanta2TestResult(t,200);


        System.out.println("Gson average: " + g_res.stream().mapToInt(Long::intValue).average().getAsDouble());
        System.out.println("Quanta2 average: " + q2_res.stream().mapToInt(Long::intValue).average().getAsDouble());
        System.out.println("Quanta average: " + q_res.stream().mapToInt(Long::intValue).average().getAsDouble());
        System.out.println("MessagePack average: " + m_res.stream().mapToInt(Long::intValue).average().getAsDouble());

        FileOutputStream fos = new FileOutputStream("gson_mpack_q1_q2_speed.dat");
        for (int i = 0; i < 200; i++) {
            fos.write((g_res.get(i)+"\t"+m_res.get(i)+"\t"+q_res.get(i)+"\t"+q2_res.get(i)+"\n").getBytes());
        }
        fos.close();
    }


    List<Long> quantaTestResult(TestObjectOfPrimitives data, int tries) {
        PrimitiveConvertor<TestObjectOfPrimitives> mapper =  new PrimitiveConvertor<>(TestObjectOfPrimitives.class);
        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < tries; i++) {
            arr.add(
                    SexyTimer.getMean(() -> {

                        byte[] bin = mapper.serialize(data);
                        TestObjectOfPrimitives t = mapper.deserialize(bin);

                    }, 1000).getDuration()
            );
        }
        return arr;
    }

    List<Long> quanta2TestResult(TestObjectOfPrimitives data, int tries) {
        NestedConvertor<TestObjectOfPrimitives> mapper =  new NestedConvertor<>(TestObjectOfPrimitives.class);
        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < tries; i++) {
            arr.add(
                    SexyTimer.getMean(() -> {

                        byte[] bin = mapper.serialize(data);
                        TestObjectOfPrimitives t = mapper.deserialize(bin);

                    }, 1000).getDuration()
            );
        }
        return arr;
    }


    List<Long> gsonTestResult(TestObjectOfPrimitives data, int tries) {
        Gson g = new Gson();
        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < tries; i++) {
            arr.add(
                    SexyTimer.getMean(() -> {

                        String s = g.toJson(data);
                        TestObjectOfPrimitives t = g.fromJson(s, TestObjectOfPrimitives.class);

                    }, 1000).getDuration()
            );
        }
        return arr;
    }


    List<Long> messagePackTestResult(TestObjectOfPrimitives data, int tries) {
        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < tries; i++) {
            arr.add(
                    SexyTimer.getMean(() -> {

                        try {
                            byte[] buff = objectMapper.writeValueAsBytes(data);
                            TestObjectOfPrimitives t = objectMapper.readValue(buff, TestObjectOfPrimitives.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }, 1000).getDuration()
            );
        }
        return arr;
    }



}
