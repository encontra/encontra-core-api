package pt.inevo.encontra.descriptors;

import java.io.Serializable;
import pt.inevo.encontra.common.distance.DistanceMeasure;
import pt.inevo.encontra.common.distance.HasWeights;
import pt.inevo.encontra.index.Vector;

import java.util.Arrays;

/**
 * The Generic class for describing an EnContRA Descriptor.
 * @author ricardo
 */
public abstract class VectorDescriptor<ID extends Serializable, T extends Number> extends Vector<T> implements Descriptor, HasWeights,Cloneable, Iterable<T> {

    protected double weights[];
    protected Serializable id;
    protected DistanceMeasure distanceMeasure;

    public VectorDescriptor(Class<T> type, int size) {
        this(type, size, null);
    }

    public VectorDescriptor(Class<T> type, int size,Serializable id){
        super(type, size);
        this.id=id;
        this.weights=new double[size];
        Arrays.fill(weights,1.0);
    }

    @Override
    public double [] getWeights() {
        return weights;
    }

    @Override
    public void setWeights(double [] weights) {
        this.weights = weights;
    }

    public abstract DistanceMeasure getDistanceMeasure();

    /**
     * Gets the Descriptor type. The type is represented by the Descriptor class
     * name.
     * @return
     */
    @Override
    public Serializable getId() {
        return id;
    }

    /**
     * Gets the distance between two EnContRA Descriptors.
     * @param descriptor
     * @return
     */
    @Override
    public double getDistance(Descriptor descriptor) {
        return distanceMeasure.distance(this,descriptor);
    }
}