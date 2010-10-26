package pt.inevo.encontra.index;

import java.util.*;

/**
 * The result set of a query.
 * Contains a list of Result's that can be iterated.
 * @author ricardo
 */
public class ResultSet<T> implements Collection<Result<T>> {

    private SortedSet<Result<T>> results;

    public ResultSet() {
        this(new ArrayList<Result<T>>());
    }

    public ResultSet(List<Result<T>> results) {
        this.results = Collections.synchronizedSortedSet(new TreeSet<Result<T>>());
        this.results.addAll(results);
    }

    public boolean contains(Result<T> res) {
        return (indexOf(res) >= 0);
    }

    public int indexOf(Result<T> res) {
        Iterator<Result<T>> iterator = results.iterator();
        int i = 0;
        Result<T> r;
        while (iterator.hasNext() && !iterator.next().equals(res)) {
            i++;
        }
        if (i >= size()) {
            return -1;
        }
        return i;
    }

    public Result<T> get(int index) {
        Iterator<Result<T>> iterator = results.iterator();
        while (true) {
            if (iterator.hasNext()) {
                if (index == 0) {
                    return iterator.next();
                }
                iterator.next();
                index--;
            } else {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        String resultSet = "[";
        for (Result res : results) {
            resultSet += res.toString() + ", ";
        }
        resultSet += "]";

        return resultSet;
    }

    protected float sigmoid(float f) {
        double result = 0f;
        result = -1d + 2d / (1d + Math.exp(-2d * f / 0.6));
        return (float) (1d - result);
    }

    /**
     * Returns the score of the document at given position.
     * Please note that the score in this case is a distance,
     * which means a score of 0 denotes the best possible hit.
     * The result list starts with position 0 as everything
     * in computer science does.
     *
     * @param position defines the position
     * @return the score of the document at given position. The lower the better (its a distance measure).
     */
    public double getScore(int position) {
        return get(position).getSimilarity();
    }

    public void invertScores() {
        List<Result<T>> invertedResults = new ArrayList<Result<T>>();
        int size = size();
        for (int i = 0; i < size; i++) {
            Result r = get(i);
            invertedResults.add(r);

            r.setSimilarity(1f - r.getSimilarity());
        }
        results.clear();
        results.addAll(invertedResults);
    }

    // TODO - Precalculate maxScore in ResultSet
    public void normalizeScores() {
        double maxScore = 0;
        for (Result result : results) {
            if (result.getSimilarity() > maxScore) {
                maxScore = result.getSimilarity();
            }
        }
        if (maxScore == 0) // Avoid division by zero
        {
            return;
        }
        for (Result result : results) {
            result.setSimilarity(result.getSimilarity() / maxScore);
        }
    }

    @Override
    public int size() {
        return results.size();
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Result) {
            Result r = (Result) o;
            return results.contains(r);
        } else {
            return false;
        }
    }

    @Override
    public Iterator<Result<T>> iterator() {
        return results.iterator();
    }

    @Override
    public T[] toArray() {
        return (T[]) results.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) results.toArray(a);
    }

    @Override
    public boolean add(Result<T> tResult) {
        return results.add(tResult);
    }

    public boolean remove(int idx) {
        return results.remove(get(idx));
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Result) {
            Result r = (Result) o;
            return results.remove(r);
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return results.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Result<T>> c) {
        return results.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return results.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return results.retainAll(c);
    }

    @Override
    public void clear() {
        results.clear();
    }
}
