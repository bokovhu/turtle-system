package me.bokov.turtlesystem;

import lombok.Data;
import picocli.CommandLine;

public class TurtleSystemOptions {

    @CommandLine.Option (names = "-i")
    String inputFile;
    @CommandLine.Option (names = "-o")
    String outputFile;
    @CommandLine.Option (names = "-c")
    int iterationCount = 5;

}
