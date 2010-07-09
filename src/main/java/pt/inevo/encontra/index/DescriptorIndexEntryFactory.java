package pt.inevo.encontra.index;

import pt.inevo.encontra.descriptors.Descriptor;
import pt.inevo.encontra.descriptors.DescriptorExtractor;

/**
 * This decouples the Descriptor from the needed serialization
 * @param <O>
 */
public abstract class DescriptorIndexEntryFactory<O extends AbstractObject,E extends IndexEntry> implements IndexEntryFactory<O,E>{

    DescriptorExtractor extractor;

    public DescriptorIndexEntryFactory(DescriptorExtractor extractor) {
        this.extractor=extractor;
    }

    public DescriptorExtractor getExtractor() {
        return extractor;
    }
    
    public E createIndexEntry(O object) {
        assert (object.getObject() != null);
        Descriptor descriptor=extractor.extract(object);
        return createIndexEntry(object,descriptor);
    }

    protected abstract E createIndexEntry(O object,Descriptor descriptor);

    //public abstract Descriptor getDescriptor(E entry);

    public abstract Object getObjectId(E entry);

}
