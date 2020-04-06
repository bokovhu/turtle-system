package me.bokov.turtlesystem.v2;

import lombok.Value;

import java.awt.*;
import java.util.Deque;
import java.util.List;

@Value
public class TurtleState {

    double x, y;
    double rotation;
    double minX, minY, maxX, maxY;
    double lineWidth;
    Color color;
    boolean isDrawing;

    Deque <TurtleState> stack;
    List<DrawnLine> drawnLines;

}
