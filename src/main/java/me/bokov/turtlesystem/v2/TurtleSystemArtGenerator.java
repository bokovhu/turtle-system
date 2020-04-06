package me.bokov.turtlesystem.v2;

import me.bokov.tslang.TurtleSystemLexer;
import me.bokov.tslang.TurtleSystemParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public final class TurtleSystemArtGenerator {

    private final GeneratorOptions options;
    private LSystem lSystem;
    private TurtleContext turtleContext;
    private Turtle turtle;
    private TurtleSystemProcessor turtleSystemProcessor;

    public TurtleSystemArtGenerator (GeneratorOptions options) {
        this.options = options;
    }

    private void loadLSystem () {

        File inputFile = new File (options.getInputFileName ());
        if (!inputFile.exists () || !inputFile.isFile ()) {
            throw new IllegalArgumentException ("Input file must be a valid existing file!");
        }

        try {

            CharStream charStream = CharStreams.fromPath (inputFile.toPath ());
            TurtleSystemLexer lexer = new TurtleSystemLexer (charStream);
            TokenStream tokenStream = new CommonTokenStream (lexer);
            TurtleSystemParser parser = new TurtleSystemParser (tokenStream);

            this.turtleSystemProcessor = new TurtleSystemProcessorImpl ();
            ParseTree parseTree = parser.parse ();
            ParseTreeWalker walker = new ParseTreeWalker ();
            walker.walk (this.turtleSystemProcessor, parseTree);

            this.lSystem = this.turtleSystemProcessor.toLSystem ();

        } catch (Exception exc) {
            throw new RuntimeException (exc);
        }

    }

    private void iterateLSystem () {

        for (int i = 0; i < options.getIterationCount (); i++) {

            this.lSystem = this.lSystem.iterate ();

        }

    }

    private void executeLSystem () {

        this.turtle = new BaseTurtle ();
        this.turtleContext = new BaseTurtleContext (this.turtle);

        for (String s : lSystem.getCurrentState ()) {

            for (Symbol symbol : lSystem.getSymbols ()) {

                if (symbol.getSymbol ().equals (s)) {
                    symbol.getCommands ()
                            .forEach (
                                    cmd -> cmd.execute (this.turtleContext)
                            );
                }

            }

        }

    }

    private void generateAndSaveResult () {

        executeLSystem ();

        final double padding = 8.0;

        final double correctedMinX = this.turtle.state ().getMinX () - padding;
        final double correctedMinY = this.turtle.state ().getMinY () - padding;
        final double correctedMaxX = this.turtle.state ().getMaxX () + padding;
        final double correctedMaxY = this.turtle.state ().getMaxY () + padding;

        Rectangle artBoundingRect = new Rectangle (
                (int) correctedMinX, (int) correctedMinY,
                (int) (correctedMaxX - correctedMinX),
                (int) (correctedMaxY - correctedMinY)
        );

        if (artBoundingRect.width >= 8192 || artBoundingRect.height >= 8192) {
            throw new IllegalStateException ("Too large output");
        }

        final BufferedImage outputImage = new BufferedImage (
                artBoundingRect.width, artBoundingRect.height,
                BufferedImage.TYPE_INT_ARGB
        );
        final Graphics2D outputGraphics = outputImage.createGraphics ();

        for (DrawnLine drawnLine : this.turtle.state ().getDrawnLines ()) {

            outputGraphics.setColor (drawnLine.getColor ());
            outputGraphics.setStroke (new BasicStroke ((float) drawnLine.getLineWidth ()));
            outputGraphics.drawLine (
                    (int) (drawnLine.getFromX () - artBoundingRect.x),
                    (int) (drawnLine.getFromY () - artBoundingRect.y),
                    (int) (drawnLine.getToX () - artBoundingRect.x),
                    (int) (drawnLine.getToY () - artBoundingRect.y)
            );

        }

        try {

            ImageIO.write (
                    outputImage,
                    "PNG",
                    new File (options.getOutputFileName ())
            );

        } catch (Exception exc) {
            throw new RuntimeException (exc);
        }

    }

    public void generateArt () {

        loadLSystem ();
        iterateLSystem ();
        generateAndSaveResult ();

    }

}
