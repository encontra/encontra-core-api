package pt.inevo.encontra.storage;

import pt.inevo.encontra.index.AbstractObject;

import java.io.Serializable;

/**
 * Simple storable object
 * @param <ID> The Type of the Id
 * @param <O>  The 
 * @param <V>  A Serializable Value used for storing
 */
public abstract class StorableObject<ID extends Serializable,O,V extends Serializable> extends AbstractObject<ID,O> implements IEntry<ID,V> {

    public StorableObject() {
        super();
    }
    
    public StorableObject(ID identifier,O image){
        super(identifier, image);
    }
    

    @Override
    public abstract V getValue();

    @Override
    public abstract void setValue(V o);
}
