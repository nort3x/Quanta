package nortex.quanta.legacy_support;

import java.util.Objects;

public interface BiConsumer<T,U> {
    void accept(T var1, U var2);
    default java.util.function.BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> var1) {
        Objects.requireNonNull(var1);
        return (var2, var3) -> {
            this.accept(var2, var3);
            var1.accept(var2, var3);
        };
    }
}
