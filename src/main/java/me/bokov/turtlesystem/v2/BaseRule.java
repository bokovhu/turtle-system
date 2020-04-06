package me.bokov.turtlesystem.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseRule implements Rule {

    private final String symbol;
    private final List<String> result;
    private final Double probability;

    public BaseRule (String symbol, List<String> result, Double probability) {
        this.symbol = symbol;
        this.result = new ArrayList<> (result);
        this.probability = probability;
    }

    @Override
    public String getSymbol () {
        return this.symbol;
    }

    @Override
    public List<String> getResult () {
        return Collections.unmodifiableList (this.result);
    }

    @Override
    public Double getProbability () {
        return this.probability;
    }

}
