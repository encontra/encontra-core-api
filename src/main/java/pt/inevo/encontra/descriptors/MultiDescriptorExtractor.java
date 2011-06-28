package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.reflection.Instantiator;
import pt.inevo.encontra.storage.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * This Extractor return a MultiDescriptor which is a List of Descriptors.
 * This allows creating a separate index entry for each of these descriptors.
 *
 * For using a single index entry use a CompositeDescriptorExtractor
 * @see pt.inevo.encontra.descriptors.CompositeDescriptorExtractor
 * @param <O> The IndexedObject type
 * @param <D> The type of the Descriptor to extract
 */
public abstract class MultiDescriptorExtractor<O extends IEntity,D extends Descriptor> extends DescriptorExtractor<O, MultiDescriptor<D>> {


    public MultiDescriptorExtractor(){
        super();
    }

    @Override
    public MultiDescriptor<D> newDescriptor() {
        return  new MultiDescriptor<D>();
    }

    public MultiDescriptorExtractor(Class<O> indexedObjectclass){
        super(indexedObjectclass, null);
    }

    @Override
    public O setupIndexedObject(MultiDescriptor<D> descriptor, O object) {

        object.setId(descriptor.getId());
        return object;
    }

    protected abstract List<D> extractDescriptors(O object);

    @Override
    public MultiDescriptor<D> extract(O object) {

        List<D> descriptors= extractDescriptors(object);

        MultiDescriptor descriptor = newDescriptor();
        descriptor.setDescriptors(descriptors);
        descriptor.setId(object.getId());

        return descriptor;
    }
}
