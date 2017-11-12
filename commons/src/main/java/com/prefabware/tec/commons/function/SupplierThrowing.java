package com.prefabware.tec.commons.function;

@FunctionalInterface
public interface SupplierThrowing<T> {
	T get() throws Exception;
}