package com.prefabware.tec.commons.function;

@FunctionalInterface
public interface RunnableThrowing {
	void run() throws Exception;
}