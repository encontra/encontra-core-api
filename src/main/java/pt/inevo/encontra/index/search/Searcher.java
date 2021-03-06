package pt.inevo.encontra.index.search;

import pt.inevo.encontra.common.ResultProvider;
import pt.inevo.encontra.common.ResultSet;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;

/**
 * Generic interface for the searcher.
 * @author Ricardo
 * @param <E> the type of elements the Searcher will return
 */
public interface Searcher<E> {

    /**
     * Searches through the storage mechanism accordingly to the query provided.
     * @param query the interrogation
     * @return a ResultSet with the results from the query
     */
    public ResultSet<E> search(Query query);

    /**
     * Gets the ResultProvider being used by this searcher.
     * @return
     */
    public ResultProvider getResultProvider();

    /**
     * Set the ResultProvider to be used by this searcher.
     * @param provider
     */
    public void setResultProvider(ResultProvider provider);

    /**
     * Inserts an object into the underlying storage mechanism.
     * @param entry the object to be inserted
     * @return true if operation successfully ends, false otherwise
     */
    public boolean insert(E entry);

    /**
     * Removes an object from the underlying storage mechanism.
     * @param obj
     * @return true if operation successfully ends, false otherwise
     */
    public boolean remove(E obj);

    /**
     * Sets the storage mechanism.
     * @param storage
     */
    public void setObjectStorage(EntityStorage storage);

    /**
     * Gets the storage mechanism.
     * @return
     */
    public EntityStorage getObjectStorage();

    /**
     * Gets the cost of performing a search in this searcher.
     * Each Searcher should have it's own cost function.
     * @return
     */
    public int getCost();
}
