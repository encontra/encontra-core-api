package pt.inevo.encontra.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.index.*;
import pt.inevo.encontra.index.search.AbstractSearcher;
import pt.inevo.encontra.index.search.Searcher;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;

/**
 * Entry point of the engine
 * @author ricardo
 */
public class Engine<O extends IEntity> extends AbstractSearcher<O> {

    IndexedObjectFactory indexedObjectFactory;
    EntityStorage storage;

    protected Searcher searcher;

    public Engine() {
        init();
    }

    private void init() {

    }

    public void setObjectStorage(EntityStorage storage){
        this.storage = storage;
    }


    /**
     * Insert the into the Engine. This makes the object to be inserted into all
     * the indexes registered in the Engine.
     * @param object
     */
    public boolean insert(O object) {
        object=(O)storage.save(object);
        if(object instanceof IndexedObject){
            searcher.insert(object);
        } else {
            try {
                //storage.save(object);
                List<IndexedObject> indexedObjects=indexedObjectFactory.processBean(object);
                for(IndexedObject obj : indexedObjects){
                    searcher.insert(obj);
                }
            } catch (IndexingException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return true;
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



    @Override
    public ResultSet<O> search(Query query) {
        return getResultObjects(searcher.search(query));
    }

    public EntityStorage getObjectStorage() {
        return storage;
    }

    @Override
    protected Result<O> getResultObject(Result<IEntry> entryresult) {
        return new Result<O>((O) storage.get(entryresult.getResult().getId()));
    }

    @Override
    public Query.QueryType[] getSupportedQueryTypes() {
        return searcher.getSupportedQueryTypes();
    }

    @Override
    public boolean supportsQueryType(Query.QueryType type) {
        return searcher.supportsQueryType(type);
    }

    public Searcher getSearcher() {
        return searcher;
    }

    public void setSearcher(Searcher searcher) {
        this.searcher = searcher;
        this.searcher.setObjectStorage(this.storage);
    }

    public IndexedObjectFactory getIndexedObjectFactory() {
        return indexedObjectFactory;
    }

    public void setIndexedObjectFactory(IndexedObjectFactory indexedObjectFactory) {
        this.indexedObjectFactory = indexedObjectFactory;
    }
}
