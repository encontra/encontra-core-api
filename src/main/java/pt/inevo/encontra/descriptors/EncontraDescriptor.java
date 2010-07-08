package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.index.AbstractObject;
import pt.inevo.encontra.index.IndexEntry;

/**
 * The Generic class for describing an EnContRA Descriptor.
 * @author ricardo
 */
public abstract class EncontraDescriptor<T extends AbstractObject> {//implements IndexEntry<String,Object>{

    protected String id;

    public EncontraDescriptor() {
       this.id=this.getClass().getCanonicalName();
    }
    
    public EncontraDescriptor(String id){
        this.id=id;
    }

    public abstract EncontraDescriptor setStringRepresentation(String descriptor);
    
    /**
     * Gets the Descriptor type. The type is represented by the Descriptor class
     * name.
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Computes the descriptor from the given object.
     * @param object the object from which we want to determine the descriptor
     * @return true if the operation completes succefully, or false otherwise
     */
    public abstract boolean extract(T object);

    /**
     * Gets the distance between two EnContRA Descriptors.
     * @param descriptor
     * @return
     */
    public abstract double getDistance(EncontraDescriptor<T> descriptor);

    /**
     * Gets a String representation of the Descriptor.
     * @return
     */
    public abstract String getStringRepresentation();

    /**
     * Gets a Double array representation of the Descriptor. (maintain?)
     * @return
     */
    public abstract double[] getDoubleRepresentation();


}
