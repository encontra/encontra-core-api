package pt.inevo.encontra.index.search;

import java.io.Serializable;
import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.descriptors.DescriptorExtractor;
import pt.inevo.encontra.engine.QueryProcessor;
import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.common.Result;
import pt.inevo.encontra.common.ResultProvider;
import pt.inevo.encontra.common.ResultSet;
import pt.inevo.encontra.common.ResultSetDefaultImpl;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

/**
 * AbstractSearcher with common functions implemented.
 * @author Ricardo
 * @param <O>
 */
public abstract class AbstractSearcher<O extends IEntity> implements Searcher<O> {

    protected Index<Descriptor> index;
    protected EntityStorage storage;
    protected QueryProcessor queryProcessor;
    protected ResultProvider resultProvider;
    protected DescriptorExtractor extractor;

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
        this.queryProcessor.setTopSearcher(this);
    }

       public void setDescriptorExtractor(DescriptorExtractor extractor) {
        this.extractor = extractor;
    }

    public DescriptorExtractor getDescriptorExtractor() {
        return extractor;
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

    @Override
    public void setResultProvider(ResultProvider provider){
        this.resultProvider = provider;
    }

    @Override
    public ResultProvider getResultProvider() {
        return this.resultProvider;
    }

    /**
     * Gets the cost of performing a search using this searcher.
     * The default cost is the number of elements in the underlying index.
     * @return
     */
    @Override
    public int getCost(){
        return index.getEntryProvider().size();
    }

    /**
     * Get a Result with an Object instead of the IndexedObject. Must be
     * implemented by all the concret searchers.
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
        ResultSet<O> results = new ResultSetDefaultImpl<O>();

        for (Result entryResult : indexEntryResultSet) {
            Result r = getResultObject(entryResult);
            if (r != null){
                r.setScore(entryResult.getScore());
                results.add(r);
            }
        }
        return results;
    }
}
