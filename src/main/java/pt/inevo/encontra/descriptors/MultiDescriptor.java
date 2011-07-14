package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.storage.Entry;
import pt.inevo.encontra.storage.IEntry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiDescriptor<D extends Descriptor> extends ArrayList<D> implements Descriptor {

    private Serializable id;
    private String name;


    public void setDescriptors(D[] descriptors) {
        this.clear();
        this.addAll(Arrays.asList(descriptors));
    }

    @Override
    public double getDistance(Descriptor d) {

        double distance = 0f;
        int descriptorCount = 0;

        if(d instanceof MultiDescriptor){
            MultiDescriptor other=(MultiDescriptor) d;

            for(Descriptor descriptor : this) {
                Descriptor otherDescriptor = other.findDescriptor(descriptor.getName());
                distance += descriptor.getDistance(otherDescriptor);
                descriptorCount++;
            }

        } else {
            D otherDescriptor = (D) d;

            for(Descriptor descriptor : this) {
                distance += descriptor.getDistance(otherDescriptor);
                descriptorCount++;
            }

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

        for(Descriptor descriptor : this) {
            norm += descriptor.getNorm();
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

    public List<D> getDescriptors() {
        return this;
    }

    public void setDescriptors(List<D> descriptors) {
        this.clear();
        this.addAll(descriptors);
    }

    @Override
    public Object getValue() {
        IEntry[] values=new IEntry[this.size()];
        int i=0;
        for(Descriptor descriptor : this) {
            IEntry entry=new Entry(descriptor.getName(),descriptor.getValue());
            values[i++]=entry;
        }
        return values;
    }

    public D findDescriptor(String name){
        for(D descriptor : this) {
            if(descriptor.getName().equals(name))
                return descriptor;
        }
        try {
            D desc = (D)Class.forName(name).newInstance();
            //descriptors.add(desc);
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