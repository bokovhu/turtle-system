package me.bokov.turtlesystem.v2;

import me.bokov.tslang.TurtleSystemBaseListener;
import me.bokov.tslang.TurtleSystemListener;

public interface TurtleSystemProcessor extends TurtleSystemListener {

    LSystem toLSystem ();

}
