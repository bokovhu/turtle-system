package me.bokov.turtlesystem.v2;

import java.awt.*;

public interface TurtleCommand {

    void execute (TurtleContext context);

    static TurtleCommand FORWARD (Expression<Double> expr) {
        return context -> context.turtle ().controller ().forward (expr.evaluate (context));
    }
    static TurtleCommand BACKWARD (Expression<Double> expr) {
        return context -> context.turtle ().controller ().backward (expr.evaluate (context));
    }
    static TurtleCommand LEFT (Expression<Double> expr) {
        return context -> context.turtle ().controller ().left (expr.evaluate (context));
    }
    static TurtleCommand RIGHT (Expression<Double> expr) {
        return context -> context.turtle ().controller ().right (expr.evaluate (context));
    }
    static TurtleCommand COLOR (Expression<Color> expr) {
        return context -> context.turtle ().controller ().color (expr.evaluate (context));
    }
    static TurtleCommand LINE_WIDTH (Expression<Double> expr) {
        return context -> context.turtle ().controller ().lineWidth (expr.evaluate (context));
    }
    static TurtleCommand UP () {
        return context -> context.turtle ().controller ().up ();
    }
    static TurtleCommand DOWN () {
        return context -> context.turtle ().controller ().down ();
    }
    static TurtleCommand PUSH () {
        return context -> context.turtle ().controller ().push ();
    }
    static TurtleCommand POP () {
        return context -> context.turtle ().controller ().pop ();
    }
    static TurtleCommand ROTATE (Expression<Double> expr) {
        return context -> context.turtle ().controller ().rotate (expr.evaluate (context));
    }

}
