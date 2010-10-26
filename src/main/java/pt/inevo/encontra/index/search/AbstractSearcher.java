package pt.inevo.encontra.index.search;

import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.index.Result;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;

/**
 * AbstractSearcher with common functions implemented.
 * @author Ricardo
 * @param <O>
 */
public abstract class AbstractSearcher<O extends IEntity> implements Searcher<O> {

    protected Index<Descriptor> index;
    protected EntityStorage storage;

    public void setIndex(Index index) {
        this.index = index;
    }

    public Index getIndex(Index index) {
        return index;
    }

    @Override
    public void setObjectStorage(EntityStorage storage) {
        this.storage = storage;
    }

    @Override
    public EntityStorage getObjectStorage() {
        return storage;
    }

    /**
     * Get a Result with an Object instead of the IndexedObject. Must be
     * implemented by all the concret searchers.
     * @param idx
     * @param indexEntryresult
     * @return
     */
    protected abstract Result<O> getResultObject(Result<IEntry> indexEntryresult);

    /**
     * Gets all the Results with an IEntity instead of the IEntry object.
     * @param indexEntryResultSet
     * @return
     */
    protected ResultSet<O> getResultObjects(ResultSet<IEntry> indexEntryResultSet) {
        ResultSet<O> results = new ResultSet<O>();

        for (Result entryResult : indexEntryResultSet) {
            Result r = getResultObject(entryResult);
            r.setSimilarity(entryResult.getSimilarity());
            results.add(r);
        }
        return results;
    }
}
