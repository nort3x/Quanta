package me.nort3x.quanta.legacy_support;

import java.util.Objects;

public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);

}
