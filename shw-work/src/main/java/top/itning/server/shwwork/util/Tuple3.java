package top.itning.server.shwwork.util;

import reactor.util.annotation.NonNull;

import java.util.Objects;

/**
 * 包含三个对象的Tuple
 *
 *
 * @date 2019/4/30 21:47
 */
public class Tuple3<T1, T2, T3> {
    @NonNull
    final T1 t1;
    @NonNull
    final T2 t2;
    @NonNull
    final T3 t3;

    public Tuple3(T1 t1, T2 t2, T3 t3) {
        this.t1 = Objects.requireNonNull(t1, "t1");
        this.t2 = Objects.requireNonNull(t2, "t2");
        this.t3 = Objects.requireNonNull(t3, "t3");
    }

    public T1 getT1() {
        return t1;
    }

    public T2 getT2() {
        return t2;
    }

    public T3 getT3() {
        return t3;
    }
}
