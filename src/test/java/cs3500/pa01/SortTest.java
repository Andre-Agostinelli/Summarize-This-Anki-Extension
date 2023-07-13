package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;



/**
 * Sort tester class to test sorting, outputting, and writing to a file.
 */
class SortTest {

  /**
   * tests sorting with a given flag.
   */
  @Test
  void sort() {
    MarkdownFile mdf1 = new MarkdownFile();
    MarkdownFile mdf2 = new MarkdownFile();
    MarkdownFile mdf3 = new MarkdownFile();
    mdf1.filename = "B";
    mdf2.filename = "C";
    mdf3.filename = "A";

    ArrayList<MarkdownFile> amdf = new ArrayList<>();
    amdf.add(mdf1);
    amdf.add(mdf2);
    amdf.add(mdf3);
    amdf.sort(new FilenameCompare());
    assertEquals(amdf.get(0), mdf3);
    assertEquals(amdf.get(1), mdf1);
    assertEquals(amdf.get(2), mdf2);

    long time =  10000000;
    long time1 = 1000000;
    long time2 = 1000005;
    mdf1.modified = FileTime.from(Instant.ofEpochMilli(time));
    mdf2.modified = FileTime.from(Instant.ofEpochMilli(time1));
    mdf3.modified = FileTime.from(Instant.ofEpochMilli(time2));

    amdf.sort(new ModifiedCompare());
    assertEquals(amdf.get(0), mdf2);
    assertEquals(amdf.get(1), mdf3);
    assertEquals(amdf.get(2), mdf1);

    mdf1.created = FileTime.from(Instant.ofEpochMilli(time1));
    mdf2.created = FileTime.from(Instant.ofEpochMilli(time2));
    mdf3.created = FileTime.from(Instant.ofEpochMilli(time));
    amdf.sort(new CreatedCompare());

    assertEquals(amdf.get(0), mdf1);
    assertEquals(amdf.get(1), mdf2);
    assertEquals(amdf.get(2), mdf3);
  }

  /**
   * Tests outputting string to a file method.
   */
  @Test
  void outputFile() throws IOException {
    MarkdownFile mdf = new MarkdownFile();
    MarkdownFile mdf2 = new MarkdownFile();
    mdf.contents = "- [[This is a string to output to a given file]]";
    mdf2.contents = "# Title example";
    mdf.filename = "a";
    mdf2.filename = "b";
    Path path1 = Path.of("src", "test", "resources",
        "dummyMdFolder", "output.md");

    ArrayList<MarkdownFile> amdf = new ArrayList<>();
    amdf.add(mdf);
    amdf.add((mdf2));

    Flag flag = Flag.FILENAME;
    Sort sortedList = new Sort(amdf, flag);
    sortedList.outputFile(path1);
    Scanner sc = new Scanner(path1);
    StringBuilder strBuilder = new StringBuilder();
    while (sc.hasNextLine()) {
      strBuilder.append(sc.nextLine());
    }
    assertEquals(strBuilder.toString(),
        "- [[This is a string to output to a given file]]# Title example");

  }

  /**
   * Tests writing to a file.
   */
  @Test
  void writeToFile() throws IOException {
    String contents = "abcdefg";
    String output = null;
    Path path1 = Path.of("src", "test", "resources", "dummyMdFolder", "tempFile.md");

    Sort sorter = new Sort(new ArrayList<>(), Flag.FILENAME);
    sorter.writeToFile(path1.toFile(), contents);
    Scanner sc = new Scanner(path1);
    while (sc.hasNextLine()) {
      output = sc.nextLine();
    }
    assertEquals(output, contents);

  }
}