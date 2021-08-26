package benchmark;


import com.google.caliper.runner.*;
import com.google.caliper.runner.config.CaliperConfig;
import com.google.caliper.runner.config.CaliperConfigModule;
import com.google.caliper.runner.options.OptionsModule;
import com.google.caliper.util.OutputModule;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Quanta.class.getSimpleName())
                .include(JavaSerializer.class.getSimpleName())
                .include(ProtoBuf.class.getSimpleName())
                .include(MessagePack.class.getSimpleName())
                .build();

        new Runner(opt).run();

    }
}
