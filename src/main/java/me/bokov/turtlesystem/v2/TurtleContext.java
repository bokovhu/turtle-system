package me.bokov.turtlesystem.v2;

import java.util.List;

public interface TurtleContext {

    Turtle turtle ();
    <T> Expression <T> getVariable (String variableName, Class<T> variableTypeClass);
    <T> Expression <T> getFunction (String functionName, Class<T> functionReturnTypeClass, List<Expression<?>> args);

}
