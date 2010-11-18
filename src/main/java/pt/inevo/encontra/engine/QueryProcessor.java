package pt.inevo.encontra.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.index.search.Searcher;
import pt.inevo.encontra.query.CriteriaQuery;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.QueryParser;
import pt.inevo.encontra.query.QueryParserNode;

/**
 * Interface for the query processor
 */
public abstract class QueryProcessor<E extends IndexedObject> {

    protected QueryParser queryParser;
    protected Map<String, Searcher> searcherMap;

    public QueryProcessor() {
        super();
        searcherMap = new HashMap<String, Searcher>();
    }

    public QueryParser getQueryParser() {
        return queryParser;
    }

    public void setSearcher(String name, Searcher searcher) {
        searcherMap.put(name, searcher);
    }

    public void removeSearcher(String name) {
        if (searcherMap.containsKey(name)) {
            searcherMap.remove(name);
        }
    }

    public boolean insert(E entry) {
        String name = entry.getName();
        Searcher searcher = searcherMap.get(name);
        if (searcher == null) {
            return false;
        }
        return searcher.insert(entry);
    }

    public boolean remove(E entry) {
        String name = entry.getName();
        Searcher searcher = searcherMap.get(name);
        if (searcher == null) {
            return false;
        }
        return searcher.remove(entry);
    }

    public abstract ResultSet process(Stack<QueryParserNode> node);

    public ResultSet search(Query query) {
        if (query instanceof CriteriaQuery) {
            Stack<QueryParserNode> nodes = queryParser.parse(query);
            return process(nodes);
        }
        return new ResultSet();
    }
}
