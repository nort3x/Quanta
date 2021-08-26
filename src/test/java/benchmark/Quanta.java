package benchmark;

import benchmark.objects.ComplexTestObject;
import benchmark.objects.SimpleTestObject;

import com.google.caliper.core.Running;
import com.google.caliper.model.InstrumentType;
import com.google.caliper.runner.instrument.AllocationInstrument;
import com.google.caliper.runner.instrument.InstrumentModule;
import com.google.caliper.worker.instrument.InstrumentTypeKey;
import com.google.caliper.worker.instrument.WorkerInstrument;
import me.nort3x.quanta.pub.auto.NestedConvertor;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 5, timeUnit = TimeUnit.SECONDS)
public class Quanta {
    NestedConvertor<ComplexTestObject> complexTestObjectNestedConvertor;
    NestedConvertor<SimpleTestObject> simpleTestObjectConvertor ;

    byte[] simpleSered;
    byte[] complexSered;

    public Quanta(){
        simpleTestObjectConvertor = new NestedConvertor<>(SimpleTestObject.class);
        complexTestObjectNestedConvertor = new NestedConvertor<>(ComplexTestObject.class);
        simpleSered = simpleTestObjectConvertor.serialize(SimpleTestObject.getOne());
        complexSered = complexTestObjectNestedConvertor.serialize(ComplexTestObject.getOne());

        System.out.println("QuantaSerializedSimpleObjectSize: "+simpleSered.length);
        System.out.println("QuantaSerializedComplexObjectSize: "+complexSered.length);
    }

    @Benchmark
    @com.google.caliper.Benchmark
    @Fork(1)

    public void simpleObjectSerialization(){
        byte[] arr = simpleTestObjectConvertor.serialize(SimpleTestObject.getOne());
    }

//    @Benchmark
//    @Fork(1)
    public void complexObjectSerialization(){
        byte[] arr = complexTestObjectNestedConvertor.serialize(ComplexTestObject.getOne());
    }

    @Benchmark
    @com.google.caliper.Benchmark
    @Fork(1)
    public void simpleObjectDeserialization(){
        SimpleTestObject simpleTestObject =  simpleTestObjectConvertor.deserialize(simpleSered);
    }

//    @Benchmark
//    @Fork(1)
    public void complexObjectDeserialization(){
        ComplexTestObject complexTestObject = complexTestObjectNestedConvertor.deserialize(complexSered);
    }
}
