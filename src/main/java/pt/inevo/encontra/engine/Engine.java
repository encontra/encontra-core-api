package pt.inevo.encontra.engine;

import java.util.ArrayList;
import java.util.List;
import pt.inevo.encontra.index.AbstractObject;
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

    public Engine() {
        init();
    }

    public Engine(QueryCombiner combiner) {
        this();
        this.combiner = combiner;
    }

    private void init() {
        indexes = new ArrayList<Index>();
    }

    /**
     * Add an index to the Engine.
     * @param idx
     */
    public void registerIndex(Index idx) {
        indexes.add(idx);
    }

    /**
     * Remove an index from the Engine.
     * @param idx
     */
    public void unregisterIndex(Index idx) {
        indexes.remove(idx);
    }

    /**
     * Gets all the Indexes registered in the Engine.
     * @return
     */
    public List<Index> getRegisteredIndexes() {
        return indexes;
    }

    /**
     * Gets the Query Combiner used by this Engine.
     * @return
     */
    public QueryCombiner getCombiner() {
        return combiner;
    }

    /**
     * Sets the Query Combiner to be used by this Engine.
     * @param combiner
     */
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
    public abstract ResultSet search(Query[] queries);

    /**
     * Insert the into the Engine. This makes the object to be inserted into all
     * the indexes registered in the Engine.
     * @param object
     */
    public void insertObject(AbstractObject object) {
        for (Index idx : indexes) {
            idx.insertObject(object);
        }
    }

    /**
     * Remove the object from the Engine. This makes the object to be removed
     * from all the indexes registered in the Engine.
     * @param object
     */
    public void removeObject(AbstractObject object) {
        for (Index idx : indexes) {
            idx.removeObject(object);
        }
    }

    /**
     * Checks if an object exists in the Engine. If one of the registered
     * indexes contains the object than it returns true. It only returns false,
     * if all the indexes don't contain the object.
     * @param object
     * @return
     */
    public boolean contains(AbstractObject object){
        for (Index idx : indexes) {
            if (idx.contains(object)){
                return true;
            }
        }
        return false;
    }
}
