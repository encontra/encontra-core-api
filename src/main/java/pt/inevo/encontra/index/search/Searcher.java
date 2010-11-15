package pt.inevo.encontra.index.search;

import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;

/**
 * Generic interface for the searcher.
 * @author Ricardo
 * @param <E>
 */
public interface Searcher<E> {

    /**
     * @param query the interrogation
     * @return a ResultSet with the results from the query
     */
    public ResultSet<E> search(Query query);

    public boolean insert(E entry);

    public boolean remove(E obj);

    public void setObjectStorage(EntityStorage storage);

    public EntityStorage getObjectStorage();
}
