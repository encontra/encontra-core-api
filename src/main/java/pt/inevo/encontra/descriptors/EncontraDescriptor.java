package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.index.AbstractObject;

/**
 * The Generic class for describing an EnContRA Descriptor.
 * @author ricardo
 */
public abstract class EncontraDescriptor {

    /**
     * Descriptor Type - is represented as a string given by the class name
     */
    protected String type;

    /**
     * Gets the Descriptor type. The type is represented by the Descriptor class
     * name.
     * @return
     */
    public abstract String getType();

    /**
     * Computes the descriptor from the given object.
     * @param object the object from which we want to determine the descriptor
     * @return true if the operation completes succefully, or false otherwise
     */
    public abstract boolean extract(AbstractObject object);

    /**
     * Gets the distance between two EnContRA Descriptors.
     * @param descriptor
     * @return
     */
    public abstract double getDistance(EncontraDescriptor descriptor);

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
