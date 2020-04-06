package me.bokov.turtlesystem.v2;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public interface Turtle {

    TurtleState state ();

    TurtleController controller ();

    default TurtleState update () {

        final TurtleState current = this.state ();

        return new TurtleState (
                current.getX (), current.getY (),
                current.getRotation (),
                Math.min (current.getX (), current.getMinX ()),
                Math.min (current.getY (), current.getMinY ()),
                Math.max (current.getX (), current.getMaxX ()),
                Math.max (current.getY (), current.getMaxY ()),
                current.getLineWidth (),
                new Color (current.getColor ().getRGB ()),
                current.isDrawing (),
                new ArrayDeque<> (current.getStack ()),
                new ArrayList<> (current.getDrawnLines ())
        );

    }

    default TurtleState stateAfterMovement (double dx, double dy) {

        final TurtleState current = this.state ();
        double newX = current.getX () + dx;
        double newY = current.getY () + dy;

        List<DrawnLine> newDrawnLines = new ArrayList<> (current.getDrawnLines ());
        if (current.isDrawing ()) {
            newDrawnLines.add (
                    new DrawnLine (
                            current.getX (), current.getY (),
                            newX, newY,
                            current.getColor (),
                            current.getLineWidth ()
                    )
            );
        }

        return new TurtleState (
                newX, newY,
                current.getRotation (),
                Math.min (newX, current.getMinX ()),
                Math.min (newY, current.getMinY ()),
                Math.max (newX, current.getMaxX ()),
                Math.max (newY, current.getMaxY ()),
                current.getLineWidth (),
                new Color (current.getColor ().getRGB ()),
                current.isDrawing (),
                new ArrayDeque<> (current.getStack ()),
                newDrawnLines
        );

    }

    default TurtleState stateAfterRotation (double deltaDegrees) {

        final TurtleState current = this.state ();

        return new TurtleState (
                current.getX (), current.getY (),
                current.getRotation () + deltaDegrees,
                current.getMinX (), current.getMinY (),
                current.getMaxX (), current.getMaxY (),
                current.getLineWidth (),
                new Color (current.getColor ().getRGB ()),
                current.isDrawing (),
                new ArrayDeque<> (current.getStack ()),
                new ArrayList<> (current.getDrawnLines ())
        );

    }

    default TurtleState stateAfterPush () {

        final TurtleState current = this.state ();
        Deque<TurtleState> newStack = new ArrayDeque<> (current.getStack ());
        newStack.addLast (current);

        return new TurtleState (
                current.getX (), current.getY (),
                current.getRotation (),
                current.getMinX (), current.getMinY (),
                current.getMaxX (), current.getMaxY (),
                current.getLineWidth (),
                new Color (current.getColor ().getRGB ()),
                current.isDrawing (),
                newStack,
                new ArrayList<> (current.getDrawnLines ())
        );

    }

    default TurtleState stateAfterPop () {

        final TurtleState current = this.state ();
        TurtleState previous = current;
        Deque<TurtleState> newStack = new ArrayDeque<> (current.getStack ());
        if (!newStack.isEmpty ()) {
            previous = newStack.removeLast ();
        }

        return new TurtleState (
                previous.getX (), previous.getY (),
                previous.getRotation (),
                current.getMinX (), current.getMinY (),
                current.getMaxX (), current.getMaxY (),
                previous.getLineWidth (),
                new Color (previous.getColor ().getRGB ()),
                previous.isDrawing (),
                newStack,
                new ArrayList<> (current.getDrawnLines ())
        );

    }

    default TurtleState stateAfterDrawParametersChanged (double newLineWidth, Color newColor, boolean newIsDrawing) {

        final TurtleState current = this.state ();

        return new TurtleState (
                current.getX (), current.getY (),
                current.getRotation (),
                current.getMinX (), current.getMinY (),
                current.getMaxX (), current.getMaxY (),
                newLineWidth,
                new Color (newColor.getRGB ()),
                newIsDrawing,
                new ArrayDeque<> (current.getStack ()),
                new ArrayList<> (current.getDrawnLines ())
        );

    }

}
