package io.dsco.stream.command;

public interface Command<P, R>
{
    R  execute(P param) throws Exception;
}
