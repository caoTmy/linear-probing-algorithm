# HashSet with Linear Probing

Implementation of a `SimpleSet<String>` backed by an open-addressing hash table with **linear probing** and **tombstone deletion**.

### Goal

- Implement `add`, `contains`, `remove`, and dynamic resizing for a hash-backed string set.
- understanding why deletion in open addressing requires a tombstone rather than a simple `null`.

---

## The Idea: Hashing with Open Addressing

A hash table stores elements directly inside an array — one element, one slot. No linked lists, no buckets.  
That raises an immediate question: what happens when two elements want the same slot? You need a **collision resolution strategy**.

The hash function used here:

```java
int idealIdx = Math.abs(element.hashCode()) % table.length;
```

The result is the *ideal* slot — where the element belongs if nothing is in the way.

---

## Linear Probing

When `idealIdx` is already taken, the next slot is tried, then the next, and so on:

```java
int testIdx = (idealIdx + i) % table.length;  // i = 0, 1, 2, ...
```

The modulo wraps around so the search loops back to the beginning when the end of the array is reached.

---

## Deletion with Tombstones

Setting `table[idx] = null` on removal silently breaks `contains()` for any element displaced during insertion.

**Example:** `"S"` sits at slot 0, only reachable because probing from slot 3 continues past occupied slots. Set slot 3 to `null` and `contains("S")` stops there — returning `false` even though `"S"` exists.

```
remove("X")     →  [--, --, --, null, --]
contains("S")  starts at slot 3, finds null → returns false  ← WRONG
```

### Using Tombstones instead of `null`

A tombstone marks the slot as *logically deleted but physically occupied*, meaning: "Something was here — keep going."


```
remove("X")     →  [--, --, --, ✝, --]
contains("S")  starts at slot 3, sees ✝ → keeps probing → finds "S" at slot 0  ← CORRECT
```


---

## Rules for correct Tombstone-Correct Behavior

### 1. `contains()` — skips over tombstones


### 2. `remove()` — writes tombstone instead of null


### 3. `add()` — order of operations matters

---

## Dynamic Table Growth

As the table fills up, clusters grow and linear probing degrades toward linear search. To keep operations fast, the table doubles in size when the load exceeds 50%:

```
if (size > table.length / 2) {
    String[] newTable = new String[table.length * 2];
    String[] tmpTable = table;
    table = newTable;
    size = 0;                           // reset: add() will recount

    for (String entry : tmpTable) {
        if (entry != null && !entry.equals(TOMBSTONE))
            add(entry);                 // rehash via add() into the new table
    }
}
```

Two crucial decisions here:

- **`size = 0` before copying**: otherwise every recursive `add()` during rehash inflates the counter massively.
- **Tombstones are dropped**: after rehashing, all elements sit at clean positions — old tombstones are meaningless and waste space.
---

## Known Limitation

If a large number of deletions leaves the table full of tombstones with no `null` slots remaining, probing can loop indefinitely without a termination condition. In this implementation that scenario is prevented in practice by the 50% load threshold triggering a rehash before it can occur. A production implementation would additionally track the tombstone count and trigger a cleanup rehash when necessary.

---

## Project Structure

```
src/
├── main/java/.../hashes/
│   ├── SimpleSet.java          # generic set interface
│   ├── StringHashSet.java      # this implementation
│   ├── TestApplication.java    # step-by-step demo with expected outputs
│   └── DemoApplication.java    # counts distinct words in Goethe's Faust
└── test/java/.../hashes/
    ├── StringHashSetAddTest.java
    ├── StringHashSetContainsTest.java
    ├── StringHashSetGrowTest.java
    └── StringHashSetRemoveTest.java
```
