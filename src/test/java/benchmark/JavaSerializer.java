package benchmark;

import benchmark.objects.ComplexTestObject;
import benchmark.objects.SimpleTestObject;
import com.google.caliper.model.InstrumentType;
import com.google.caliper.worker.instrument.InstrumentTypeKey;
import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 5, timeUnit = TimeUnit.SECONDS)
@InstrumentTypeKey(InstrumentType.ALLOCATION_MICRO)
public class JavaSerializer {
    byte[] simpleSered;
    byte[] complexSered;


    public JavaSerializer(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
            objectOutputStream.writeObject(SimpleTestObject.getOne());
            simpleSered = bos.toByteArray();
            bos.reset();
            objectOutputStream.writeObject(ComplexTestObject.getOne());
            complexSered = bos.toByteArray();

            System.out.println("JavaSerializedSimpleObjectSize: " + simpleSered.length);
            System.out.println("JavaSerializedComplexObjectSize: " + complexSered.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Benchmark
    @com.google.caliper.Benchmark
    @Fork(1)
    public void simpleObjectSerialization() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        objectOutputStream.writeObject(SimpleTestObject.getOne());
        byte[] arr =  bos.toByteArray();
    }

//    @Benchmark
//    @Fork(1)
    public void complexObjectSerialization() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        objectOutputStream.writeObject(ComplexTestObject.getOne());
        byte[] arr =  bos.toByteArray();
    }

    @Benchmark
    @com.google.caliper.Benchmark
    @Fork(1)
    public void simpleObjectDeserialization() throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(simpleSered);
        ObjectInputStream objectInputStream = new ObjectInputStream(bis);
        SimpleTestObject simpleTestObject = (SimpleTestObject) objectInputStream.readObject();
    }

//    @Benchmark
//    @Fork(1)
    public void complexObjectDeserialization() throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(complexSered);
        ObjectInputStream objectInputStream = new ObjectInputStream(bis);
        ComplexTestObject complexTestObject = (ComplexTestObject) objectInputStream.readObject();
    }

}
