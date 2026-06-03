package edu.hm.cs.bka.algo.hashes;

///  Simple interface for sets.
///
/// @param <E> Entry type. Must implement hashCode and equals meaningfully.
public interface SimpleSet<E> {

  /// Adds an element (if not already contained).
  ///
  /// @param element element to add, must not be null.
  /// @return true if the element was newly inserted
  boolean add(E element);

  /// Removes an element if it is contained.
  ///
  /// @param element element to remove, must not be null.
  /// @return true if the element was contained.
  boolean remove(E element);

  /// Returns whether an element is contained.
  ///
  /// @param element element to look for, must not be null.
  /// @return true if the element is contained
  boolean contains(E element);

  /// Returns the number of contained elements.
  ///
  /// @return number of elements
  int size();

  /// Returns whether the set is empty.
  ///
  /// @return true if the set is empty
  boolean isEmpty();
}
