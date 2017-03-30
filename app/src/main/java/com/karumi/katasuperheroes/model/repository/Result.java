package com.karumi.katasuperheroes.model.repository;

/**
 * Created by josealbertomartinfalcon on 18/3/17.
 */

public class Result<T, Q> {

    private final T value;
    private final Q error;

    public Result(T value, Q error) {
        this.value = value;
        this.error = error;
    }

    public static <T, Q> Result<T, Q> success(T value) {
        return new Result<>(value, null);
    }

    public static <T, Q> Result<T, Q> error(Q error) {
        return new Result<>(null, error);
    }

    public T getValue() {
        return value;
    }

    public Q getError() {
        return error;
    }

    public enum SuperHeroError {
        InvalidSession,
        NetworkError;
    }
}
