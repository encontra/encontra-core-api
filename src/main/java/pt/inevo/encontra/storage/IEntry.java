package pt.inevo.encontra.storage;

import java.io.Serializable;

/**
 * A simple entry with id/value palue that can be persisted
 * @param <K> The type of the entry Key
 * @param <V>
 */
public interface IEntry<K extends Serializable,V extends Serializable> extends IEntity<K>{
    public V getValue();
    public void setValue(V o);
}
