package benchmark;


import benchmark.objects.ProtoSimpleTestObject;
import com.google.caliper.model.InstrumentType;
import com.google.caliper.worker.instrument.InstrumentTypeKey;
import com.google.protobuf.InvalidProtocolBufferException;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;



@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 5, timeUnit = TimeUnit.SECONDS)
@InstrumentTypeKey(InstrumentType.ALLOCATION_MICRO)
public class ProtoBuf {
    byte[] simpleSered;
    byte[] complexSered;

    public ProtoBuf() {
        simpleSered = ProtoSimpleTestObject.SimpleObject.newBuilder()
                .setA(1)
                .setB(2)
                .addAllC(Arrays.asList("some,Strings,here,as,shown".split(",")))
                .addAllD(Arrays.asList(1f, 2f, 3f, 4f, 5f, 6f))
                .build().toByteArray();
    }

    @Benchmark
    @com.google.caliper.Benchmark
    @Fork(1)
    public void protobufSimpleObjectSerialization() {
        byte[] sered = ProtoSimpleTestObject.SimpleObject.newBuilder()
                .setA(1)
                .setB(2)
                .addAllC(Arrays.asList("some,Strings,here,as,shown".split(",")))
                .addAllD(Arrays.asList(1f, 2f, 3f, 4f, 5f, 6f))
                .build().toByteArray();
    }


    @Benchmark
    @com.google.caliper.Benchmark
    @Fork(1)
    public void protobufSimpleDeserialization() throws InvalidProtocolBufferException {
        ProtoSimpleTestObject.SimpleObject obj = ProtoSimpleTestObject.SimpleObject.parseFrom(simpleSered);
    }
}
