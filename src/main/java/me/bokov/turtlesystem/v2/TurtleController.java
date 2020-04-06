package me.bokov.turtlesystem.v2;

import java.awt.*;

public interface TurtleController {

    void forward (double units);
    void backward (double units);
    void left (double units);
    void right (double units);
    void rotate (double degrees);
    void up ();
    void down ();
    void color (Color color);
    void lineWidth (double lineWidth);
    void push ();
    void pop ();

}
