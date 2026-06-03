package edu.hm.cs.bka.algo.hashes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestApplication {

  public static void main() {
    SimpleSet<String> set = new StringHashSet();
    System.out.println(set); // alles leer!

    set.add("X"); // hashwert 3
    System.out.println(set); // -> [--, --, --, X, --]

    set.add("T"); // hashwert 4
    System.out.println(set); // -> [--, --, --, X, T]

    set.add("S"); // hashwert 3
    System.out.println(set); // -> [S, --, --, X, T]

    System.out.println(set.contains("X")); // -> true
    System.out.println(set.contains("S")); // -> true

    set.add("V"); // rehash
    System.out.println(set); // -> [--, --, --, S, T, --, V, --, X, --]

    set.add("I");
    System.out.println(set); // -> [--, --, --, S, T, I, V, --, X, --]

    set.remove("S");
    System.out.println(set); // -> [--, --, --, ✝, T, I, V, --, X, --]

    set.remove("T");
    System.out.println(set); // -> [--, --, --, ✝, ✝, I, V, --, X, --]

    System.out.println(set.contains("I")); // -> true

    set.add("I");
    System.out.println(set); // -> [--, --, --, ✝, ✝, I, V, --, X, --]

    set.add("R");
    System.out.println(set); // -> [--, --, R, ✝, ✝, I, V, --, X, --]

    set.add("H");
    System.out.println(set); // -> [--, --, R, H, ✝, I, V, --, X, --]
  }
}