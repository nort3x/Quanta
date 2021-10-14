package me.nort3x.quanta.pub.basic;

public class DeserializationConfig {

    boolean replaceNullWithEmpty = true;
    boolean throwExceptionOnNullValue = false;
    boolean logEachStep = false;

    private static final DeserializationConfig deserializationConfigDefault = new DeserializationConfig();

    public static DeserializationConfig getDefault() {
        return deserializationConfigDefault;
    }


    public boolean isReplaceNullWithEmpty() {
        return replaceNullWithEmpty;
    }

    public void setReplaceNullWithEmpty(boolean replaceNullWithEmpty) {
        this.replaceNullWithEmpty = replaceNullWithEmpty;
    }

    public boolean isThrowExceptionOnNullValue() {
        return throwExceptionOnNullValue;
    }

    public void setThrowExceptionOnNullValue(boolean throwExceptionOnNullValue) {
        this.throwExceptionOnNullValue = throwExceptionOnNullValue;
    }

    public boolean isLogEachStep() {
        return logEachStep;
    }

    public void setLogEachStep(boolean logEachStep) {
        this.logEachStep = logEachStep;
    }
}
