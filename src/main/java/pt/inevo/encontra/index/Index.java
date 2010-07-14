package pt.inevo.encontra.index;

import java.util.List;

import pt.inevo.encontra.index.search.Searcher;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.Query.QueryType;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;

/**
 * Generic index structure.
 * Defines the shared methods by all the index structures that implement this
 * interface, for insert, remove, list, and search through it;
 * @author ricardo
 */
public interface Index<E extends IEntry> extends EntityStorage{

    
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

    public int size();
    
    public E get(int idx);
    
    /**
     * Checks if an object already exists in the index.
     * @param entry the to be checked
     * @return true if already exists, or false otherwise
     */
    public boolean contains(E entry);

    /**
     * Obtains all the objects that are within the index
     * @return a list with all the objects
     */
    public List<E> getAll();

}