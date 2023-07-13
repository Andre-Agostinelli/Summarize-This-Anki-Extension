package cs3500.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class TestBankTest {

  @Test
  void addProblem() {
  }

  @Test
  void problemCollectionToString() {
  }

  @Test
  void addAll() {
  }

  /**
   * Tests to ensure problems are properly extracted and can be re-output as String
   */
  @Test
  void extractProblems() {
    String example = "- [[What is my name?:::Jeff]]";
    TestBank exTestBank = new TestBank(new ArrayList<>());
    exTestBank.extractProblems(example);
    Problem exampleProblem = new Problem("What is my name?", "Jeff");
    assertEquals(exTestBank.problemCollection.get(0).question, exampleProblem.question);
    assertEquals(exTestBank.problemCollection.get(0).answer, exampleProblem.answer);
    assertEquals(exTestBank.problemCollection.get(0).problemToString(),
        "[[What is my name?:::Jeff]]***HARD\n");
    assertEquals(exTestBank.problemCollection.get(0).problemToString(),
        exampleProblem.problemToString());
  }

  /**
   * Tests to ensure Problems can be output to file
   */
  @Test
  void outputFile() {
    Path exPath = Path.of("src", "test", "resources", "srTestExamples",
        "outputFileTest.sr");
    Problem exampleProblem = new Problem("What is my name?", "Jeff.");
    Problem exampleProblem2 = new Problem("What is the capital of Canada?",
        "The capital is Ottawa.");
    TestBank exTestBank = new TestBank(new ArrayList<>());
    exTestBank.addProblem(exampleProblem);
    exTestBank.addProblem(exampleProblem2);
    exTestBank.outputFile(exPath);
    Scanner sc;
    try {
      sc = new Scanner(exPath);
    } catch (IOException e) {
      throw new RuntimeException("Test path is not valid and scanner cannot read in: " + e);
    }
    StringBuilder str = new StringBuilder();
    while (sc.hasNextLine()) {
      str.append(sc.nextLine()).append("\n");
    }
    assertEquals(str.toString(), "1. [[What is my name?:::Jeff.]]***HARD\n2."
        + " [[What is the capital of Canada?:::The capital is Ottawa.]]***HARD\n");
  }


  @Test
  void writeToFile() {
  }
}