package me.bokov.turtlesystem.v2;

import lombok.Data;

import java.io.Serializable;

@Data
public class GeneratorOptions implements Serializable {

    private String inputFileName;
    private String outputFileName;
    private int iterationCount;

}
