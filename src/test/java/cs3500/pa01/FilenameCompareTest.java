package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

/**
 * Tests class to test a filename based comparison
 */
class FilenameCompareTest {

  /**
   * Tests the compare method based on filename
   */
  @Test
  void compare() {
    MarkdownFile mdf1 = new MarkdownFile();
    MarkdownFile mdf2 = new MarkdownFile();
    try {
      mdf1.path = Path.of("src", "test", "resources",
          "dummyMdFolder", "sampleMarkdown.md");
      mdf2.path = Path.of("src", "test", "resources", "dummyMdFolder",
          "scanThisFolder", "markdown1.0.md");
    } catch (InvalidPathException e) {
      throw new InvalidPathException(e.toString(), "Path could not be found:");
    }
    mdf1.filename = mdf1.path.getFileName().toString();
    mdf2.filename = mdf2.path.getFileName().toString();

    MarkdownFile mdf3 = new MarkdownFile();
    mdf3.filename = mdf1.filename;

    FilenameCompare fc = new FilenameCompare();
    //2023-05-18T00:40:04.5468741Z: mdf1
    //2023-05-17T22:59:52.2055839Z: mdf2
    // so since mdf1 was made after mdf2 so if compare arg[1] = mddf1 result -> 1

    //for some reason was returning -15/15 instead of -1/1
    //still means same thing though as compare returns < 0 or > 0
    assertEquals(fc.compare(mdf2, mdf1), -1);
    assertEquals(fc.compare(mdf1, mdf2), 1);
    assertEquals(fc.compare(mdf1, mdf3), 0);
  }
}