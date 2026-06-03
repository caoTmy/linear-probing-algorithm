package edu.hm.cs.bka.algo.hashes;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.Duration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StringHashSetAddTest {

  private static final Duration TIMEOUT = Duration.ofSeconds(5);

  @Test
  @Order(0)
  public void testUnchangedInitialState() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {null, null, null, null, null};
          int size = sut.size();
          assertTrue(sut.isEmpty());
          assertEquals(0, size, "Größe sollte initial 0 sein.");
          assertArrayEquals(expected, entries, "Tabelle sollte leer sein und Länge 5 haben.");
        });
  }

  @Test
  @Order(1)
  public void addSingleElement() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          boolean result = sut.add("Yeah");
          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {null, "Yeah", null, null, null};
          int size = sut.size();
          assertFalse(sut.isEmpty());
          assertTrue(result, "Rückgabe sollte true sein bei Einfügung.");
          assertEquals(1, size, "Größe wurde nicht hochgezählt.");
          assertArrayEquals(expected, entries, "String 'Yeah' wurde falsch abgelegt.");
        });
  }

  @Test
  @Order(2)
  public void addElementsWithoutCollision() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("Yeah");
          sut.add("yeah");
          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {null, "Yeah", null, "yeah", null};
          int size = sut.size();
          assertEquals(2, size, "Größe wurde nicht hochgezählt.");
          assertArrayEquals(expected, entries, "Strings wurden falsch abgelegt.");
        });
  }

  @Test
  @Order(3)
  public void addElementsWithCollision() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("Yeah");
          sut.add("hey");
          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {null, "Yeah", "hey", null, null};
          int size = sut.size();
          assertEquals(2, size, "Größe wurde nicht hochgezählt.");
          assertArrayEquals(expected, entries, "Strings wurden falsch abgelegt.");
        });
  }

  @Test
  @Order(4)
  public void repeatElementsWithCollision() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("Yeah");
          sut.add("hey");
          boolean result = sut.add("Yeah");
          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {null, "Yeah", "hey", null, null};
          int size = sut.size();
          assertFalse(result, "Zweites Hinzufügen sollte false liefern.");
          assertEquals(2, size, "Größe wurde nicht hochgezählt.");
          assertArrayEquals(expected, entries, "Strings wurden falsch abgelegt.");
        });
  }

  @Test
  @Order(5)
  public void testCollisionOnLastElement() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("tracl");
          sut.add("yay");
          Field f = StringHashSet.class.getDeclaredField("table");
          f.setAccessible(true);

          String[] entries = (String[]) f.get(sut);
          String[] expected = new String[] {"yay", null, null, null, "tracl"};
          int size = sut.size();
          assertEquals(2, size, "Größe wurde nicht hochgezählt.");
          assertArrayEquals(expected, entries, "Strings wurden falsch abgelegt.");
        });
  }
}
