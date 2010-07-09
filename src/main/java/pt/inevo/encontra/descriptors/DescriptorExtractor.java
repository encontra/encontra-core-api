package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.index.AbstractObject;


public interface DescriptorExtractor<T extends AbstractObject, D extends Descriptor> {

    /**
     * Computes the descriptor from the given object.
     * @param object the object from which we want to determine the descriptor
     * @return true if the operation completes succefully, or false otherwise
     */
    public D extract(T object);

    public D newDescriptor(Class<D> clazz);
}
