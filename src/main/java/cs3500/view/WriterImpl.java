package cs3500.view;

import java.io.IOException;
import java.util.Objects;

/**
 * Represents the actual Writer implementation
 */
public class WriterImpl implements Writer {

  // Fields
  private final Appendable appendable;

  // Constructor
  public WriterImpl(Appendable appendable) {
    this.appendable = Objects.requireNonNull(appendable);
  }

  /**
   * Writes to a given place (console)
   *
   * @param phrase the phrase to be written
   */
  @Override
  public void write(String phrase) {
    try {
      appendable.append(phrase);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage() + " Cannot append phrase to appendable.");
    }
  }
}
