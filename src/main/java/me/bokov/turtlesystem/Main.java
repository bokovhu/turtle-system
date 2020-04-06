package me.bokov.turtlesystem;

import me.bokov.turtlesystem.v2.GeneratorOptions;
import me.bokov.turtlesystem.v2.TurtleSystemArtGenerator;
import picocli.CommandLine;

public class Main {

    public static void main (String[] args) {

        TurtleSystemOptions options = new TurtleSystemOptions ();
        CommandLine commandLine = new CommandLine (options);
        commandLine.parseArgs (args);

        GeneratorOptions generatorOptions = new GeneratorOptions ();
        generatorOptions.setInputFileName (options.inputFile);
        generatorOptions.setOutputFileName (options.outputFile);
        generatorOptions.setIterationCount (options.iterationCount);

        TurtleSystemArtGenerator artGenerator = new TurtleSystemArtGenerator (generatorOptions);
        artGenerator.generateArt ();

    }

}
