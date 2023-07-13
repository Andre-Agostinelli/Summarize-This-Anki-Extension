package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Testing the CreatedCompare Class
 */
class CreatedCompareTest {


  @BeforeEach
  void initCreatedCompare() throws IOException {
    MarkdownFile mdf1 = new MarkdownFile();
    MarkdownFile mdf2 = new MarkdownFile();
    mdf1.path = Path.of("src", "test", "resources",
        "dummyMdFolder", "sampleMarkdown.md");
    mdf2.path = Path.of("src", "test", "resources", "dummyMdFolder",
        "scanThisFolder", "dummymarkdown1.0.md");
    BasicFileAttributes attributes1 = Files.readAttributes(mdf1.path, BasicFileAttributes.class);
    BasicFileAttributes attributes2 = Files.readAttributes(mdf2.path, BasicFileAttributes.class);
    //attributes1.creationTime() =
    mdf1.created = attributes1.creationTime();
    mdf2.created = attributes2.creationTime();
    //CreatedCompare cc = new CreatedCompare();
  }

  /**
   * Testing the compare function to it returns based on Time Created.
   */
  @Test
  void compare() {
    MarkdownFile mdf1 = new MarkdownFile();
    MarkdownFile mdf2 = new MarkdownFile();
    mdf1.path = Path.of("src", "test", "resources",
        "dummyMdFolder", "scanThisFolder", "dummymarkdown1.0.md");
    mdf2.path = Path.of("src", "test", "resources", "dummyMdFolder",
        "scanThisFolder", "dummymarkdown2.0.md");
    MarkdownFile mdf3 = new MarkdownFile();
    mdf3.path = Path.of("src", "test", "resources",
        "dummyMdFolder", "output.md");

    long time1 = 168502149;
    long time2 = 168509149;
    FileTime fileTime1 = FileTime.fromMillis(time1);
    FileTime fileTime2 = FileTime.fromMillis(time2);

    /*
     *  Following test is failing on build server...
    Files.setAttribute(mdf1.path, "creationTime", fileTime1);
    Files.setAttribute(mdf2.path, "creationTime", fileTime2);
    Files.setAttribute(mdf3.path, "creationTime", fileTime2);

    BasicFileAttributes attributes1 = Files.readAttributes(mdf1.path, BasicFileAttributes.class);
    BasicFileAttributes attributes2 = Files.readAttributes(mdf2.path, BasicFileAttributes.class);
    BasicFileAttributes attributes3 = Files.readAttributes(mdf3.path, BasicFileAttributes.class);

    mdf1.created = attributes1.creationTime();
    mdf2.created = attributes2.creationTime();
    mdf3.created = attributes3.creationTime();
     */
    mdf1.created = fileTime1;
    mdf2.created = fileTime2;
    mdf3.created = fileTime2;
    CreatedCompare cc = new CreatedCompare();

    // so since mdf1 was made before mdf2 so if compare arg[1] = mddf1 result -> -1
    // Following test is failing on build server...
    //assertEquals(cc.compare(mdf2, mdf1), 1);
    //assertEquals(cc.compare(mdf1, mdf2), -1);
    //assertEquals(cc.compare(mdf2, mdf3), 0);
    //TODO: Add tests back somehow


    assertEquals(cc.compare(mdf2, mdf1), 1);
    assertEquals(cc.compare(mdf1, mdf2), -1);
    assertEquals(cc.compare(mdf2, mdf3), 0);

  }

}