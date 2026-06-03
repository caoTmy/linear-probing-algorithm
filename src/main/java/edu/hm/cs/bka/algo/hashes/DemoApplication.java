package edu.hm.cs.bka.algo.hashes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DemoApplication {

  /// Reads words from a file and populates a set with them.
  ///
  /// @throws IOException if there are problems with the sample file
  /// @throws URISyntaxException if there are problems with the sample file
  public static void main() throws IOException, URISyntaxException {

    SimpleSet<String> set = new StringHashSet();
    Path file = Paths.get(DemoApplication.class.getClassLoader().getResource("faust.txt").toURI());
    try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
      Stream<String> words =
          lines
              .map(line -> line.split("[^\\p{L}]"))
              .flatMap(Arrays::stream) // Stream<String>
              .filter(word -> !word.isEmpty());
      words.forEachOrdered(set::add);
    }
    System.out.println(set);
    System.out.println(set.size()); // should return 6955
  }
}