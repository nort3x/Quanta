package me.nort3x.quanta.pub.config;

import me.nort3x.quanta.legacy_support.Function;

import java.lang.reflect.Field;

public class QuantaConfiguration {

    boolean replaceNullWithEmpty = true;
    boolean throwExceptionOnNullValue = false;
    boolean logEachStep = false;

    boolean ignoreStaticFields = true;
    boolean ignoreSyntheticFields = true;
    boolean ignoreTransientFields = true;

    Function<Field,Boolean> customFieldFilter = null;


    public static QuantaConfiguration getDefault() {
        return QUNATA_CONFIGURATION_DEFAULT;
    }


    public QuantaConfiguration setLogEachStep(boolean logEachStep) {
        this.logEachStep = logEachStep;
        return this;
    }


    public Function<Field, Boolean> getCustomFieldFilter() {
        return customFieldFilter;
    }

    public void setIgnoreTransientFields(boolean ignoreTransientFields) {
        this.ignoreTransientFields = ignoreTransientFields;
    }

    public QuantaConfiguration setReplaceNullWithEmpty(boolean replaceNullWithEmpty) {
        this.replaceNullWithEmpty = replaceNullWithEmpty;
        return this;
    }

    public QuantaConfiguration setThrowExceptionOnNullValue(boolean throwExceptionOnNullValue) {
        this.throwExceptionOnNullValue = throwExceptionOnNullValue;
        return this;
    }

    public QuantaConfiguration setIgnoreStaticFields(boolean ignoreStaticFields) {
        this.ignoreStaticFields = ignoreStaticFields;
        return this;
    }

    public QuantaConfiguration setIgnoreSyntheticFields(boolean ignoreSyntheticFields) {
        this.ignoreSyntheticFields = ignoreSyntheticFields;
        return this;
    }


    public void setCustomFieldFilter(Function<Field, Boolean> customFieldFilter) {
        this.customFieldFilter = customFieldFilter;
    }

    public boolean isThrowExceptionOnNullValue() {
        return throwExceptionOnNullValue;
    }

    public boolean isReplaceNullWithEmpty() {
        return replaceNullWithEmpty;
    }

    public boolean isLogEachStep() {
        return logEachStep;
    }

    public boolean isIgnoreStaticFields() {
        return ignoreStaticFields;
    }

    public boolean isIgnoreSyntheticFields() {
        return ignoreSyntheticFields;
    }

    private static final QuantaConfiguration QUNATA_CONFIGURATION_DEFAULT = new QuantaConfiguration();

    public boolean isIgnoreTransientFields() {
        return ignoreTransientFields;
    }
}
