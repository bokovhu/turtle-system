package me.bokov.turtlesystem.v2;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class BaseTurtle implements Turtle, TurtleController {

    private TurtleState state = new TurtleState (
            0.0, 0.0,
            0.0,
            0.0, 0.0, 0.0, 0.0,
            1.0,
            Color.BLACK,
            true,
            new ArrayDeque<> (),
            new ArrayList<> ()
    );

    @Override
    public TurtleState state () {
        return this.state;
    }

    @Override
    public TurtleController controller () {
        return this;
    }

    @Override
    public void forward (double units) {

        this.state = this.stateAfterMovement (
                Math.cos (Math.toRadians (this.state.getRotation ())) * units,
                Math.sin (Math.toRadians (this.state.getRotation ())) * units
        );

    }

    @Override
    public void backward (double units) {

        this.state = this.stateAfterMovement (
                Math.cos (Math.toRadians (this.state.getRotation () + 180.0)) * units,
                Math.sin (Math.toRadians (this.state.getRotation () + 180.0)) * units
        );

    }

    @Override
    public void left (double units) {

        this.state = this.stateAfterMovement (
                Math.cos (Math.toRadians (this.state.getRotation () + 90.0)) * units,
                Math.sin (Math.toRadians (this.state.getRotation () + 90.0)) * units
        );

    }

    @Override
    public void right (double units) {

        this.state = this.stateAfterMovement (
                Math.cos (Math.toRadians (this.state.getRotation () + 270.0)) * units,
                Math.sin (Math.toRadians (this.state.getRotation () + 270.0)) * units
        );

    }

    @Override
    public void rotate (double degrees) {

        this.state = this.stateAfterRotation (degrees);

    }

    @Override
    public void up () {

        this.state = this.stateAfterDrawParametersChanged (
                this.state.getLineWidth (),
                this.state.getColor (),
                false
        );

    }

    @Override
    public void down () {

        this.state = this.stateAfterDrawParametersChanged (
                this.state.getLineWidth (),
                this.state.getColor (),
                true
        );

    }

    @Override
    public void color (Color color) {

        this.state = this.stateAfterDrawParametersChanged (
                this.state.getLineWidth (),
                color,
                this.state.isDrawing ()
        );

    }

    @Override
    public void lineWidth (double lineWidth) {

        this.state = this.stateAfterDrawParametersChanged (
                lineWidth,
                this.state.getColor (),
                this.state.isDrawing ()
        );

    }

    @Override
    public void push () {

        this.state = this.stateAfterPush ();

    }

    @Override
    public void pop () {

        this.state = this.stateAfterPop ();

    }

}
