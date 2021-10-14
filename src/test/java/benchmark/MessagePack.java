package benchmark;

import benchmark.objects.SimpleTestObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.caliper.model.InstrumentType;
import com.google.caliper.worker.instrument.InstrumentTypeKey;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 5, timeUnit = TimeUnit.SECONDS)
@InstrumentTypeKey(InstrumentType.ALLOCATION_MICRO)
public class MessagePack {


    ObjectMapper objectMapper;
    byte[] sered;
    public MessagePack(){
        objectMapper = new ObjectMapper(new MessagePackFactory());
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            sered = objectMapper.writeValueAsBytes(SimpleTestObject.getOne());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @Benchmark
    @com.google.caliper.Benchmark
    @Fork(1)
    public void messagePackSerialization() throws JsonProcessingException {
        byte[] arr = objectMapper.writeValueAsBytes(SimpleTestObject.getOne());
    }

    @Benchmark
    @com.google.caliper.Benchmark
    @Fork(1)
    public void messagePackDeserialization() throws IOException {
        SimpleTestObject simpleTestObject = objectMapper.readValue(sered,SimpleTestObject.class);
    }
}
