package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.index.IndexedObject;


public abstract class DescriptorExtractor<O extends IndexedObject, D extends Descriptor> {

    private Class<O> indexObjectClass;
    private Class<D> descriptorClass;

    public DescriptorExtractor(Class indexObjectClass,Class descriptorClass){
        this.indexObjectClass=indexObjectClass;
        this.descriptorClass=descriptorClass;
    }


    protected D newDescriptor() {
        try {
            return  descriptorClass.newInstance();
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
            O res=indexObjectClass.newInstance();
            return setupIndexedObject(descriptor,res);
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }



}
