package me.bokov.turtlesystem;

import me.bokov.turtlesystem.v2.LSystem;
import me.bokov.turtlesystem.v2.Rule;
import me.bokov.turtlesystem.v2.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BaseLSystem implements LSystem {

    private final List<Rule> rules;
    private final List<Symbol> symbols;
    private final List<String> startingState;
    private final List<String> currentState;

    public BaseLSystem (
            List<Rule> rules,
            List<Symbol> symbols,
            List<String> startingState,
            List<String> currentState
    ) {
        this.rules = new ArrayList<> (rules);
        this.symbols = new ArrayList<> (symbols);
        this.startingState = new ArrayList<> (startingState);
        this.currentState = new ArrayList<> (currentState);
    }

    @Override
    public List<Rule> getRules () {
        return Collections.unmodifiableList (this.rules);
    }

    @Override
    public List<Symbol> getSymbols () {
        return Collections.unmodifiableList (this.symbols);
    }

    @Override
    public List<String> getStartingState () {
        return Collections.unmodifiableList (this.startingState);
    }

    @Override
    public List<String> getCurrentState () {
        return Collections.unmodifiableList (this.currentState);
    }

    @Override
    public LSystem iterate () {

        List<String> nextState = new ArrayList<> ();
        Random random = new Random ();

        for (String s : this.currentState) {

            List<Rule> applicableRules = rules.stream ()
                    .filter (r -> r.getSymbol ().equals (s))
                    .collect (Collectors.toList ());

            if (applicableRules.isEmpty ()) {
                nextState.add (s);
            } else if (applicableRules.size () == 1) {
                nextState.addAll (applicableRules.get (0).getResult ());
            } else {

                List<RuleProbabilityRange> ruleRanges = new ArrayList<> ();
                double lastRangeEnd = 0.0;
                for (Rule rule : applicableRules) {
                    ruleRanges.add (
                            new RuleProbabilityRange (
                                    lastRangeEnd,
                                    lastRangeEnd + rule.getProbability (),
                                    rule
                            )
                    );
                    lastRangeEnd += rule.getProbability ();
                }

                double randomValue = random.nextDouble () * lastRangeEnd;

                for (RuleProbabilityRange range : ruleRanges) {

                    if (range.rangeStart <= randomValue && range.rangeEnd >= randomValue) {
                        nextState.addAll (range.rule.getResult ());
                        break;
                    }

                }

            }

        }

        return new BaseLSystem (
                this.rules,
                this.symbols,
                this.startingState,
                nextState
        );
    }

    private static final class RuleProbabilityRange {

        private final double rangeStart, rangeEnd;
        private final Rule rule;

        private RuleProbabilityRange (double rangeStart, double rangeEnd, Rule rule) {
            this.rangeStart = rangeStart;
            this.rangeEnd = rangeEnd;
            this.rule = rule;
        }

    }

}
