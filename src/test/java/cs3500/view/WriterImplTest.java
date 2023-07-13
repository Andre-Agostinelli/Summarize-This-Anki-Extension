package cs3500.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the writer Implementation.
 */
class WriterImplTest {
  private Appendable appendable;

  private Writer writer;

  private static final String VALUE = "test";

  /**
   * Sets up tester class for Writer
   */
  @BeforeEach
  public void setUp() {
    this.appendable = new StringBuilder();
    this.writer = new WriterImpl(this.appendable);
  }

  /**
   * Unit tests if this method appends.
   */
  @Test
  public void testSuccess() {
    // check empty StringBuilder
    assertEquals("", this.appendable.toString());

    // write to it
    this.writer.write(VALUE);

    // check only that value appears in the StringBuilder
    assertEquals(VALUE, this.appendable.toString());
  }

  /**
   * Tests the failure case where appending is not possible
   */
  @Test
  public void testFailure() {
    this.writer = new WriterImpl(new MockAppendable());
    Exception exc = assertThrows(RuntimeException.class, () -> this.writer.write(VALUE),
        "Mock throwing an error:");
    assertEquals("Mock throwing an error Cannot append phrase to appendable.", exc.getMessage());
  }

}