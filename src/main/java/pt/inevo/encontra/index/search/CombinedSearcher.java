package pt.inevo.encontra.index.search;

import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.QueryCombiner;
import pt.inevo.encontra.storage.EntityStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinedSearcher<E extends IndexedObject> implements Searcher<E> {

    protected EntityStorage storage;
    /**
     * The current Query Combiner - helps combining the results from the
     * queries realized.
     */
    protected QueryCombiner combiner;
    protected Map<String, Searcher> searcherMap;

    public CombinedSearcher() {
        searcherMap = new HashMap<String, Searcher>();
    }

    public void setQueryCombiner(QueryCombiner combiner) {
        this.combiner = combiner;
    }

    public QueryCombiner getQueryCombiner() {
        return combiner;
    }

    public void addSearcher(String name, Searcher searcher) {
        searcherMap.put(name, searcher);
    }

    public void removeSearcher(String name) {
        if (searcherMap.containsKey(name)) {
            searcherMap.remove(name);
        }
    }

    /**
     * Perform a search in this engine.
     * The query result will be the composition of the results from all the
     * indexes, accordingly to the QueryCombiner rules.
     * @param query the interrogation
     * @return a ResultSet with the results from the query
     */
    @Override
    public ResultSet<E> search(Query query) {
        List<ResultSet> results = new ArrayList<ResultSet>();
        //sends the query to all the indexes that support that query type
        for (Map.Entry<String, Searcher> entry : searcherMap.entrySet()) {
            String name = entry.getKey();
            Searcher searcher = entry.getValue();
            results.add(searcher.search(query)); //getObjectResults(idx,idx.search()));
        }

        return combiner.combine(results);
    }

    @Override
    public boolean insert(E entry) {
        String name = entry.getName();
        Searcher searcher = searcherMap.get(name);
        if (searcher == null){
            return false;
        }
        return searcher.insert(entry);
    }

    @Override
    public boolean remove(E entry){
        String name = entry.getName();
        Searcher searcher = searcherMap.get(name);
        if (searcher == null){
            return false;
        }
        return searcher.remove(entry);
    }

    @Override
    public void setObjectStorage(EntityStorage storage) {
        this.storage = storage;
    }

    @Override
    public EntityStorage getObjectStorage() {
        return storage;
    }
}