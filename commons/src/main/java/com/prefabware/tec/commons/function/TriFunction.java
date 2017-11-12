package com.prefabware.tec.commons.function;

@FunctionalInterface
public interface TriFunction<T, U, R,S> {
 
    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @param s the third function argument
     * @return the function result
     */
    R apply(T t, U u, S s);
}