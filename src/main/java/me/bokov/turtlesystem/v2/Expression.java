package me.bokov.turtlesystem.v2;

import java.util.List;

public interface Expression<T> {

    static <T> Expression<T> LITERAL (T value) {
        return context -> value;
    }

    static Expression<Double> UNARY_MINUS_DECIMAL (Expression<Double> expr) {
        return context -> -1 * expr.evaluate (context);
    }

    static Expression<Double> ADD_DECIMAL (Expression<Double> a, Expression<Double> b) {
        return context -> a.evaluate (context) + b.evaluate (context);
    }

    static Expression<Double> SUB_DECIMAL (Expression<Double> a, Expression<Double> b) {
        return context -> a.evaluate (context) - b.evaluate (context);
    }

    static Expression<Double> MUL_DECIMAL (Expression<Double> a, Expression<Double> b) {
        return context -> a.evaluate (context) * b.evaluate (context);
    }

    static Expression<Double> DIV_DECIMAL (Expression<Double> a, Expression<Double> b) {
        return context -> a.evaluate (context) / b.evaluate (context);
    }

    static <T> Expression <T> FUNCTION_CALL (String functionName, Class <T> type, List<Expression<?>> args) {
        return context -> context.getFunction (functionName, type, args).evaluate (context);
    }

    static <T> Expression <T> VARIABLE (String variableName, Class <T> type) {
        return context -> context.getVariable (variableName, type).evaluate (context);
    }

    T evaluate (TurtleContext context);

}
