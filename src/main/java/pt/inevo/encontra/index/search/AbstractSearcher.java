package pt.inevo.encontra.index.search;

import pt.inevo.encontra.benchmark.Benchmark;
import pt.inevo.encontra.benchmark.BenchmarkEntry;
import pt.inevo.encontra.common.Result;
import pt.inevo.encontra.common.ResultProvider;
import pt.inevo.encontra.common.ResultSet;
import pt.inevo.encontra.common.ResultSetDefaultImpl;
import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.descriptors.DescriptorExtractor;
import pt.inevo.encontra.descriptors.MultiDescriptor;
import pt.inevo.encontra.descriptors.MultiDescriptorExtractor;
import pt.inevo.encontra.engine.IQueryProcessor;
import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.index.IndexedObjectFactory;
import pt.inevo.encontra.index.IndexingException;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * AbstractSearcher with common functions implemented.
 *
 * @param <O> the type of entities to be returned by the AbstractSearcher
 * @author Ricardo
 */
public abstract class AbstractSearcher<O extends IEntity> implements Searcher<O> {

    /**
     * The Index where to perform queries
     */
    protected Index<Descriptor> index;

    /**
     * The entity where IEntity objects are stored
     */
    protected EntityStorage storage;

    /**
     * Processes the performed queries
     */
    protected IQueryProcessor queryProcessor;

    /**
     * Provides the results
     */
    protected ResultProvider resultProvider;

    /**
     * Entity for extracting descriptors from the objects
     */
    protected DescriptorExtractor extractor;

    /**
     * Knows how to split the objects
     */
    private IndexedObjectFactory indexedObjectFactory;

    /**
     * Keeps track of all the registered searchers.
     * We can registered a searcher for a simple field or a complex one.
     */
    protected Map<String, Searcher> searcherMap;

    /**
     * Benchmarking the operations using the Searcher
     */
    protected Benchmark benchmark = new Benchmark("Searcher");

    /**
     * Logging for all the searchers that extend the Abstract Searcher
     */
    protected Logger logger = Logger.getLogger(AbstractSearcher.class.toString());

    public AbstractSearcher() {
        this.searcherMap = new HashMap<String, Searcher>();
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public IndexedObjectFactory getIndexedObjectFactory() {
        return indexedObjectFactory;
    }

    public void setIndexedObjectFactory(IndexedObjectFactory indexedObjectFactory) {
        this.indexedObjectFactory = indexedObjectFactory;
    }

    public void setSearcher(String name, Searcher searcher) {
        searcherMap.put(name, searcher);
    }

    public void removeSearcher(String name) {
        if (searcherMap.containsKey(name)) {
            searcherMap.remove(name);
        }
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
     *
     * @return the IQueryProcessor entity
     */
    public IQueryProcessor getQueryProcessor() {
        return queryProcessor;
    }

    /**
     * Sets the IQueryProcessor for the Searcher.
     *
     * @param processor the IQueryProcessor entity
     */
    public void setQueryProcessor(IQueryProcessor processor) {
        this.queryProcessor = processor;
        //only the top searcher should have a query processor!
        this.queryProcessor.setTopSearcher(this);
    }

    /**
     * Sets the DescriptorExtractor used by the Searcher.
     *
     * @param extractor the descriptor extractor to be used by the Searcher
     */
    public void setDescriptorExtractor(DescriptorExtractor extractor) {
        this.extractor = extractor;
    }

    /**
     * Gets the DescriptorExtractor used by the Searcher.
     *
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
        if (storage != null)
            return (storage.get(object.getId()) != null);
        else {
            if (index != null) {
                return (index.get(object.getId()) != null);
            }
        }
        return false;
    }

    @Override
    public boolean insert(O object) {
        //saves the object in the storage - for the top domain searcher only
        if (storage != null) {
            O res = (O) storage.save(object);
            object.setId(res.getId());
        }

        //if the descriptor extractor is not null, then insert into the index
        if (extractor != null) {
            Descriptor desc = extractor.extract(object);
            if (desc instanceof Collection) {  // Handle MultiDescriptors!
                boolean res = true;
                Collection<Descriptor> descriptors = (Collection<Descriptor>) desc;
                for (Descriptor d : descriptors) {
                    res = res && index.insert(d);
                }
                return res;
            }

            return index.insert(desc);
        } else {
            boolean inserted = false;
            //save the object in the indexes
            if (object instanceof IndexedObject) {
                inserted = insertObject(object);
            } else {
                try {
                    List<IndexedObject> indexedObjects = getIndexedObjectFactory().processBean(object);
                    for (IndexedObject obj : indexedObjects) {
                        inserted &= insertObject((O) obj);
                    }
                } catch (IndexingException e) {
                    //log the exception and return false, because there was an error indexing the object.
                    inserted = false;
                }
            }

            return inserted;
        }
    }

    protected boolean insertObject(O entry) {
        Searcher searcher = getSearcher(entry);
        if (searcher == null) { //couldn't find a searcher for adding the entry
            return false;
        }
        return searcher.insert(entry);
    }

    public Searcher getSearcher(String name) {
        return searcherMap.get(name);
    }

    protected Searcher getSearcher(O entry) {
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

    @Override
    public boolean remove(O object) {
        //removes the object in the storage - for the top domain searcher only
        if (storage != null) {
            storage.delete(object);
        }

        if (extractor != null) {
            Descriptor d = extractor.extract(object);
            index.remove(d);
        } else {
            if (object instanceof IndexedObject) {
                removeObject(object);
            } else {
                try {
                    List<IndexedObject> indexedObjects = indexedObjectFactory.processBean(object);
                    for (IndexedObject obj : indexedObjects) {
                        removeObject((O) obj);
                    }
                } catch (IndexingException e) {
                    return false;
                }
            }
            return true;
        }

        return true;
    }

    protected boolean removeObject(O entry) {
        Searcher searcher = getSearcher(entry);
        if (searcher == null) {
            return false;
        }
        return searcher.remove(entry);
    }

    @Override
    public ResultSet search(Query query) {
        BenchmarkEntry searchBenchmark = benchmark.start("Search");
        ResultSet res = queryProcessor.search(query);
        searchBenchmark.stop();

        BenchmarkEntry storageBenchmark = benchmark.start("Storage retrieval");
        res = getResultObjects(res);
        storageBenchmark.stop();

        return res;
    }

    public ResultSet<O> similar(O object, int knn) {
        // TODO - must implement this method ASAP
        return new ResultSetDefaultImpl<O>();
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
            if (getDescriptorExtractor() instanceof MultiDescriptorExtractor) {
                MultiDescriptorExtractor extractor = (MultiDescriptorExtractor) getDescriptorExtractor();
                Descriptor descriptor = (Descriptor) entryResult.getResultObject();
                Descriptor[] descriptors = {};
                MultiDescriptor multiDescriptor = extractor.newDescriptor();
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
