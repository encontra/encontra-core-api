package pt.inevo.encontra.index;

import java.io.Serializable;

/**
 * An Entry in the Index. An Index Entry has a Key and a Value.
 * @author Ricardo
 * @param <K> key
 * @param <E> value (the actual entry)
 */
public interface IndexEntry<K extends Serializable,E extends Serializable> {
    public K getKey();
    public void setKey(K key);
    public E getValue();
    public void setValue(E o);
}
