/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.engine;

import java.util.ArrayList;
import java.util.List;
import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.QueryCombiner;

/**
 * Entry point of the engine
 * @author ricardo
 */
public abstract class Engine {

    /**
     * A list of the registered indexes
     */
    protected List<Index> indexes;

    /**
     * The current Query Combiner - helps combining the results from the
     * queries realized.
     */
    protected QueryCombiner combiner;

    public Engine(){
        init();
    }

    public Engine(QueryCombiner combiner){
        this();
        this.combiner = combiner;
    }

    private void init(){
        indexes = new ArrayList<Index>();
    }

    public void registerIndex(Index idx){
        indexes.add(idx);
    }

    public void unregisterIndex(Index idx){
        indexes.remove(idx);
    }

    public List<Index> getRegisteredIndexes(){
        return indexes;
    }

    public QueryCombiner getCombiner() {
        return combiner;
    }

    public void setCombiner(QueryCombiner combiner) {
        this.combiner = combiner;
    }

    /**
     * Perform a search in this engine.
     * The query result will be the composition of the results from all the
     * indexes, accordingly to the QueryCombiner rules.
     * @param query the interrogation
     * @return a ResultSet with the results from the query
     */
    public abstract ResultSet search(Query query);

    /**
     * Perform a search in this engine, composing a group of queries to the engine.
     * @param queries
     * @return
     */
    public abstract ResultSet search(Query [] queries);
}