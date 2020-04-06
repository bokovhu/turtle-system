package me.bokov.turtlesystem.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BaseTurtleContext implements TurtleContext {

    private final Turtle turtle;
    private final Map<String, Function<TurtleContext, Expression<?>>> variableSuppliers = new HashMap<> ();
    private final Map<String, BiFunction<TurtleContext, List<Expression<?>>, Expression<?>>> functionSuppliers = new HashMap<> ();
    private final Random random = new Random ();

    public BaseTurtleContext (Turtle turtle) {
        this.turtle = turtle;

        addDefaults ();
    }

    private void addDefaults () {

        variableSuppliers.put (
                "stack_size",
                ctx -> context -> (double) context.turtle ().state ().getStack ().size ()
        );

        functionSuppliers.put (
                "min",
                (ctx, args) -> context -> Math.min (
                        (Double) args.get (0).evaluate (context),
                        (Double) args.get (1).evaluate (context)
                )
        );
        functionSuppliers.put (
                "max",
                (ctx, args) -> context -> Math.max (
                        (Double) args.get (0).evaluate (context),
                        (Double) args.get (1).evaluate (context)
                )
        );
        functionSuppliers.put (
                "abs",
                (ctx, args) -> context -> Math.abs ((Double) args.get (0).evaluate (context))
        );
        functionSuppliers.put (
                "random",
                (ctx, args) -> context -> {

                    if (args.size () == 0) {
                        return random.nextDouble ();
                    }
                    if (args.size () == 2) {

                        double min = (Double) args.get (0).evaluate (context);
                        double max = (Double) args.get (1).evaluate (context);
                        double rnd = random.nextDouble ();

                        return min + (max - min) * rnd;

                    }

                    return 1.0;

                }
        );

    }

    @Override
    public Turtle turtle () {
        return this.turtle;
    }

    @Override
    public <T> Expression<T> getVariable (String variableName, Class<T> variableTypeClass) {

        if (this.variableSuppliers.containsKey (variableName.toLowerCase ())) {
            return (Expression<T>) variableSuppliers.get (variableName.toLowerCase ())
                    .apply (this);
        }

        throw new IllegalArgumentException ("Unknown variable: " + variableName);

    }

    @Override
    public <T> Expression<T> getFunction (
            String functionName,
            Class<T> functionReturnTypeClass,
            List<Expression<?>> args
    ) {

        if (this.functionSuppliers.containsKey (functionName.toLowerCase ())) {
            BiFunction<TurtleContext, List<Expression<?>>, Expression<?>> fun = functionSuppliers.get (functionName);
            return (Expression<T>) fun.apply (this, args);
        }

        throw new IllegalArgumentException ("Unknown function: " + functionName);

    }

}
