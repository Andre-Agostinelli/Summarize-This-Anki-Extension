package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import org.junit.jupiter.api.Test;

/**
 * Test class for comparing based on lastModifiedTime
 */
class ModifiedCompareTest {

  /**
   * Test comapre function based on time modified last
   */
  @Test
  void compare() throws IOException {
    MarkdownFile mdf1 = new MarkdownFile();
    MarkdownFile mdf2 = new MarkdownFile();
    mdf1.path = Path.of("src", "test", "resources",
        "dummyMdFolder", "sampleMarkdown.md");
    mdf2.path = Path.of("src", "test", "resources", "dummyMdFolder",
        "scanThisFolder", "dummymarkdown1.0.md");
    BasicFileAttributes attributes1 = Files.readAttributes(mdf1.path, BasicFileAttributes.class);
    BasicFileAttributes attributes2 = Files.readAttributes(mdf2.path, BasicFileAttributes.class);


    MarkdownFile mdf3 = new MarkdownFile();

    mdf1.modified = attributes1.lastModifiedTime();
    mdf2.modified = attributes2.lastModifiedTime();
    mdf3.modified = attributes2.lastModifiedTime();

    ModifiedCompare mc = new ModifiedCompare();

    int result1 = attributes1.lastModifiedTime().compareTo(attributes2.lastModifiedTime());
    int result2 = attributes2.lastModifiedTime().compareTo(attributes1.lastModifiedTime());

    //2023-05-18T00:40:04.5468741Z: mdf1
    //2023-05-17T22:59:52.2055839Z: mdf2
    // so since mdf1 was made after mdf2 so if compare arg[1] = mddf1 result -> 1
    assertEquals(mc.compare(mdf2, mdf1), result2);
    assertEquals(mc.compare(mdf1, mdf2), result1);
    assertEquals(mc.compare(mdf2, mdf3), 0);
  }

}