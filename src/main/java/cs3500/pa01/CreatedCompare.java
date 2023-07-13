package cs3500.pa01;

/**
 * Represents a sort based on FileTime created.
 */
public class CreatedCompare extends AbsCompare {

  /**
   * Overrides the compare function to compare Markdown objects based on this class.
   *
   * @param o1 the first object to be compared.
   * @param o2 the second object to be compared.
   * @return the integer result of compare following natural ordering of compare functions
   */
  @Override
  public int compare(MarkdownFile o1, MarkdownFile o2) {
    if (o1.created.compareTo(o2.created) == 0) {
      return 0;
    } else if (o1.created.compareTo(o2.created) > 0) {
      return 1;
    } else {
      return -1;
    }
  }
}
