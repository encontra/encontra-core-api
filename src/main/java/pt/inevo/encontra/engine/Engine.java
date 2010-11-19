//package pt.inevo.encontra.engine;
//
//import java.util.List;
//
//import pt.inevo.encontra.index.*;
//import pt.inevo.encontra.index.search.AbstractSearcher;
//import pt.inevo.encontra.storage.IEntity;
//import pt.inevo.encontra.storage.IEntry;
//
///**
// * Entry point of the framework.
// * An Engine is searcheable.
// * @author ricardo
// */
//public class Engine<O extends IEntity> extends AbstractSearcher<O> {
//
//    protected IndexedObjectFactory indexedObjectFactory;
//
//    public Engine() {}
//
//    /**
//     * Insert the into the Engine. This makes the object to be inserted into all
//     * the indexes registered in the Engine.
//     * @param object
//     */
//    @Override
//    public boolean insert(O object) {
//        O res = (O) storage.save(object);
//        object.setId(res.getId());
//        System.out.println("Saved object with ID "+res.getId());
//        if (object instanceof IndexedObject) {
//            queryProcessor.insert((IndexedObject)object);
//        } else {
//            try {
//                List<IndexedObject> indexedObjects = indexedObjectFactory.processBean(object);
//                for (IndexedObject obj : indexedObjects) {
//                    queryProcessor.insert(obj);
//                }
//            } catch (IndexingException e) {
//                System.out.println("Exception: " + e.getMessage());
//            }
//        }
//
//        return true;
//    }
//
//    /*
//     * Remove the object from the Engine. This makes the object to be removed
//     * from all the indexes registered in the Engine.
//     * @param object
//     * */
//    @Override
//    public boolean remove(O object) {
//        storage.delete(object);
//        System.out.println("Removed object from storage with ID " +object.getId());
//
//        if (object instanceof IndexedObject) {
//            queryProcessor.remove((IndexedObject)object);
//        } else {
//            try {
//                List<IndexedObject> indexedObjects = indexedObjectFactory.processBean(object);
//                for (IndexedObject obj : indexedObjects) {
//                    queryProcessor.remove(obj);
//                }
//            } catch (IndexingException e) {
//                System.out.println("Exception: " + e.getMessage());
//            }
//        }
//        return true;
//    }
//
//    @Override
//    protected Result<O> getResultObject(Result<IEntry> entryresult) {
//        return new Result<O>((O) storage.get(entryresult.getResult().getId()));
//    }
//
//    public IndexedObjectFactory getIndexedObjectFactory() {
//        return indexedObjectFactory;
//    }
//
//    public void setIndexedObjectFactory(IndexedObjectFactory indexedObjectFactory) {
//        this.indexedObjectFactory = indexedObjectFactory;
//    }
//}
