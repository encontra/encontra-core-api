package pt.inevo.encontra.index;

import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.descriptors.DescriptorExtractor;

/**
 * This decouples the Descriptor from the needed serialization
 * @param <O>
 */
public abstract class DescriptorIndexEntryFactory<O extends IndexedObject,E extends IndexEntry> extends IndexEntryFactory<O,E>{

    DescriptorExtractor extractor;

    public DescriptorIndexEntryFactory(Class entryClass, Class indexedObjectClass) {
        super(entryClass,indexedObjectClass);
    }

    public void setExtractor(DescriptorExtractor extractor) {
        this.extractor=extractor;
    }

    public DescriptorExtractor getExtractor() {
        return extractor;
    }

    @Override
    protected E setupIndexEntry(O object, E entry) {
        assert (object != null);
        Descriptor descriptor = extractor.extract(object);
        if (object.getId() != null) {
            entry.setKey(object.getId().toString());
        }
        return setupIndexEntry(object, descriptor, entry);
    }

    protected abstract E setupIndexEntry(O object, Descriptor descriptor, E entry);

}
