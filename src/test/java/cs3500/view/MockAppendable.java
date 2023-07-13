package cs3500.view;

import java.io.IOException;

/**
 * Mock appendable class to test throwing errors.
 */
public class MockAppendable implements Appendable {

  /**
   * Var used to throw exception
   *
   * @throws IOException exception thrown from bad appendable
   */
  private void throwInOut() throws IOException {
    throw new IOException("Mock throwing an error");
  }

  /**
   * Bad appendable example which will always throw error
   *
   * @param csq
   *         The character sequence to append.  If {@code csq} is
   *         {@code null}, then the four characters {@code "null"} are
   *         appended to this Appendable.
   *
   * @return null
   *
   * @throws IOException throws exception for bad appending
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throwInOut();
    return null;
  }

  /**
   * Bad appendable example which will always throw error
   *
   * @param csq
   *         The character sequence from which a subsequence will be
   *         appended.  If {@code csq} is {@code null}, then characters
   *         will be appended as if {@code csq} contained the four
   *         characters {@code "null"}.
   *
   * @param start
   *         The index of the first character in the subsequence
   *
   * @param end
   *         The index of the character following the last character in the
   *         subsequence
   *
   * @return null
   *
   * @throws IOException throws exception for bad appending
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throwInOut();
    return null;
  }

  /**
   * Bad appendable example which will always throw error
   *
   * @param c
   *         The character to append
   *
   * @return null
   * @throws IOException throws exception for bad appending
   */
  @Override
  public Appendable append(char c) throws IOException {
    throwInOut();
    return null;
  }
}
