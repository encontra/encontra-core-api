package pt.inevo.encontra.index.search;

import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;

public interface Searcher<E> {

    /**
     * Perform a search in this engine.
     * The query result will be the composition of the results from all the
     * indexes, accordingly to the QueryCombiner rules.
     * @param query the interrogation
     * @return a ResultSet with the results from the query
     */
    public ResultSet<E> search(Query query);

    public boolean insert(E entry);
    
    public void setObjectStorage(EntityStorage storage);

    public EntityStorage getObjectStorage();

        /**
     * Obtains all the supported QueryTypes by the index
     * @return a list of the supported query types
     */
    public Query.QueryType[] getSupportedQueryTypes();

    /**
     * Checks if a specific QueryType is supported by the index
     * @param type the QueryType to be checked
     * @return true if type is supported, otherwise, retrieved false
     */
    public boolean supportsQueryType(Query.QueryType type);


}
