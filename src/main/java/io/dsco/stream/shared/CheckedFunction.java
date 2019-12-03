package io.dsco.stream.shared;

/**
 * This extends the default java Function interface and allows it to throw an exception.
 */
//https://stackoverflow.com/questions/18198176/java-8-lambda-function-that-throws-exception
@FunctionalInterface
public interface CheckedFunction<T, R>
{
    R apply(T t) throws Exception;
}
