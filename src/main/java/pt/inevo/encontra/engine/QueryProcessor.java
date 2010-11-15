package pt.inevo.encontra.engine;

import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.index.IndexedObjectFactory;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.index.search.CombinedSearcher;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.criteria.ExpressionVisitor;

/**
 * Interface for the query processor
 */
public abstract class QueryProcessor<E extends IndexedObject> extends CombinedSearcher<E> implements ExpressionVisitor {

    protected IndexedObjectFactory indexedObjectFactory;

    public QueryProcessor() {
        super();
    }

    /**
     * Perform a search in this engine.
     * The query result will be the composition of the results from all the
     * indexes, accordingly to the specified algorithm for combining results.
     * @param query the interrogation
     * @return a ResultSet with the results from the query
     */
    @Override
    public abstract ResultSet<E> search(Query query);

    public IndexedObjectFactory getIndexedObjectFactory() {
        return indexedObjectFactory;
    }

    public void setIndexedObjectFactory(IndexedObjectFactory indexedObjectFactory) {
        this.indexedObjectFactory = indexedObjectFactory;
    }
}