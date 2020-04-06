package me.bokov.turtlesystem.v2;

public interface VariableSupplier <T> {

    Expression <T> supplyVariable (TurtleContext context);

}
