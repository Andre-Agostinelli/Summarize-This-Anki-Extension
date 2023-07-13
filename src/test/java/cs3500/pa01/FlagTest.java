package cs3500.pa01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Tests the enumeration functionality
 *
 */
class FlagTest {

  /**
   * Test to ensure the values function works on FlagEnum
   */
  @Test
  void values() {
    Flag flag0 = Flag.CREATED;
    Flag flag1 = Flag.FILENAME;
    Flag flag2 = Flag.MODIFIED;
    Flag[] flag3 = Flag.values();
    Flag flag4 = flag3[0];
    Flag flag5 = flag3[1];
    Flag flag6 = flag3[2];
    assertEquals(flag0, flag4);
    assertEquals(flag1, flag5);
    assertEquals(flag2, flag6);
  }
}