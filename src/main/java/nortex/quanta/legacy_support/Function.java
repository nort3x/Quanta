package nortex.quanta.legacy_support;

import java.util.Objects;

public interface Function<Input, OutPut> {

    /**
     * functional eval
     *
     * @param input input value
     * @return functional output
     */
    OutPut apply(Input input);


    default <V> Function<V, OutPut> compose(Function<? super V, ? extends Input> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }


    default <V> Function<Input, V> andThen(Function<? super OutPut, ? extends V> after) {
        Objects.requireNonNull(after);
        return (Input t) -> after.apply(apply(t));
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }

}
