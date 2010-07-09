package pt.inevo.encontra.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pt.inevo.encontra.index.*;
import pt.inevo.encontra.index.search.IndexSearcher;
import pt.inevo.encontra.index.search.Searcher;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.QueryCombiner;
import pt.inevo.encontra.storage.ObjectStorage;
import pt.inevo.encontra.storage.StorableObject;

/**
 * Entry point of the engine
 * @author ricardo
 */
public class Engine<O extends StorableObject> implements Searcher<O>{

    ObjectStorage storage;

    protected Searcher searcher;

    public Engine() {
        init();
    }


    private void init() {
 
    }

    public void setObjectStorage(ObjectStorage storage){
        this.storage = storage;
    }

    public ObjectStorage getObjectStorage() {
        return storage;
    }


    /**
     * Insert the into the Engine. This makes the object to be inserted into all
     * the indexes registered in the Engine.
     * @param object
     */
    public void insert(O object) {
        storage.save(object);
        IndexEntry entry = idxEntryFactories.get(i).createIndexEntry(object);
        searcher.insert(entry);
        for(int i=0;i<indexes.size();i++){

            indexes.get(i).insert(entry);
        }
    }

    /*
     * Remove the object from the Engine. This makes the object to be removed
     * from all the indexes registered in the Engine.
     * @param object
     *
    public void removeObject(AbstractObject object) {
        for (Index idx : indexes) {
            idx.remove(object);
        }
    }*/

    /**
     * Checks if an object exists in the Engine. If one of the registered
     * indexes contains the object than it returns true. It only returns false,
     * if all the indexes don't contain the object.
     * @param object
     * @return
     */
    public boolean contains(O object){
        return (storage.get((Serializable)object.getId())!=null);
    }

    /**
     * Get a Result with an Object instead of the Index entry
     * @param idx
     * @param indexEntryresult
     * @return
     */
    @SuppressWarnings({"unchecked"})
    protected Result<O> getObjectResult(Index idx,Result indexEntryresult){
        Serializable id=(Serializable) getEntryFactory(idx).getObjectId((IndexEntry)indexEntryresult.getResult());
        Result<O> result=new Result<O>((O)storage.get(id));
        result.setSimilarity(indexEntryresult.getSimilarity());

        return result;
    }

    protected ResultSet<O>  getObjectResults(Index idx,ResultSet<O> indexEntryResultSet){
        ResultSet<O> results=new ResultSet<O>();

        for(Result<O> entryResult : indexEntryResultSet) {
            results.add(getObjectResult(idx,entryResult));
        }
        return results;
    }

    public IndexEntryFactory getEntryFactory(Index idx) {
        return idxEntryFactories.get(indexes.indexOf(idx));
    }

    @Override
    public ResultSet<O> search(Query query) {
        return getObjectResults(searcher.search(query));
    }

    @Override
    public ResultSet<O> search(Query[] queries) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
