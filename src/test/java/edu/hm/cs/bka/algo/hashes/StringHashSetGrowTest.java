package edu.hm.cs.bka.algo.hashes;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.Duration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StringHashSetGrowTest {

  private static final Duration TIMEOUT = Duration.ofSeconds(5);

  @Test
  @Order(0)
  public void testNotGrowing() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("U");
          sut.add("Y");
          sut.add("Z");

          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {"U", "Z", null, null, "Y"};
          int size = sut.size();
          assertEquals(3, size, "3 Element einefügt.");
          assertArrayEquals(expected, entries, "Tabelle sollte nicht vergrößert sein.");
        });
  }

  @Test
  @Order(1)
  public void testGrowing() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("U");
          sut.add("Y");
          sut.add("Z");
          sut.add("W");

          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {"Z", null, null, null, null, "U", null, "W", null, "Y"};
          int size = sut.size();
          assertEquals(4, size, "4 Element einefügt.");
          assertArrayEquals(expected, entries, "Tabelle sollte verdoppelt sein.");
        });
  }
}
