package pt.inevo.encontra.index;


public interface IndexEntry<K,E> {
    public K getKey();
    public void setKey(K key);
    public E getValue();
    public void setValue(E o);
}
