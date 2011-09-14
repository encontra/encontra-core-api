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

    protected List<Double> weights = new ArrayList<Double>();
    protected List<Descriptor> descriptors=new ArrayList<Descriptor>();

    public CompositeDescriptor(){}

    public CompositeDescriptor(Descriptor[] descriptors) {
        setDescriptors(descriptors);
    }

    public void setDescriptors(Descriptor[] descriptors) {
        this.descriptors = new ArrayList<Descriptor>();
        this.descriptors.addAll(Arrays.asList(descriptors));
        this.weights=new ArrayList<Double>(descriptors.length);
        for (int i = 0; i < descriptors.length; i++) {
            weights.add(1.0);
        }
    }

    @Override
    public double getDistance(Descriptor d) {
        CompositeDescriptor other=(CompositeDescriptor) d;
        double distance = 0f;
        int descriptorCount = 0;

        for(Descriptor descriptor : descriptors) {
            Descriptor otherDescriptor = other.findDescriptor(descriptor.getName());
            distance += descriptor.getDistance(otherDescriptor) * weights.get(descriptorCount);
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
    public double getNorm() {
        double norm = 0f;
        int descriptorCount = 0;

        for(Descriptor descriptor : descriptors) {
            norm += descriptor.getNorm() * weights.get(descriptorCount);
            descriptorCount++;
        }

        if (descriptorCount > 0) {
            norm = norm / (float) descriptorCount;
        }
        return norm;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

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
        try {
            Descriptor desc = (Descriptor)Class.forName(name).newInstance();
            descriptors.add(desc);
            weights.add(1.0);
            return desc;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        }else if (val instanceof IEntry) {
            IEntry obj = (IEntry)val;
            Descriptor descriptor = findDescriptor(obj.getId().toString());
            descriptor.setValue(obj.getValue());
        } else {
            System.out.println("Object type was: " + val.getClass().toString());
            throw new NotImplementedException();
        }
        return;
    }
}