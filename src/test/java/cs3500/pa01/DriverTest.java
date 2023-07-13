package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Test to test whole program from the command line
 */
class DriverTest {

  /**
   * Tests the main function essentially from command line
   */
  @Test
  public void testMain() throws IOException {
    Path temp = Files.createTempDirectory("t");
    String[] args = {temp.toString(), "filename", "out.md"};
    Driver.main(args);
  }

  /**
   * Hard tests the driver function with sample file TesterFile.md and output to TesterOutput.md
   * and should output just #title
   *
   * @throws IOException if scanner cannot read in lines correctly
   */
  @Test
  public void testMain1() throws IOException {
    Path testPath = Path.of("src", "test", "resources",
        "dummyMdFolder", "MainTest/TesterFile.md");
    Path testerOutput = Path.of("src", "test", "resources",
        "dummyMdFolder", "MainTest/TesterOutput.md");

    String[] args = {testPath.toString(), "created", testerOutput.toString()};
    Driver.main(args);
    Scanner sc = new Scanner(testerOutput);
    StringBuilder str = new StringBuilder();
    try {
      while (sc.hasNextLine()) {
        str.append(sc.nextLine());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    //String should be formatted with extra \n since title has no important bullets
    assertEquals(str.toString(), "# This is a title");
  }

  /**
   * Hard tests the driver function with sample file TesterFile.md and output to TesterOutput.md
   * and should output just #title with modified as flag
   *
   * @throws IOException if scanner cannot read in lines correctly
   */
  @Test
  public void testMain2() throws IOException {
    Path testPath = Path.of("src", "test", "resources",
        "dummyMdFolder", "MainTest/TesterFile.md");
    Path testerOutput = Path.of("src", "test", "resources",
        "dummyMdFolder", "MainTest/TesterOutput.md");

    String[] args = {testPath.toString(), "modified", testerOutput.toString()};
    Driver.main(args);
    Scanner sc = new Scanner(testerOutput);
    StringBuilder str = new StringBuilder();
    try {
      while (sc.hasNextLine()) {
        str.append(sc.nextLine());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    //String should be formatted with extra \n since title has no important bullets
    assertEquals(str.toString(), "# This is a title");
  }

  /**
   * Tests to ensure if incorrect amount of cmd-line args are given an error is thrown
   *
   * @throws IOException throws an IO exception if Driver receives one
   */
  @Test
  public void testMainException() throws IOException {
    String[] args = {"a", "modified", "b", "c"};
    IllegalArgumentException thrown = assertThrows(
        IllegalArgumentException.class,
        () -> Driver.main(args),
        "Incorrect number of command line arguments");
  }

}