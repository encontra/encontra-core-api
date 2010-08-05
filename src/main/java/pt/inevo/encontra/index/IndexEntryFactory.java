package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.IEntry;

/**
 * Encontra IndexEntry Builder
 */
public abstract class IndexEntryFactory<O extends IEntry,E extends IndexEntry> { //extends MultiIndexHandler<IndexWriter>{

    private Class<E> indexEntryClass;
    protected Class<O> objectClass;

    public IndexEntryFactory(Class entryClass,Class objectClass){
        this.indexEntryClass=entryClass;
        this.objectClass=objectClass;
    }
    /**
     * A list of the registered index entry factories
     */
    public E createIndexEntry(O object) {
        try {
            E  entry = indexEntryClass.newInstance();
            return setupIndexEntry(object,entry);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract E  setupIndexEntry(O object, E entry);
    protected abstract O  setupObject(E entry, O object);

    public O getObject(E entry) {
        try {
            O res=objectClass.newInstance();
            return setupObject(entry,res);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
