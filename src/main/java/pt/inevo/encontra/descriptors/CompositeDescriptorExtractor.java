package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.storage.IEntity;

import java.util.ArrayList;
import java.util.List;

public class CompositeDescriptorExtractor<O extends IEntity> extends DescriptorExtractor<O, CompositeDescriptor> {

    private List<DescriptorExtractor<O, Descriptor>> extractors = new ArrayList<DescriptorExtractor<O, Descriptor>>();
    List<Double> weights = new ArrayList<Double>();

    public CompositeDescriptorExtractor(){}

    public void addExtractor(DescriptorExtractor<O, Descriptor> d, double weight) {
        extractors.add(d);
        weights.add(weight);
    }

    public CompositeDescriptorExtractor(Class<O> indexedObjectClass, Class descriptorClass) {
        super(indexedObjectClass, descriptorClass);
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
