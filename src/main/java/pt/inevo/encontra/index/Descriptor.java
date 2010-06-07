package pt.inevo.encontra.index;

/**
 * Descriptor -> element in the (key, descriptor)
 * @author ricardo
 */
public class Descriptor<T> {

    protected T [] descriptor;

    public Descriptor(T [] descriptorValue){
        this.descriptor = descriptorValue;
    }
}
