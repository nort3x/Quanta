import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nortex.quanta.serialize.auto.BasicSerializer;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;
import java.io.PrintStream;

public class MicroBenchMarking {
    public static class BenchMarkTime {

        BasicSerializer<TestObject> quanta;
        TestObject randomObject;
        Gson gson;
        ObjectMapper messagepk;

        byte[] quantaSerialized, msgpkSerialized;
        String gsonSerialized;


        @BeforeExperiment
        protected void setUp() throws JsonProcessingException {
            randomObject = TestObject.randomTestObject();



            quanta = new BasicSerializer<>(TestObject.class);

            gson = new GsonBuilder().create();

            messagepk = new ObjectMapper(new MessagePackFactory());
            messagepk.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

            gsonSerialized = gson.toJson(randomObject);
            msgpkSerialized = messagepk.writeValueAsBytes(randomObject);
            quantaSerialized = quanta.serialize(randomObject);


        }

        @Benchmark
        public void quantaSerialize(int reps) {
            for (int i = 0; i < reps; i++)
                quantaSerialized = quanta.serialize(randomObject);
        }

        @Benchmark
        public void messagePackSerialize(int reps) throws JsonProcessingException {

            for (int i = 0; i < reps; i++)
                msgpkSerialized = messagepk.writeValueAsBytes(randomObject);

        }


        @Benchmark
        public void gsonDeserialize(int reps) {
            for (int i = 0; i < reps; i++)
                gson.fromJson(gsonSerialized,TestObject.class);
        }

        @Benchmark
        public void quantaDeserialize(int reps) {
            for (int i = 0; i < reps; i++)
                quanta.deserialize(quantaSerialized);
        }

        @Benchmark
        public void messagePackDeserialize(int reps) throws IOException {

            for (int i = 0; i < reps; i++)
                messagepk.readValue(msgpkSerialized,TestObject.class);

        }


        @Benchmark
        public void gsonSerialize(int reps) {
            for (int i = 0; i < reps; i++)
                gsonSerialized = gson.toJson(randomObject);
        }

    }



    public static void main(String[] args) {
        CaliperMain.main(BenchMarkTime.class, args);
    }
}
