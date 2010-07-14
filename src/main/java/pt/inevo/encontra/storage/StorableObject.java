package pt.inevo.encontra.storage;

import pt.inevo.encontra.index.IndexedObject;

import java.io.Serializable;

/**
 * Simple storable object
 * @param <ID> The Type of the Id
 * @param <O>  The 
 * @param <V>  A Serializable Value used for storing
 */
public abstract class StorableObject<ID extends Serializable,O,V extends Serializable> extends IndexedObject<ID,O>  {

    public StorableObject() {
        super();
    }
    
    public StorableObject(ID identifier,O image){
        super(identifier, image);
    }
    

    public abstract V getStorableValue();

    public abstract void setStorableValue(V o);
}
