package cs3500.pa01;

import cs3500.controller.Controller;
import cs3500.controller.ProblemController;
import cs3500.model.TestBank;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;

/*
@author Andre Agostinelli;
@version v1
@since 2015-09-22

 */

/**
 * This is the main driver of this project.
 * Aims to instantiate needed objects and call methods.
 */
public class Driver {
  /**
   * Project entry point.
   *
   * @param args - String[2] = args[0] String startingDir, args[1] String flag,
   *             args[2] String outputDir
   *             Path startingDir, Flag flag, Path outputDir.
   */
  public static void main(String[] args) {
    System.out.println("If less than 3 arguments are given then you"
        + " will enter spaced repetition mode");
    if (args.length == 0) {
      System.out.println("Welcome to the spaced repetition game!");
      System.out.println("Please enter a file path to an sr file: ");
      String filePath = System.in.toString();
      if (!(filePath.endsWith(".sr"))) {
        System.out.println("Not a valid study session file");
        throw new IllegalArgumentException("Not a valid study session file");
      } else {
        System.out.println("Please enter the number of questions you wish to study: ");
        int size = Integer.parseInt(System.in.toString());
        System.out.println("Once the session starts you may type '***EXIT' to exit");
        if (size < 0) {
          System.out.println("Not a valid number of question to study");
          throw new IllegalArgumentException("Not a valid number of question to study");
        } else {
          //create new testBank
          TestBank testBank = new TestBank(new ArrayList<>());
          TestBank mainBank = testBank.createTestBank(filePath, size);

          System.out.println("Game is starting:");


          Readable input = new InputStreamReader(System.in);
          Appendable output = new OutputStreamWriter(System.out);
          Controller controller = new ProblemController(input, output, mainBank);
          controller.run();
        }
      }

    } else if (args.length == 3) {
      Path startingDir = Path.of(args[0]);
      Flag f = switch (args[1]) {
        case "filename" -> Flag.FILENAME;
        case "modified" -> Flag.MODIFIED;
        case "created" -> Flag.CREATED;
        default -> throw new RuntimeException("String for sorting flag is invalid: " + args[1]);
      };

      Path srPath = Path.of(args[2].substring(0, args[2].length() - 3) + ".sr");
      Path outputDir = Path.of(args[2]);

      // pa02
      TestBank masterBank = new TestBank(new ArrayList<>());

      FileExplorer fileExp = new FileExplorer(new ArrayList<>(), masterBank);
      try {
        //walks the file tree
        Files.walkFileTree(startingDir, fileExp);
      } catch (IOException e) {
        throw new InvalidPathException(startingDir.toString(), "Path is invalid" + e);
      }
      fileExp.testBank.outputFile(srPath);
      Sort sortedList = new Sort(fileExp.mdCollection, f);
      sortedList.outputFile(outputDir);
      //TestBank.outputFile(srPath); //AA
    } else {
      throw new IllegalArgumentException("Incorrect number of command line arguments");
    }
  }
}