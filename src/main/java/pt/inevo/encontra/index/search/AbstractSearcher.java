package pt.inevo.encontra.index.search;

import pt.inevo.encontra.common.Result;
import pt.inevo.encontra.common.ResultProvider;
import pt.inevo.encontra.common.ResultSet;
import pt.inevo.encontra.common.ResultSetDefaultImpl;
import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.descriptors.DescriptorExtractor;
import pt.inevo.encontra.engine.IQueryProcessor;
import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;
import pt.inevo.encontra.benchmark.Benchmark;
import pt.inevo.encontra.benchmark.BenchmarkEntry;
import pt.inevo.encontra.descriptors.MultiDescriptor;
import pt.inevo.encontra.descriptors.MultiDescriptorExtractor;

import java.io.Serializable;

/**
 * AbstractSearcher with common functions implemented.
 *
 * @param <O> the type of entities to be returned by the AbstractSearcher
 * @author Ricardo
 */
public abstract class AbstractSearcher<O extends IEntity> implements Searcher<O> {

    protected Index<Descriptor> index;
    protected EntityStorage storage;
    protected IQueryProcessor queryProcessor;
    protected ResultProvider resultProvider;
    protected DescriptorExtractor extractor;

    protected Benchmark benchmark=new Benchmark("Searcher");

    public Benchmark getBenchmark(){
        return benchmark;
    }

    public void setIndex(Index index) {
        this.index = index;
    }

    /**
     * Gets the Index over which the Searcher performs the queries.
     *
     * @param index
     * @return
     */
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
     * Gets the IQueryProcessor for the Searcher.
     * @return the IQueryProcessor entity
     */
    public IQueryProcessor getQueryProcessor() {
        return queryProcessor;
    }

    /**
     * Sets the IQueryProcessor for the Searcher.
     * @param processor the IQueryProcessor entity
     */
    public void setQueryProcessor(IQueryProcessor processor) {
        this.queryProcessor = processor;
        this.queryProcessor.setTopSearcher(this);
    }

    /**
     * Sets the DescriptorExtractor used by the Searcher.
     * @param extractor the descriptor extractor to be used by the Searcher
     */
    public void setDescriptorExtractor(DescriptorExtractor extractor) {
        this.extractor = extractor;
    }

    /**
     * Gets the DescriptorExtractor used by the Searcher.
     * @return the descriptor extractor used by the Searcher
     */
    public DescriptorExtractor getDescriptorExtractor() {
        return extractor;
    }

    /**
     * Checks if an object exists in the Engine. If one of the registered
     * indexes contains the object than it returns true. It only returns false,
     * if all the indexes don't contain the object.
     *
     * @param object
     * @return
     */
    public boolean contains(O object) {
        return (storage.get((Serializable) object.getId()) != null);
    }


    @Override
    public boolean insert(O object) {
        //saves the object in the storage - for the top domain searcher only
        if(storage!=null) {
            O res = (O) storage.save(object);
            object.setId(res.getId());
        }
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
        BenchmarkEntry searchBenchmark=benchmark.start("Search");
        ResultSet res = queryProcessor.search(query);
        searchBenchmark.stop();

        BenchmarkEntry storageBenchmark=benchmark.start("Storage retrieval");
        res = getResultObjects(res);
        storageBenchmark.stop();

        return res;
    }

    @Override
    public void setResultProvider(ResultProvider provider) {
        this.resultProvider = provider;
    }

    @Override
    public ResultProvider getResultProvider() {
        return this.resultProvider;
    }

    /**
     * Gets the cost of performing a search using this searcher.
     * The default cost is the number of elements in the underlying index.
     *
     * @return
     */
    @Override
    public int getCost() {
        return index.getEntryProvider().size();
    }

    /**
     * Get a Result with an Object instead of the IndexedObject. Must be
     * implemented by all the concret searchers.
     *
     * @param indexEntryresult
     * @return
     */
    protected abstract Result<O> getResultObject(Result<IEntry> indexEntryresult);

    /**
     * Gets all the Results with an IEntity instead of the IEntry object.
     *
     * @param indexEntryResultSet
     * @return
     */
    protected ResultSet<O> getResultObjects(ResultSet<IEntry> indexEntryResultSet) {
        ResultSet<O> results = new ResultSetDefaultImpl<O>();

        for (Result entryResult : indexEntryResultSet) {
            // TODO - relax type contraints in extractor - Ugly hack - Handle multidescriptor extractor
            if(getDescriptorExtractor() instanceof MultiDescriptorExtractor){
                MultiDescriptorExtractor extractor=(MultiDescriptorExtractor) getDescriptorExtractor();
                Descriptor descriptor = (Descriptor) entryResult.getResultObject();
                Descriptor [] descriptors ={};
                MultiDescriptor multiDescriptor=  extractor.newDescriptor();
                multiDescriptor.setId(descriptor.getId());
                multiDescriptor.setDescriptors(descriptors);
                entryResult.setResultObject(multiDescriptor);
            }
            Result r = getResultObject(entryResult);
            if (r != null) {
                r.setScore(entryResult.getScore());
                results.add(r);
            }
        }
        return results;
    }
}
