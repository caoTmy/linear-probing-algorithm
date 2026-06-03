package edu.hm.cs.bka.algo.hashes;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.Duration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StringHashSetRemoveTest {

  private static final Duration TIMEOUT = Duration.ofSeconds(5);

  @Test
  @Order(0)
  public void testSimpleRemove() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("Z");
          boolean result = sut.remove("Z");

          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {StringHashSet.TOMBSTONE, null, null, null, null};
          int size = sut.size();
          assertTrue(result, "Löschung war erfolgreich");
          assertEquals(0, size, "Sollte wieder leer sein!");
          assertArrayEquals(expected, entries, "Tabelle sollte Tombstone enthalten.");
        });
  }

  @Test
  @Order(1)
  public void testSearchOverTombstone() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("Yeah");
          sut.add("hey");
          sut.remove("Yeah");
          boolean result = sut.contains("hey");

          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {null, StringHashSet.TOMBSTONE, "hey", null, null};

          assertTrue(result, "Element war enthalten!");
          assertEquals(1, sut.size(), "Größe stimmt nicht!");
          assertArrayEquals(expected, entries, "Tabelle sollte Tombstone enthalten.");
        });
  }

  @Test
  @Order(2)
  public void overwriteFirstTombStone() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("U");
          sut.add("V");
          sut.add("W");
          sut.remove("V");
          sut.remove("U");
          sut.add("X");
          sut.remove("W");
          boolean result = sut.add("Yeah");
          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected =
              new String[] {StringHashSet.TOMBSTONE, "Yeah", StringHashSet.TOMBSTONE, "X", null};

          assertTrue(result, "Element wiedereingefügt!");
          assertEquals(2, sut.size(), "Größe stimmt nicht!");
          assertArrayEquals(expected, entries, "Tabelle sollte Tombstone enthalten.");
        });
  }
}
