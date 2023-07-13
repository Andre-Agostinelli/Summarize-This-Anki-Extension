package cs3500.pa01;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

import cs3500.model.TestBank;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a FileExplorer aimed at exploring a given directory and finding .md files.
 */
public class FileExplorer implements FileVisitor<Path> {

  ArrayList<MarkdownFile> mdCollection;

  TestBank testBank;



  /**
   * Instantiates a FileExplorer object which holds an arrayList of MarkdownFile objects.
   *
   * @param mdC        the list of MarkdownFiles.
   * @param masterBank the list of test Problems
   */
  FileExplorer(ArrayList<MarkdownFile> mdC, TestBank masterBank) {
    this.mdCollection = mdC;
    this.testBank = masterBank;
  }

  /**
   * Helps explore a directory when a new dir is visited.
   *
   * @param dir a reference to the directory.
   * @param attrs the directory's basic attributes.
   *
   * @return the FileVisitResult statement to further visit the given directory.
   * @throws IOException if the directory cannot be visited.
   */
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    System.out.format("Starting Directory: %s%n", dir);
    return CONTINUE;
  }

  /**
   * Helps continue walking through file directory stating a dir was finished.
   *
   * @param dir a reference to the directory.
   * @param exc {@code null} if the iteration of the directory completes without
   *          an error; otherwise the I/O exception that caused the iteration
   *          of the directory to complete prematurely.
   *
   * @return the FileVisitResult statement to further visit the given directory.
   * @throws IOException if the fileWalker could not finish the directory.
   */
  public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
    System.out.format("Finishing Directory: %s%n", dir);
    return CONTINUE;
  }

  /**
   * Visits a file in the given directory.
   *
   * @param file a reference to the file.
   * @param attr the file's basic attributes.
   *
   * @return the FileVisitResult statement when visiting the file.
   * @throws IOException if the file was not visited correctly.
   */
  public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
    if (file.toString().endsWith(".md")) {
      MarkdownFile mdf = new MarkdownFile(null, null, null, null, null);
      TestBank problems = new TestBank(new ArrayList<>());
      String contents = readFromFile(file.toFile());
      problems.extractProblems(contents);
      mdf.fillMarkdownFile(file, attr, contents);
      this.mdCollection.add(mdf);
      this.testBank.addAll(problems);
    }
    return CONTINUE;
  }

  /**
   * Identifies that there has been an error and where it occurs.
   *
   * @param file a reference to the file.
   * @param exc the I/O exception that prevented the file from being visited.
   *
   * @return the TERMINATE fileVisitResult stating an error occurred.
   * @throws IOException if a file was not visited correctly.
   */
  public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
    System.out.format("Reached a file reading error here: " + file);
    System.err.println(exc);
    return TERMINATE;
  }

  /**
   * Reads the contents from a file to a String.
   *
   * @param file A file object which corresponds to a path in the file system and some information
   *             at that path.
   * @return the contents of the file as a String.
   */
  public String readFromFile(File file) {
    // Initialize a Scanner to read the file
    Scanner sc;
    try { // The file may not exist, in which case we need to handle that error (hence try-catch)
      sc = new Scanner(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    // Use the Scanner to iterate through the file line-by-line and accumulate its contents
    StringBuilder content = new StringBuilder(); // StringBuilder is better than String concat
    while (sc.hasNextLine()) { // Check there is another unread line in the file
      content.append(sc.nextLine()).append("\n");
    }
    return content.toString(); // Return the contents collected in the StringBuilder
  }



}


