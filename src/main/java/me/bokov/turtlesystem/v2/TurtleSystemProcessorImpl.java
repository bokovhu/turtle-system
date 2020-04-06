package me.bokov.turtlesystem.v2;

import me.bokov.tslang.TurtleSystemBaseListener;
import me.bokov.tslang.TurtleSystemParser;
import me.bokov.turtlesystem.BaseLSystem;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class TurtleSystemProcessorImpl extends TurtleSystemBaseListener implements TurtleSystemProcessor {

    private List<String> startState = new ArrayList<> ();
    private List<Rule> rules = new ArrayList<> ();
    private List<Symbol> symbols = new ArrayList<> ();

    private List<TurtleCommand> turtleCommandBuffer = new ArrayList<> ();

    @Override
    public void exitStart_expression (TurtleSystemParser.Start_expressionContext ctx) {
        super.exitStart_expression (ctx);

        startState.addAll (
                ctx.symbol_name_list ()
                        .IDENTIFIER ()
                        .stream ().map (idNode -> idNode.getText ().strip ())
                        .collect (Collectors.toList ())
        );

    }

    @Override
    public void exitSymbol_expression (TurtleSystemParser.Symbol_expressionContext ctx) {
        super.exitSymbol_expression (ctx);

        final String symbolValue = ctx.IDENTIFIER ().getText ().strip ();

        if (symbols.stream ().anyMatch (s -> s.getSymbol ().equals (symbolValue))) {
            throw new IllegalStateException ("Symbol already defined: " + symbolValue);
        }

        this.symbols.add (
                new BaseSymbol (symbolValue, turtleCommandBuffer)
        );
        turtleCommandBuffer.clear ();

    }

    @Override
    public void exitRule_expression (TurtleSystemParser.Rule_expressionContext ctx) {
        super.exitRule_expression (ctx);

        final String symbolValue = ctx.IDENTIFIER ().getText ().strip ();
        final Double probability = (
                ctx.probability () != null && ctx.probability ().DECIMAL () != null
                        ? Double.parseDouble (ctx.probability ().DECIMAL ().getText ())
                        : 1.0
        );

        this.rules.add (
                new BaseRule (
                        symbolValue,
                        ctx.symbol_name_list ().IDENTIFIER ()
                                .stream ().map (id -> id.getText ().strip ())
                                .collect (Collectors.toList ()),
                        probability
                )
        );

    }

    private Expression<Double> getExpression (TurtleSystemParser.Calc_expressionContext ctx) {
        ParseTreeWalker walker = new ParseTreeWalker ();
        CalcExpressionProcessor processor = new CalcExpressionProcessor ();
        walker.walk (processor, ctx);

        Expression<Double> expr = processor.toExpression ();
        return expr;
    }

    @Override
    public void exitTurtle_forward (TurtleSystemParser.Turtle_forwardContext ctx) {
        super.exitTurtle_forward (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.FORWARD (
                        getExpression (ctx.calc_expression ())
                )
        );

    }

    @Override
    public void exitTurtle_backward (TurtleSystemParser.Turtle_backwardContext ctx) {
        super.exitTurtle_backward (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.BACKWARD (
                        getExpression (ctx.calc_expression ())
                )
        );
    }

    @Override
    public void exitTurtle_left (TurtleSystemParser.Turtle_leftContext ctx) {
        super.exitTurtle_left (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.LEFT (
                        getExpression (ctx.calc_expression ())
                )
        );
    }

    @Override
    public void exitTurtle_right (TurtleSystemParser.Turtle_rightContext ctx) {
        super.exitTurtle_right (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.RIGHT (
                        getExpression (ctx.calc_expression ())
                )
        );
    }

    @Override
    public void exitTurtle_rotate (TurtleSystemParser.Turtle_rotateContext ctx) {
        super.exitTurtle_rotate (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.ROTATE (
                        getExpression (ctx.calc_expression ())
                )
        );
    }

    @Override
    public void exitTurtle_push (TurtleSystemParser.Turtle_pushContext ctx) {
        super.exitTurtle_push (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.PUSH ()
        );
    }

    @Override
    public void exitTurtle_pop (TurtleSystemParser.Turtle_popContext ctx) {
        super.exitTurtle_pop (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.POP ()
        );
    }

    @Override
    public void exitTurtle_up (TurtleSystemParser.Turtle_upContext ctx) {
        super.exitTurtle_up (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.UP ()
        );
    }

    @Override
    public void exitTurtle_down (TurtleSystemParser.Turtle_downContext ctx) {
        super.exitTurtle_down (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.DOWN ()
        );
    }

    @Override
    public void exitTurtle_width (TurtleSystemParser.Turtle_widthContext ctx) {
        super.exitTurtle_width (ctx);

        turtleCommandBuffer.add (
                TurtleCommand.LINE_WIDTH (
                        getExpression (ctx.calc_expression ())
                )
        );
    }

    @Override
    public LSystem toLSystem () {
        return new BaseLSystem (
                this.rules,
                this.symbols,
                this.startState,
                new ArrayList<> (this.startState)
        );
    }

}
