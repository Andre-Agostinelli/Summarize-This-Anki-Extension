package cs3500.pa01;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Scanner;

/**
 * Represents a Markdown File.
 */
public class MarkdownFile {
  String filename;
  FileTime created;
  FileTime modified;
  String contents;
  Path path;

  /**
   * Instantiates a MarkdownFile with the required meta-data and contents.
   *
   * @param fn  the name of the MarkdownFile.
   * @param cr  the time associated with file creation.
   * @param m   the time associated with file modification.
   * @param con the contents of the MarkdownFile.
   * @param p   the path of the MarkdownFile.
   */
  MarkdownFile(String fn, FileTime cr, FileTime m, String con, Path p) {
    this.filename = fn;
    this.created = cr;
    this.modified = m;
    this.contents = con;
    this.path = p;
  }

  /**
   * Instantiates a Markdown File with no information for testing/convenience.
   */
  MarkdownFile() {
    this.filename = null;
    this.created = null;
    this.modified = null;
    this.contents = null;
    this.path = null;
  }

  /**
   * Fills a MarkdownFile with the relevant data (contents, path, timings) and
   * calls to function to transform the contents to desired output.
   *
   * @param file     the path of the given .md file.
   * @param attr     the BasicFileAttributes of the .md file (creationTime, lastModifiedTime, ...).
   * @param contents the string holding the contents of the markdown folder.
   */
  public void fillMarkdownFile(Path file, BasicFileAttributes attr, String contents) {
    this.filename = file.getFileName().toString();
    this.created = attr.creationTime();
    //this.created = FileTime knownCreationTime = FileTime.from(Instant.parse())
    //This doesn't work in github
    this.modified = attr.lastModifiedTime();
    this.contents = transform(contents);
    this.path = file;
  }

  /**
   * Transforms the string contents of a given MarkdownFolder and returns.
   * the updated condensed study guide version.
   *
   * @param contents the source contents read in from the original MarkdownFile.
   * @return the updated contents of the MarkdownFile.
   */
  public String transform(String contents) {
    Scanner scan = new Scanner(contents);
    StringBuilder output = new StringBuilder();
    boolean firstHeader = true;


    while (scan.hasNextLine()) {
      StringBuilder content = new StringBuilder();
      content.append(scan.nextLine()).append("\n");

      if (content.toString().startsWith("#")) {
        handleHeader(output, content, firstHeader);
        firstHeader = false;
      } else if (content.toString().startsWith("- ") && content.toString().contains("[[")) {
        while (!(content.toString().contains("]]")) && scan.hasNextLine()) {
          content.append(scan.nextLine());
        }
        if (content.toString().contains("\n")) {
          String bulletHolder = removeN(content.toString());
          StringBuilder bulletStringBuilder = new StringBuilder(bulletHolder);
          handleBullet(output, bulletStringBuilder);
        } else {
          handleBullet(output, content);
        }
      }
    }
    scan.close();
    return output.toString();
  }



  /**
   * Handles the case when a bullet is present and appends to output
   *
   * @param output  the resulting StringBuilder with transformed output
   * @param content the initial StringBuilder to transform
   */
  private void handleBullet(StringBuilder output, StringBuilder content) {
    output.append("- ").append(content.substring(content.indexOf("[[") + 2,
        content.indexOf("]]"))).append("\n");
    //remove already analyzed lines so we can use .contains
    content.delete(0, content.indexOf("]]") + 2);
  }


  /**
   * Handles the case when a header is present and appends to output
   *
   * @param output      the resulting StringBuilder with transformed output
   * @param content     the initial StringBuilder to transform
   * @param firstHeader the boolean flag informing if this is the first seen header
   */
  private void handleHeader(StringBuilder output, StringBuilder content,
                            boolean firstHeader) {
    if (firstHeader) {
      output.append(content);
      //append header and change flag
    } else {
      output.append("\n").append(content);
    }
  }


  /**
   * Removes the line break if it is inside an important point
   *
   * @param holder string with a broken line in an important point
   * @return returns the string in output format
   */
  private String removeN(String holder) {
    int temp = holder.indexOf("\n");
    String str1 = holder.substring(0, temp);
    String str2 = holder.substring(temp + 1);
    if (str1.endsWith(" ") || str2.startsWith(" ")) {
      holder = str1.concat(str2);
    } else {
      holder = str1.concat(" ").concat(str2);
    }
    return holder;
  }




}






