package pt.inevo.encontra.index;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for providing access to the Index's entries.
 * The EntryProvider provides full access to the entries stored in a particular Index.
 * @param <E> the type of entries stored in an Index
 */
public interface EntryProvider<E>{

    /**
     * Resets the EntryProvider to the logical beginning of the Index.
     */
    public void begin();

    /**
     * Sets the EntryProvider to the logical beginning of the Index.
     */
    public void end();

    /**
     * Sets the EntryProvider cursor to a given Index Entry.
     * @param entry the entry to be used as cursor
     * @return true if the cursor is successfully set, of false otherwise
     */
    public boolean setCursor(E entry);

    /**
     * Gets the first entry of the Index (using the logical beginning of the index).
     * @return the first entry of the Index
     */
    public E getFirst();

    /**
     * Gets the last entry of the Index (using the logical end of the index).
     * @return the last entry of the Index
     */
    public E getLast();

    /**
     * Gets an entry from the Index given the entry's key.
     * @param key the key of the index's entry
     * @return the index's entry
     */
    public E getEntry(Serializable key);

    /**
     * Gets all the entries of the Index.
     * @return a list with all the entries of the Index.
     */
    public List<E> getAll();

    /**
     * Returns the number of entries in the Index.
     * @return
     */
    public int size();

    /**
     * Gets the next entry specified by the EntryProvider's cursor.
     * @return the next entry in the Index
     */
    public E getNext();

    /**
     * Checks if the EntryProvider has iterated over all the entries in the Index.
     * @return true if the EntryProvider reached the logical end of the Index, or false otherwise
     */
    public boolean hasNext();

    /**
     * Gets the previous entry specified by the EntryProvider's cursor.
     * @return the previous entry in the index
     */
    public E getPrevious();

    /**
     * Checks if the EntryProvider has reached the logical beginning of the Index.
     * @return true if the EntryProvider reached the beginning of the Index, or false otherwise
     */
    public boolean hasPrevious();

    /**
     * Checks if the Index contains the supplied entry.
     * @param entry the entry to be checked
     * @return true if the entry exists in the Index, or false otherwise
     */
    public boolean contains(E entry);
}