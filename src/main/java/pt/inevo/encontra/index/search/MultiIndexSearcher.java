package pt.inevo.encontra.index.search;

import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.index.IndexEntry;
import pt.inevo.encontra.index.IndexEntryFactory;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.query.Query;

import java.util.ArrayList;
import java.util.List;

public class MultiIndexSearcher<E> extends IndexSearcher<E>{

    /**
     * A list of the registered indexes
     */
    protected List<Index> indexes;

    /**
     * A list of the registered index entry factories
     */
    protected List<IndexEntryFactory>idxEntryFactories;

    public MultiIndexSearcher() {
        indexes = new ArrayList<Index>();
        idxEntryFactories = new ArrayList<IndexEntryFactory>();
    }


      /**
     * Add an index to the Engine.
     * @param idx
     */
    public void registerIndex(Index idx,IndexEntryFactory idxEntryFactory) {
        indexes.add(idx);
        idxEntryFactories.add(idxEntryFactory);
    }

    /**
     * Remove an index from the Engine.
     * @param idx
     */
    public void unregisterIndex(Index<IndexEntry> idx) {
        indexes.remove(idx);
    }

    /**
     * Gets all the Indexes registered in the Engine.
     * @return
     */
    public List<Index> getRegisteredIndexes() {
        return indexes;
    }

    @Override
    public ResultSet<E> search(Query query) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ResultSet<E> search(Query[] queries) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
