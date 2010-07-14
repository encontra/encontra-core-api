package pt.inevo.encontra.descriptors;


import pt.inevo.encontra.storage.Entry;
import pt.inevo.encontra.storage.IEntry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeDescriptor implements Descriptor {

    private Serializable id;
    private String name;

    private double[] weights;
    protected List<Descriptor> descriptors=new ArrayList<Descriptor>();

    public CompositeDescriptor(){
        this(new Descriptor[] {});
    }

    public CompositeDescriptor(Descriptor[] descriptors) {
        setDescriptors(descriptors);
    }

    public void setDescriptors(Descriptor[] descriptors) {
        this.descriptors = Arrays.asList(descriptors);
        this.weights=new double[descriptors.length];
        Arrays.fill(weights,1.0);
    }

    @Override
    public double getDistance( Descriptor d) {
        CompositeDescriptor other=(CompositeDescriptor) d;
        double distance = 0f;
        int descriptorCount = 0;

        for(Descriptor descriptor : descriptors) {
            Descriptor otherDescriptor = other.findDescriptor(descriptor.getName());
            distance += descriptor.getDistance(otherDescriptor) * weights[descriptorCount];
            descriptorCount++;
        }

        if (descriptorCount > 0) {
            // TODO: find some better scoring mechanism, e.g. some normalization. One thing would be linearization of the features!
            // For now: Averaging ...
            distance = distance / (float) descriptorCount;
        }
        return distance;
    }

    @Override
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name=name;
    }


    
    /*
    @Override
    public double[] getWeights() {
        return weights;
    }

    @Override
    public void setWeights(double[] weights) {
        this.weights=weights;
    }*/


    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public void setId(Serializable id) {
       this.id=id;
    }


    public List<Descriptor> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(List<Descriptor> descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public Object getValue() {
        IEntry[] values=new IEntry[descriptors.size()];
        int i=0;
        for(Descriptor descriptor : descriptors) {
            IEntry entry=new Entry(descriptor.getName(),descriptor.getValue());
            values[i++]=entry;
        }
        return values;
    }

    public Descriptor findDescriptor(String name){
        for(Descriptor descriptor : descriptors) {
            if(descriptor.getName().equals(name))
                return descriptor;
        }
        return null;
    }
    @Override
    public void setValue(Object val) {
       if(val instanceof IEntry[]){
            IEntry[] objects=(IEntry[]) val;
            for(IEntry obj : objects) {
                Descriptor descriptor=findDescriptor(obj.getId().toString());
                descriptor.setValue(obj.getValue());
            }
        } else {
           throw new NotImplementedException();
        }
    }
}
