package pt.inevo.encontra.index.search;

import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.index.IndexEntryFactory;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.query.Query;

import java.util.List;

public interface Searcher<E> {

    /**
     * Perform a search in this engine.
     * The query result will be the composition of the results from all the
     * indexes, accordingly to the QueryCombiner rules.
     * @param query the interrogation
     * @return a ResultSet with the results from the query
     */
    public ResultSet<E> search(Query query);

    /**
     * Perform a search in this engine, composing a group of queries to the engine.
     * @param queries
     * @return
     */
    public ResultSet<E> search(Query[] queries);
}
