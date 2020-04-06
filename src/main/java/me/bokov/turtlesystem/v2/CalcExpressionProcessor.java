package me.bokov.turtlesystem.v2;

import me.bokov.tslang.TurtleSystemBaseListener;
import me.bokov.tslang.TurtleSystemParser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CalcExpressionProcessor extends TurtleSystemBaseListener {

    private boolean entered = false;

    private Deque<Expression<Double>> expressionStack = new ArrayDeque<> ();

    @Override
    public void exitBinaryExpression (TurtleSystemParser.BinaryExpressionContext ctx) {
        super.exitBinaryExpression (ctx);

        Expression<Double> rhs = expressionStack.removeLast ();
        Expression<Double> lhs = expressionStack.removeLast ();

        Expression<Double> binaryExpression = null;
        switch (ctx.OPERATOR ().getText ()) {
            case "+":
                binaryExpression = Expression.ADD_DECIMAL (
                        lhs, rhs
                );
                break;
            case "-":
                binaryExpression = Expression.SUB_DECIMAL (
                        lhs, rhs
                );
                break;
            case "*":
                binaryExpression = Expression.MUL_DECIMAL (
                        lhs, rhs
                );
                break;
            default:
                binaryExpression = Expression.DIV_DECIMAL (
                        lhs, rhs
                );
                break;
        }

        expressionStack.addLast (binaryExpression);

    }

    @Override
    public void exitLiteralExpression (TurtleSystemParser.LiteralExpressionContext ctx) {
        super.exitLiteralExpression (ctx);

        expressionStack.addLast (
                Expression.LITERAL (
                        Double.parseDouble (ctx.DECIMAL ().getText ())
                )
        );

    }

    @Override
    public void exitVariableExpression (TurtleSystemParser.VariableExpressionContext ctx) {
        super.exitVariableExpression (ctx);

        expressionStack.addLast (
                Expression.VARIABLE (
                        ctx.IDENTIFIER ().getText (),
                        Double.class
                )
        );

    }

    @Override
    public void exitFunctionCallExpression (TurtleSystemParser.FunctionCallExpressionContext ctx) {
        super.exitFunctionCallExpression (ctx);

        int numArguments = ctx.function_call_expression ()
                .function_call_parameter_list ()
                .calc_expression ()
                .size ();

        List<Expression<?>> arguments = new ArrayList<> ();
        for (int i = 0; i < numArguments; i++) {
            arguments.add (0, expressionStack.removeLast ());
        }

        expressionStack.addLast (
                Expression.FUNCTION_CALL (
                        ctx.function_call_expression ().IDENTIFIER ().getText (),
                        Double.class,
                        arguments
                )
        );

    }

    @Override
    public void exitUnaryExpression (TurtleSystemParser.UnaryExpressionContext ctx) {
        super.exitUnaryExpression (ctx);

        Expression<Double> expression = expressionStack.removeLast ();
        expressionStack.addLast (
                Expression.UNARY_MINUS_DECIMAL (expression)
        );

    }

    public Expression<Double> toExpression () {
        return expressionStack.getLast ();
    }

}
