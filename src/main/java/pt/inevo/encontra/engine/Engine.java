package pt.inevo.encontra.engine;

import java.io.Serializable;
import java.util.List;

import pt.inevo.encontra.index.*;
import pt.inevo.encontra.index.search.AbstractSearcher;
import pt.inevo.encontra.index.search.Searcher;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;

/**
 * Entry point of the framework.
 * An Engine is searcheable (we can search through different indexes).
 * @author ricardo
 */
public class Engine<O extends IEntity> extends AbstractSearcher<O> {

    protected IndexedObjectFactory indexedObjectFactory;
    protected Searcher searcher;

    public Engine() {
        init();
    }

    private void init() {
    }

    @Override
    public void setObjectStorage(EntityStorage storage) {
        this.storage = storage;
    }

    /**
     * Insert the into the Engine. This makes the object to be inserted into all
     * the indexes registered in the Engine.
     * @param object
     */
    @Override
    public boolean insert(O object) {
        O res = (O) storage.save(object);
        object.setId(res.getId());
        System.out.println("Saved object with ID "+res.getId());
        if (object instanceof IndexedObject) {
            searcher.insert(object);
        } else {
            try {
                List<IndexedObject> indexedObjects = indexedObjectFactory.processBean(object);
                for (IndexedObject obj : indexedObjects) {
                    searcher.insert(obj);
                }
            } catch (IndexingException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /*
     * Remove the object from the Engine. This makes the object to be removed
     * from all the indexes registered in the Engine.
     * @param object
     * */
    @Override
    public boolean remove(O object) {
        storage.delete(object);
        System.out.println("Removed object from storage with ID " +object.getId());

        if (object instanceof IndexedObject) {
            searcher.remove(object);
        } else {
            try {
                List<IndexedObject> indexedObjects = indexedObjectFactory.processBean(object);
                for (IndexedObject obj : indexedObjects) {
                    searcher.remove(obj);
                }
            } catch (IndexingException e) {
                e.printStackTrace();
            }
        }
        return true;
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
    public ResultSet<O> search(Query query) {
        return getResultObjects(searcher.search(query));
    }

    @Override
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
