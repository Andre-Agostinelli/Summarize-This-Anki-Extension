package cs3500.view;

/**
 * Represents the interface needed to read objects to/from the view and controller
 */
public interface Reader {

  /**
   * Reads from a given readable
   *
   * @return the String that needed to be read
   */
  String read();
}
