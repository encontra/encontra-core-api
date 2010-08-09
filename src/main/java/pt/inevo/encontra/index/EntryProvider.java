package pt.inevo.encontra.index;

import java.io.Serializable;
import java.util.List;

public interface EntryProvider<E>{

    public void begin();
    public void end();
    public boolean setCursor(E entry);
    
    public E getFirst();
    public E getLast();
    public E getEntry(Serializable key);
    public List<E> getAll();
    public int size();

    public E getNext();
    public boolean hasNext();
    public E getPrevious();
    public boolean hasPrevious();
    public boolean contains(E entry);
}