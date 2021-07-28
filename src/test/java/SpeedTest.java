import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import nortex.quanta.serialize.auto.BasicSerializer;
import nortex.quanta.utils.SexyTimer;
import org.junit.jupiter.api.Test;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessagePacker;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpeedTest {

    public static class TestObject {

        int i;
        int j;
        byte k;
        String s;
        byte[] someData;
        float constFloat = 1f / 3;
        String[] aDataTable;

        public TestObject() {
        }

    }


    // will generate a test result file which can describe over all comparison between serialization methods
    @Test
    void shouldGenerateTestResultFile() throws IOException {
        TestObject t =  randomTestObject();

        List<Long> g_res = gsonTestResult(t,200);
        List<Long> m_res = quantaTestResult(t,200);
        List<Long> q_res = quantaTestResult(t,200);


        System.out.println("Gson average: " + g_res.stream().mapToInt(Long::intValue).average().getAsDouble());
        System.out.println("MessagePack average: " + m_res.stream().mapToInt(Long::intValue).average().getAsDouble());
        System.out.println("Quanta average: " + q_res.stream().mapToInt(Long::intValue).average().getAsDouble());

        FileOutputStream fos = new FileOutputStream("gson_qunata_test.dat");
        for (int i = 0; i < 200; i++) {
            fos.write((g_res.get(i)+"\t"+m_res.get(i)+"\t"+q_res.get(i)+"\n").getBytes());
        }
        fos.close();
    }


    List<Long> quantaTestResult(TestObject data, int tries) {
        BasicSerializer<TestObject> mapper =  new BasicSerializer<>(TestObject.class);
        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < tries; i++) {
            arr.add(
                    SexyTimer.getMean(() -> {

                        byte[] bin = mapper.serialize(data);
                        TestObject t = mapper.deserialize(bin);

                    }, 1000).getDuration()
            );
        }
        return arr;
    }


    List<Long> gsonTestResult(TestObject data, int tries) {
        Gson g = new Gson();
        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < tries; i++) {
            arr.add(
                    SexyTimer.getMean(() -> {

                        String s = g.toJson(data);
                        TestObject t = g.fromJson(s, TestObject.class);

                    }, 1000).getDuration()
            );
        }
        return arr;
    }


    List<Long> messagePackTestResult(TestObject data, int tries) {
        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        List<Long> arr = new ArrayList<>();
        for (int i = 0; i < tries; i++) {
            arr.add(
                    SexyTimer.getMean(() -> {

                        try {
                            byte[] buff = objectMapper.writeValueAsBytes(data);
                            TestObject t = objectMapper.readValue(buff, TestObject.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }, 1000).getDuration()
            );
        }
        return arr;
    }

    Random r = new Random();

    private TestObject randomTestObject() {
        TestObject ans = new TestObject();
        ans.i = (int) (Math.random() * Integer.MAX_VALUE);
        ans.j = (int) (-Math.random() * Integer.MAX_VALUE);
        ans.k = (byte) r.nextInt();
        ans.someData = new byte[200];
        r.nextBytes(ans.someData);
        ans.s = "QuantaIsFast!"; // not random but hey!
        ans.aDataTable = "i love efficiency".split(" ");

        return ans;

    }


}
