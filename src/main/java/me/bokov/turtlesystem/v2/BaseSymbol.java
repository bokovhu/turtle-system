package me.bokov.turtlesystem.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseSymbol implements Symbol {

    private final String symbol;
    private final List<TurtleCommand> commands;

    public BaseSymbol (String symbol, List<TurtleCommand> commands) {
        this.symbol = symbol;
        this.commands = new ArrayList<> (commands);
    }

    @Override
    public String getSymbol () {
        return this.symbol;
    }

    @Override
    public List<TurtleCommand> getCommands () {
        return Collections.unmodifiableList (this.commands);
    }

}
