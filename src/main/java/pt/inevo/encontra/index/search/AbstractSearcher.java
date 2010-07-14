package pt.inevo.encontra.index.search;

import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.descriptors.DescriptorExtractor;
import pt.inevo.encontra.index.Index;
import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.index.Result;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;

import java.io.Serializable;


public abstract class AbstractSearcher<O extends IEntity> implements Searcher<O> {
    protected Index<Descriptor> index;
    EntityStorage storage;

    public void setIndex(Index index){
        this.index=index;
    }


    @Override
    public void setObjectStorage(EntityStorage storage) {
        this.storage=storage;
    }

    @Override
    public EntityStorage getObjectStorage() {
        return storage;
    }

        /**
     * Get a Result with an Object instead of the IndexedObject
     * @param idx
     * @param indexEntryresult
     * @return
     */

   protected abstract Result<O> getResultObject(Result<IEntry> indexEntryresult);

    
    protected ResultSet<O>  getResultObjects(ResultSet<IEntry> indexEntryResultSet){
        ResultSet<O> results=new ResultSet<O>();

        for(Result entryResult : indexEntryResultSet) {
            Result r=getResultObject(entryResult);
            r.setSimilarity(entryResult.getSimilarity());
            results.add(r);
        }
        return results;
    }
}
