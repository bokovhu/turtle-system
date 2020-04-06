package me.bokov.turtlesystem.v2;

import lombok.Value;

import java.awt.*;

@Value
public class DrawnLine {

    double fromX, fromY;
    double toX, toY;
    Color color;
    double lineWidth;

}
