package cs3500.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents a TestBank filled with Problems.
 */
public class TestBank {

  public ArrayList<Problem> problemCollection;

  /**
   * Instantiates the TestBank class
   *
   * @param prblms the problems to be input into the testBank
   */
  public TestBank(ArrayList<Problem> prblms) {
    this.problemCollection = prblms;
  }

  /**
   * Adds a given Problem to the TestBank
   *
   * @param prblm the problem to add to the TestBank
   */
  public void addProblem(Problem prblm) {
    this.problemCollection.add(prblm);
  }


  /**
   * Transforms a ProblemCollection to a formatted Stringbuilder to be output to .sr file
   *
   * @param problems the ArrayList of Problems
   * @return the formatted list of problems
   */
  public StringBuilder problemCollectionToString(ArrayList<Problem> problems) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < problems.size(); i++) {
      output.append(i).append(". ").append(problems.get(i).question).append(":::")
          .append(problems.get(i).answer).append(problems.get(i).difficulty).append("\n");
    }
    return output;
  }


  /**
   * Adds the questions from one .md file to the master list
   *
   * @param testBank the testBank from a given .md file appended to the problemCollection
   */
  public void addAll(TestBank testBank) {
    this.problemCollection.addAll(testBank.problemCollection);
  }

  /**
   * Scans the contents of a mark down, appends problems to ArrayList,
   * and deletes problems from contents
   *
   * @param contents the string contents of a markdown file to be scanned
   */
  public void extractProblems(String contents) {
    Scanner scan = new Scanner(contents);

    while (scan.hasNextLine()) {
      StringBuilder content = new StringBuilder();
      content.append(scan.nextLine()).append("\n");
      if (content.toString().startsWith("- ") && content.toString().contains("[[")) {
        while (!(content.toString().contains("]]"))) {
          content.append(scan.nextLine());
        }
        if (content.toString().contains(":::")) {
          int start = content.indexOf("[[") + 2;
          int mid = content.indexOf(":::");
          int end = content.indexOf("]]");
          String question = (content.substring(start, mid));
          String answer = content.substring(mid + 3, end);
          Problem newProblem = new Problem(question, answer, Difficulty.HARD);
          this.addProblem(newProblem);
        }
      }
    }
    scan.close();
  }

  /**
   *  Writes the file out to the output path.
   *
   * @param srPath the path of the destination file.
   */
  public void outputFile(Path srPath) {
    StringBuilder strbuilder = new StringBuilder();
    for (int i = 0; i < this.problemCollection.size(); i++)  {
      strbuilder.append(i + 1).append(". ").append(this.problemCollection.get(i).problemToString());
      //System.out.println(strbuilder);
    }
    writeToFile(srPath.toFile(), strbuilder.toString());
  }



  /**
   * Writes the given String to the file filepath.
   *
   * @param file     where to write the contents.
   * @param contents contents to write to the file.
   */
  public void writeToFile(File file, String contents) {

    // Add .md to the end of the file path.
    // You may need to change this to get the desired user-experience that was asked for.
    //Path path = Path.of(file.toPath() + ".md");

    // Convert String to data for writing ("raw" byte data)
    byte[] data = contents.getBytes();

    // The path may not exist, or we may not have permissions to write to it,
    // in which case we need to handle that error (hence try-catch)
    try {
      // Built-in convenience method for writing data to a file.
      // Markdown is really just plain text with some
      // special syntax, so you can add `.md` to the file-path to write a Markdown file.
      Files.write(file.toPath(), data);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   *
   *
   * @param filePath the filePath to get question bank from
   * @param size the amount of questions to get
   */
  public TestBank createTestBank(String filePath, int size) {
    Path path = Path.of(filePath);
    Scanner sc;
    StringBuilder problemHolder = new StringBuilder();
    try {
      sc = new Scanner(path);
    } catch (IOException e) {
      throw new InvalidPathException(filePath, "Invalid path: " + e);
    }
    while (sc.hasNextLine()) {
      problemHolder.append(sc.nextLine());
    }
    extractProblems(problemHolder.toString());
    return pickProblems(this.problemCollection, size);
  }

  /**
   * Gets the correct size of the testBank
   *
   * @param problemCollection the full list of problem
   * @param size the size of the desired testBank
   * @return the testBank with the appropriate number of problems chosen
   */
  private TestBank pickProblems(ArrayList<Problem> problemCollection, int size) {
    if (size >= problemCollection.size()) {
      return new TestBank(problemCollection);
    } else {
      return new TestBank(pickSize(problemCollection, size));
    }
  }

  /**
   * Picks the number of question as desired by the user
   *
   * @param problemCollection the problems to choose from
   * @param size the size of desired test bank
   * @return the functional test bank
   */
  private ArrayList<Problem> pickSize(ArrayList<Problem> problemCollection, int size) {
    //TODO: HARD CODE SEED FOR TESTS
    Random rand = new Random();

    Set<Integer> generated = new HashSet<>();

    while (generated.size() < size) {
      Integer next = rand.nextInt(size);
      // As we're adding to a set, this ensures no repeats
      generated.add(next);
    }

    ArrayList<Integer> integerList = new ArrayList<>(generated);

    ArrayList<Problem> outputList = new ArrayList<>();

    for (int i = 0; i < size; i++) {
      outputList.add(problemCollection.get(integerList.get(i)));
    }
    return outputList;
  }

  /**
   * Finds the total number of hard questions.
   *
   * @return number of Hard questions in TestBank
   */
  public int findHardTotal() {
    int hardTotal = 0;
    for (int i = 0; i < this.problemCollection.size(); i++) {
      if (this.problemCollection.get(i).difficulty == Difficulty.HARD) {
        hardTotal++;
      }
    }
    return hardTotal;
  }

}
