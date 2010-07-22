package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.reflection.Instantiator;
import pt.inevo.encontra.storage.IEntity;


public abstract class DescriptorExtractor<O extends IEntity, D extends Descriptor> {

    private Class<O> indexObjectClass=null;
    private Class<D> descriptorClass=null;

    public DescriptorExtractor(){}
    
    public DescriptorExtractor(Class indexObjectClass,Class descriptorClass){
        this.indexObjectClass=indexObjectClass;
        this.descriptorClass=descriptorClass;
    }

    public Class<O> getIndexObjectClass() {
         if(indexObjectClass==null){
            indexObjectClass=Instantiator.getTemplateClass(this,0);
        }
        return indexObjectClass;
    }

    public void setIndexObjectClass(Class<O> indexObjectClass) {
        this.indexObjectClass = indexObjectClass;
    }

    public Class<D> getDescriptorClass() {
        if(descriptorClass==null){
            descriptorClass=Instantiator.getTemplateClass(this,1);
        }
        return descriptorClass;
    }

    public void setDescriptorClass(Class<D> descriptorClass) {
        this.descriptorClass = descriptorClass;
    }

    protected D newDescriptor() {
        try {
            return  getDescriptorClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    protected abstract O setupIndexedObject(D descriptor, O object);
    public abstract D  extract(O object);

    public O getIndexedObject(D descriptor) {
        try {
            O res=getIndexObjectClass().newInstance();
            return setupIndexedObject(descriptor,res);
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}