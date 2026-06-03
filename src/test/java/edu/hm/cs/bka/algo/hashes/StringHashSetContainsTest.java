package edu.hm.cs.bka.algo.hashes;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StringHashSetContainsTest {

  private static final Duration TIMEOUT = Duration.ofSeconds(5);

  @Test
  @Order(0)
  public void testUnchangedInitialState() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          assertFalse(sut.contains("Lala"), "Initiale Tabelle enthält nichts");
        });
  }

  @Test
  @Order(1)
  public void containSingleElement() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("Yeah");
          assertTrue(sut.contains("Yeah"));
          assertFalse(sut.contains("X"));
        });
  }

  @Test
  @Order(2)
  public void getElementsWithoutCollision() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("Yeah");
          sut.add("yeah");
          assertTrue(sut.contains("Yeah"));
          assertTrue(sut.contains("yeah"));
          assertFalse(sut.contains("V"));
          assertFalse(sut.contains("W"));
          assertFalse(sut.contains("X"));
          assertFalse(sut.contains("Y"));
          assertFalse(sut.contains("Z"));
        });
  }

  @Test
  @Order(3)
  public void getElementsWithCollision() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("Yeah");
          sut.add("hey");
          assertTrue(sut.contains("Yeah"));
          assertTrue(sut.contains("hey"));
        });
  }

  @Test
  @Order(4)
  public void getCollisionOnLastElement() {
    assertTimeoutPreemptively(
        TIMEOUT,
        () -> {
          StringHashSet sut = new StringHashSet();
          sut.add("tracl");
          sut.add("yay");
          assertTrue(sut.contains("tracl"));
          assertTrue(sut.contains("yay"));
          assertFalse(sut.contains("V"));
          assertFalse(sut.contains("W"));
          assertFalse(sut.contains("X"));
          assertFalse(sut.contains("Y"));
          assertFalse(sut.contains("Z"));
        });
  }
}
