package pt.inevo.encontra.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.index.IndexedObjectFactory;
import pt.inevo.encontra.index.IndexingException;
import pt.inevo.encontra.index.ResultSet;
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

    public QueryProcessor() {
        super();
        searcherMap = new HashMap<String, Searcher>();
    }

    public QueryParser getQueryParser() {
        return queryParser;
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

        // TODO remove this workaround when correcting this
        if (entry instanceof IndexedObject) {
            IndexedObject o = (IndexedObject) entry;
            Class s = o.getValue().getClass();
            if (!s.isPrimitive() && !s.getName().contains("String")) {
                searcher.insert(o.getValue());
            }
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

    public abstract ResultSet process(QueryParserNode node);

    public ResultSet search(Query query) {
        if (query instanceof CriteriaQuery) {
            QueryParserNode node = queryParser.parse(query);
            return process(node);
        }
        return new ResultSet();
    }
}
