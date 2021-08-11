package nortex.quanta.utils;

import java.util.concurrent.TimeUnit;

/**
 * tinny class for measuring durations
 * not synchronized for better resolution be sure to dont concurrent it
 */
public class SexyTimer {
    long nano;

    public SexyTimer() {
        nano = System.nanoTime();
    }

    /**
     * attach a name for better toString
     */
    String name = null;

    public SexyTimer(String name) {
        this.name = name;
        nano = System.nanoTime();
    }

    /**
     * will return passed time from last reset or construction or calling this method and reset it
     *
     * @return duration in nanos
     * @see SexyTimer#weakGetDuration() for better resolution
     */
    public long getDuration() {
        long nanoTime = System.nanoTime();
        nanoTime -= nano;
        reset();
        return (long) (nanoTime / factor);
    }

    /**
     * will just dump duration from last reset without resetting it
     *
     * @return duration in nanos
     */
    public long weakGetDuration() {
        return (long) ((System.nanoTime() - nano) / factor);
    }

    /**
     * will reset the internal chronometer
     */
    public void reset() {
        nano = System.nanoTime();
    }

    /**
     * print this for satisfaction
     *
     * @return beauty
     */
    @Override
    public String toString() {
        long w = weakGetDuration();

        long sec = TimeUnit.SECONDS.convert(w, TimeUnit.NANOSECONDS);
        long millis = TimeUnit.MILLISECONDS.convert(w - sec * 1000000000, TimeUnit.NANOSECONDS);
        long micros = TimeUnit.MICROSECONDS.convert(w - sec * 1000000000 - millis * 1000000, TimeUnit.NANOSECONDS);
        w -= sec * 1000000000 + millis * 1000000 + micros * 1000;

        String s = (name == null ? "[timer]" : "[" + name + "]") + " ->  seconds: " + sec + " millis: " + millis + " micros: " + micros + " nanos: " + w;
        reset();
        return s;
    }

    /**
     * @param runnable     runnable to measure
     * @param numberOfRuns iterations for sample
     * @return mean duration for each run
     */
    public static SexyTimer getMean(Runnable runnable, int numberOfRuns) {
        SexyTimer sexyTimer = new SexyTimer("MeanMeasure");
        for (int i = 0; i < numberOfRuns; i++) {
            runnable.run();
        }
        sexyTimer.factor = numberOfRuns;
        return sexyTimer;
    }

    private double factor = 1;
}
