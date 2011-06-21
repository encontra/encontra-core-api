package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntry;

/**
 * Index interface.
 * Defines the shared methods by all the index structures that implement this
 * interface, for insert, remove, list, and search through it;
 * @author ricardo
 */
public interface Index<E extends IEntry> extends EntityStorage {

    /**
     * Inserts an object into the index
     * @param entry the object to be inserted
     * @return the success of the operation
     */
    public boolean insert(E entry);

    /**
     * Removes an object from the index
     * @param entry the object to be removed
     * @return the success of the operation
     */
    public boolean remove(E entry);

    /**
     * Gets an Entry Provider for the Index. We this provider we can iterate
     * through all the index items. The provider doesn't modify the index.
     * @return the provider for this index
     */
    public EntryProvider<E> getEntryProvider();
}
