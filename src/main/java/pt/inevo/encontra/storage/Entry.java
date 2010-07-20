package pt.inevo.encontra.storage;

import java.io.Serializable;

/**
 * A simple entry with id/value palue that can be persisted
 * @param <ID> The type of the entry Key
 * @param <V>
 */
public class Entry<ID extends Serializable,V> implements IEntry<ID,V>{
    protected V object=null;
    protected ID id=null;

    public Entry(){}
    
    public Entry(ID identifier, V object){
        this.id = identifier;
        this.object = object;
    }

    /**
     * Gets the object of this object.
     * @return the object
     */
    public V getValue() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setValue(V object) {
        this.object = object;
    }

    /**
     * @return the id
     */
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id=id;
    }
}
