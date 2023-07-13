package cs3500.view;

/**
 * Represents the interface needed to write objects to/from the view and controller
 */
public interface Writer {

  /**
   * Writes to a given place (console)
   *
   * @param phrase the phrase to be written
   */
  void write(String phrase);
}
