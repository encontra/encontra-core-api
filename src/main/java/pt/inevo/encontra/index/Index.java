package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntry;

/**
 * Generic index structure.
 * Defines the shared methods by all the index structures that implement this
 * interface, for insert, remove, list, and search through it;
 * @author ricardo
 */
public interface Index<E extends IEntry> extends EntityStorage, EntryProvider<E> {

    /**
     * Inserts an object into the index
     * @param obj the object to be inserted
     * @return the success of the operation
     */
    public boolean insert(E entry);

    /**
     * Removes an object from the index
     * @param obj the object to be removed
     * @return the success of the operation
     */
    public boolean remove(E entry);
}
