package me.bokov.turtlesystem.v2;

import java.util.List;

public interface LSystem {

    List<Rule> getRules ();
    List<Symbol> getSymbols ();
    List<String> getStartingState ();
    List<String> getCurrentState ();
    LSystem iterate ();

}
