package edu.hm.cs.bka.algo.hashes;

import java.util.Arrays;

/// Implementierung eines [SimpleSet] für Strings auf Basis einer Hashtable.
public final class StringHashSet implements SimpleSet<String> {

    // Dies ist die eigentliche Hashtable!
    private String[] table = new String[5];

    // Zähler für die tatsächlich enthaltenen Elemente
    private int size = 0;

    // Marker-Eintrag für gelöschte Einträge, Vergleich mit == zulässig!
    public static final String TOMBSTONE = "✝";

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(String element) {
        if (contains(element)) {
            return false;
        }

        // RESIZING
        if (size >table.length/2) {
            String[] newTable = new String[table.length*2];
            String[] tmpTable = table; // temp festhalten des alten Arrays vorm überschreiben
            table = newTable;
            size = 0; // revert, else it will multiply each resize

            for (int i = 0; i < tmpTable.length; i++) {
                if(tmpTable[i] != null && !tmpTable[i].equals(TOMBSTONE)) { // ignore TUMBSTONES in new table
                    add(tmpTable[i]);
                }
            }
        }

        int idealIdx = Math.abs(element.hashCode()) % table.length;

        for (int i = 0; i < table.length; i++) {
            int testIdx = (idealIdx + i) % table.length; // lineares sondieren hier, loops to the beginning

             // forbid duplicates covered by contains()

            if (table[testIdx] == null || table[testIdx].equals(TOMBSTONE)) { // found an empty table slot
                table[testIdx] = element;
                size++;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean contains(String element) {
        if (element != null) { // NPE ausschließen
            int idealIdx = Math.abs(element.hashCode()) % table.length;

            for (int i = 0; i < table.length; i++) {
                int testIdx = (idealIdx + i) % table.length; // lineares sondieren

                if (element.equals(table[testIdx])) { // compare with ACTUAL position (=testIdx), not positon wished for
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean remove(String element) {
        if (!contains(element)) { // if not existing
            return false;
        }

        int idealIdx = Math.abs(element.hashCode()) % table.length; // same as in add() and contains()

        for (int i = 0; i < table.length; i++) {
            int testIdx = (idealIdx + i) % table.length;

            if (element.equals(table[testIdx])) {
                table[testIdx] = TOMBSTONE; // to substitute null
                size--; //Tombstone is placeholder, NOT a real element
                return true;
            }
        }
            return false;
    }

    // Diese Implementierung müssen Sie nicht ändern, sie ist nur
    // zum leichteren Debuggen.

    @Override
    public String toString() {
        return Arrays.toString(table).replaceAll("null", "--");
    }
}
