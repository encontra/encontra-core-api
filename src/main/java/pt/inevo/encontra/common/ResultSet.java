package pt.inevo.encontra.common;

import java.util.Collection;

/**
 * Generic interface for the ResultSet
 * @author Ricardo
 */
public interface ResultSet<T> extends Iterable<Result<T>> {

    public boolean add(Result<T> result);

    public boolean addAll(Collection<Result<T>> c);

    public void clear();

    public boolean contains(Result<T> o);

    public boolean containsResultObject(Object resultObject);

    public boolean containsAll(Collection<?> c);

    public boolean isEmpty();

    public void invertScores();

    public void normalizeScores();

    public double getScore(Object resultObject);

    public boolean remove(Result<T> o);

    public boolean removeAll(Collection<?> c);

    public int getSize();

    public void setMaxSize(int newSize);

    public ResultSet<T> getCopy();

    public Result<T> getSeed();

    public Result<T> getFirst();

    public Result<T> getLast();

    public ResultSet<T> getFirstResults(int value);

    public void setOwner(Object o);

    public Object getOwner();

    public boolean registerListener(ResultSetListener<T> listener);
}
