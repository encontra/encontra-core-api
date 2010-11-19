package pt.inevo.encontra.index.search;

import java.io.Serializable;
import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.engine.QueryProcessor;
import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.index.Result;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.query.Query;
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
    protected QueryProcessor queryProcessor;

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

    public QueryProcessor getQueryProcessor() {
        return queryProcessor;
    }

    public void setQueryProcessor(QueryProcessor processor) {
        this.queryProcessor = processor;
    }

    /**
     * Checks if an object exists in the Engine. If one of the registered
     * indexes contains the object than it returns true. It only returns false,
     * if all the indexes don't contain the object.
     * @param object
     * @return
     */
    public boolean contains(O object) {
        return (storage.get((Serializable) object.getId()) != null);
    }


    @Override
    public boolean insert(O object) {
        //saves the object in the storage - for the top domain searcher only
        O res = (O) storage.save(object);
        object.setId(res.getId());

        return queryProcessor.insert(object);
    }

    @Override
    public boolean remove(O object) {
        //removes the object in the storage - for the top domain searcher only
        storage.delete(object);

        return queryProcessor.remove(object);
    }

    @Override
    public ResultSet search(Query query) {
        return getResultObjects(queryProcessor.search(query));
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
