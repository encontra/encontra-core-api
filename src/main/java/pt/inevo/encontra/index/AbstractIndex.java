package pt.inevo.encontra.index;

import pt.inevo.encontra.descriptors.DescriptorExtractor;
import pt.inevo.encontra.storage.EntityStorage;
import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;


/**
 * Generic index structure.
 * Defines the shared methods by all the index structures that implement this
 * interface, for insert, remove, list, and search through it;
 * @author ricardo
 */
public abstract class AbstractIndex<E extends IEntry> implements Index<E>{

    protected IndexEntryFactory<IndexedObject, ?> entryFactory;

    public IndexEntryFactory getEntryFactory() {
        return entryFactory;
    }

    public void setEntryFactory(IndexEntryFactory entryFactory) {
        this.entryFactory = entryFactory;
    }

}