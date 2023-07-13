package cs3500.view;

import java.util.Objects;
import java.util.Scanner;

/**
 * Represents the actual Reader implementation
 */
public class ReaderImpl implements Reader {

  // Fields
  private final Readable readable;

  private final String escapeSequence;

  /**
   * Instantiates a ReaderImplementation
   *
   * @param readable the thing to be read
   * @param escapeSequence the sequence to break the loop
   */
  public ReaderImpl(Readable readable, String escapeSequence) {
    this.readable = Objects.requireNonNull(readable);
    this.escapeSequence = Objects.requireNonNull(escapeSequence);
  }


  /**
   * Reads from a given input source and returns a String of the contents
   *
   * @return the read in String
   */
  @Override
  public String read() {

    Scanner scanner = new Scanner(readable);

    StringBuilder output = new StringBuilder();

    while (scanner.hasNextLine() && !scanner.hasNext(escapeSequence)) {
      output.append(scanner.nextLine());
    }
    return output.toString();
  }

}
