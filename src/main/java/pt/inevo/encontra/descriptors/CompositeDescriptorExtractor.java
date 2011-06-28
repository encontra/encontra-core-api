package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.storage.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * This Extractor return a CompositeDescriptor which is a List of Descriptors and weights.
 * This allows creating a single index entry combining all of these descriptors.
 *
 * For using a multiple index entris use a MultiDescriptorExtractor
 * @see pt.inevo.encontra.descriptors.MultiDescriptorExtractor
 * @param <O> The IndexedObject type
 */
public class CompositeDescriptorExtractor<O extends IEntity> extends DescriptorExtractor<O, CompositeDescriptor> {

    protected List<DescriptorExtractor<O, Descriptor>> extractors = new ArrayList<DescriptorExtractor<O, Descriptor>>();
    protected List<Double> weights = new ArrayList<Double>();
    
    public CompositeDescriptorExtractor(){}

    public CompositeDescriptorExtractor(Class<O> indexedObjectclass){
        super(indexedObjectclass, null);
    }

    public CompositeDescriptorExtractor(Class<O> indexedObjectClass, Class descriptorClass) {
        super(indexedObjectClass, descriptorClass);
    }

    public void addExtractor(DescriptorExtractor<O, Descriptor> d, double weight) {
        extractors.add(d);
        weights.add(weight);
    }

    @Override
    public O setupIndexedObject(CompositeDescriptor descriptor, O object) {

        descriptor.getDescriptors();
        
        object.setId(descriptor.getId());
        return object;
    }

    @Override
    public CompositeDescriptor extract(O object) {
        Descriptor[] descriptors = new Descriptor[extractors.size()];
        int i = 0;
        for (DescriptorExtractor<O, Descriptor> extractor : extractors) {
            Descriptor descriptor = extractor.extract(object);
            descriptors[i++] = descriptor;
        }

        CompositeDescriptor descriptor = newDescriptor();
        descriptor.setDescriptors(descriptors);
        descriptor.setId(object.getId());
        //descriptor.setValue(doc);

        return descriptor;
    }
}
