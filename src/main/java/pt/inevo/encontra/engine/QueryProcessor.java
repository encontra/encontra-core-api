package pt.inevo.encontra.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.inevo.encontra.common.ResultSet;
import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.index.IndexedObjectFactory;
import pt.inevo.encontra.index.IndexingException;
import pt.inevo.encontra.index.search.AbstractSearcher;
import pt.inevo.encontra.index.search.Searcher;
import pt.inevo.encontra.query.CriteriaQuery;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.QueryParser;
import pt.inevo.encontra.query.QueryParserNode;
import pt.inevo.encontra.storage.IEntity;

/**
 * Generic interface for the query processor.
 */
public abstract class QueryProcessor<E extends IEntity> {

    /**
     * Knows how to split the objects
     */
    protected IndexedObjectFactory indexedObjectFactory;
    /**
     * Parses the queries to an internal representation.
     */
    protected QueryParser queryParser;
    /**
     * Keeps track of all the registered searchers.
     * We can registered a searcher for a simple field or a complex one.
     */
    protected Map<String, Searcher> searcherMap;
    /**
     * Keep track of the searcher that holds this QueryProcessor
     */
    protected AbstractSearcher topSearcher;

    public QueryProcessor() {
        super();
        searcherMap = new HashMap<String, Searcher>();
    }

    public QueryParser getQueryParser() {
        return queryParser;
    }

    public void setQueryParser(QueryParser parser){
        this.queryParser = parser;
    }
    
    public AbstractSearcher getTopSearcher() {
        return topSearcher;
    }

    public void setTopSearcher(AbstractSearcher topSearcher) {
        this.topSearcher = topSearcher;
    }

    public void setIndexedObjectFactory(IndexedObjectFactory factory) {
        this.indexedObjectFactory = factory;
    }

    public IndexedObjectFactory getIndexedObjectFactory() {
        return indexedObjectFactory;
    }

    public void setSearcher(String name, Searcher searcher) {
        searcherMap.put(name, searcher);
    }

    public void removeSearcher(String name) {
        if (searcherMap.containsKey(name)) {
            searcherMap.remove(name);
        }
    }

    public boolean insert(E object) {
        if (object instanceof IndexedObject) {
            insertObject(object);
        } else {
            try {
                List<IndexedObject> indexedObjects = indexedObjectFactory.processBean(object);
                for (IndexedObject obj : indexedObjects) {
                    insertObject((E) obj);
                }
            } catch (IndexingException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }

        return true;
    }

    public boolean remove(E object) {
        if (object instanceof IndexedObject) {
            removeObject(object);
        } else {
            try {
                List<IndexedObject> indexedObjects = indexedObjectFactory.processBean(object);
                for (IndexedObject obj : indexedObjects) {
                    removeObject((E) obj);
                }
            } catch (IndexingException e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
        return true;
    }

    protected boolean insertObject(E entry) {
        Searcher searcher = getSearcher(entry);
         if (searcher == null) {
            return false;
        }
        return searcher.insert(entry);
    }

    protected boolean removeObject(E entry) {
        Searcher searcher = getSearcher(entry);
        if (searcher == null) {
            return false;
        }
        return searcher.remove(entry);
    }

    public Searcher getSearcher(String name) {
        return searcherMap.get(name);
    }

    protected Searcher getSearcher(E entry) {
        String name = "";
        if (entry instanceof IndexedObject) {
            IndexedObject o = (IndexedObject) entry;
            name = o.getName();
        } else {
            name = entry.getClass().getName();
        }
        Searcher searcher = searcherMap.get(name);
        return searcher;
    }

    /**
     * Takes an internal representation of the query and processes it.
     * @param node the root node of the internal query representation
     * @return the results of the query
     */
    public abstract ResultSet process(QueryParserNode node);

    /**
     * Method that breaks down the supplied CriteriaQuery into its internal
     * representation and sends it to the necessary searchers to retrieve the
     * results.
     * @param query must be an instance of a CriteriaQuery
     * @return the results of the query
     */
    public ResultSet search(Query query) {
        if (query instanceof CriteriaQuery) {
            QueryParserNode node = queryParser.parse(query);
            return process(node);
        }
        throw new RuntimeException("Query must be a CriteriaQuery");
    }
}
