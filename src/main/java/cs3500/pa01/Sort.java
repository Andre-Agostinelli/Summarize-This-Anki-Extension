package cs3500.pa01;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Represents a collection of MarkdownFiles that will be sorted based on a flag.
 */
public class Sort {
  ArrayList<MarkdownFile> mdCollection;
  Flag sortFlag;

  /**
   * Instantiates a sort object.
   *
   * @param mdList the FileExplorer which is an ArrayList of MarkdownFiles.
   * @param flag the Flag enumeration ("modified", "created", "filename").
   */
  Sort(ArrayList<MarkdownFile> mdList, Flag flag) {
    this.mdCollection = mdList;
    this.sortFlag = flag;
    sort(this.mdCollection, flag);
  }

  /**
   * Sorts the FileExplorer object according to the given Flag.
   *
   * @param mdList the list of MarkdownFiles.
   * @param flag the Flag enumeration ("modified", "created", "filename").
   */
  public void sort(ArrayList<MarkdownFile> mdList, Flag flag) {
    if (flag == Flag.FILENAME) {
      mdList.sort(new FilenameCompare());
    } else if (flag == Flag.CREATED) {
      mdList.sort(new CreatedCompare());
    } else if (flag == Flag.MODIFIED) {
      mdList.sort(new ModifiedCompare());
    } else {
      throw new RuntimeException("Flag is not valid...");
    }
  }

  /**
   *  Writes the file out to the output path.
   *
   * @param output the path of the destination file.
   */
  public void outputFile(Path output) {
    StringBuilder strbuilder = new StringBuilder();
    for (int i = 0; i < this.mdCollection.size(); i++)  {
      if (i == 0) {
        strbuilder.append(this.mdCollection.get(i).contents);
      } else {
        strbuilder.append("\n").append(this.mdCollection.get(i).contents);
      }
      //System.out.println(strbuilder);
    }
    writeToFile(output.toFile(), strbuilder.toString());
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

}
