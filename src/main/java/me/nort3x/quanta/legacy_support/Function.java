package me.nort3x.quanta.legacy_support;

import java.util.Objects;

public interface Function<Input, OutPut> {

    /**
     * functional eval
     *
     * @param input input value
     * @return functional output
     */
    OutPut apply(Input input);

}
