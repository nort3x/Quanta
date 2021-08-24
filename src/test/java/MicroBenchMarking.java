import TestObjects.TestObjectOfPrimitives;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.nort3x.quanta.pub.auto.PrimitiveConvertor;
import me.nort3x.quanta.pub.auto.NestedConvertor;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.IOException;

public class MicroBenchMarking {
    public static class BenchMarkTime {

        PrimitiveConvertor<TestObjectOfPrimitives> quanta;
        NestedConvertor<TestObjectOfPrimitives> quanta2;

        TestObjectOfPrimitives randomObject;
        Gson gson;
        ObjectMapper messagepk;

        byte[] quantaSerialized, msgpkSerialized,quantaSerialized2;
        String gsonSerialized;


        @BeforeExperiment
        protected void setUp() throws JsonProcessingException {
            randomObject = TestObjectOfPrimitives.randomTestObject();


            quanta = new PrimitiveConvertor<>(TestObjectOfPrimitives.class);
            quanta2 = new NestedConvertor<>(TestObjectOfPrimitives.class);

            gson = new GsonBuilder().create();

            messagepk = new ObjectMapper(new MessagePackFactory());
            messagepk.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

            gsonSerialized = gson.toJson(randomObject);
            msgpkSerialized = messagepk.writeValueAsBytes(randomObject);
            quantaSerialized = quanta.serialize(randomObject);
            quantaSerialized2 = quanta2.serialize(randomObject);

        }

        @Benchmark
        public void quanta2Serialize(int reps) {
            for (int i = 0; i < reps; i++)
                quantaSerialized = quanta2.serialize(randomObject);
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
                gson.fromJson(gsonSerialized, TestObjectOfPrimitives.class);
        }

        @Benchmark
        public void quanta2Deserialize(int reps) {
            for (int i = 0; i < reps; i++)
                quanta2.deserialize(quantaSerialized2);
        }

        @Benchmark
        public void quantaDeserialize(int reps) {
            for (int i = 0; i < reps; i++)
                quanta.deserialize(quantaSerialized);
        }

        @Benchmark
        public void messagePackDeserialize(int reps) throws IOException {

            for (int i = 0; i < reps; i++)
                messagepk.readValue(msgpkSerialized, TestObjectOfPrimitives.class);

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
