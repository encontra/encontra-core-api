package pt.inevo.encontra.index;

import java.util.List;

public interface EntryProvider<E>{

    public void begin();
    public void end();
    
    public E getFirst();
    public E getLast();

    public E getNext();
    public boolean hasNext();
    public E getPrevious();
    public boolean hasPrevious();
    public boolean contains(E entry);

    public List<E> getAll();
    public int size();
}