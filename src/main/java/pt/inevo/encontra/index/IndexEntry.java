package pt.inevo.encontra.index;

import java.io.Serializable;


public interface IndexEntry<K extends Serializable,E> {
    public K getKey();
    public void setKey(K key);
    public E getValue();
    public void setValue(E o);
}
