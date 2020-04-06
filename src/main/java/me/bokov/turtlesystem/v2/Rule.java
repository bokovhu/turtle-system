package me.bokov.turtlesystem.v2;

import java.util.List;

public interface Rule {

    String getSymbol ();
    List<String> getResult ();
    Double getProbability ();

}
