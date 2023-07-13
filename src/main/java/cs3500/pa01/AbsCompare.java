package cs3500.pa01;

import java.util.Comparator;

/**
 * Abstract class to represent how a file will be compared.
 * The inherited classes will override the compare function.
 */
abstract class AbsCompare implements Comparator<MarkdownFile> {

  /**
   * Compare two Markdown files.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return the integer result of compare for the given class it operates on
   */
  @SuppressWarnings("checkstyle:ModifierOrder")
  @Override
  abstract public int compare(MarkdownFile o1, MarkdownFile o2);
  //abstract class
}
