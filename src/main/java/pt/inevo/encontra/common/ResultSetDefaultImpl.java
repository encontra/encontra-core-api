package pt.inevo.encontra.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import pt.inevo.encontra.common.Result;
import pt.inevo.encontra.common.ResultSet;
import pt.inevo.encontra.common.ResultSetEvent;
import pt.inevo.encontra.common.ResultSetListener;

/**
 * The result set of a query.
 * Contains a list of Result's that can be iterated.
 * @author ricardo
 */
public class ResultSetDefaultImpl<T> implements ResultSet<T> {

    private List<ResultSetListener> listeners;
    private SortedSet<Result<T>> results;
    private Result<T> seedResult, worseResult;
    private double lowestScore;
    private int maxSize;

    public ResultSetDefaultImpl() {
        this(new ArrayList<Result<T>>());
    }

    public ResultSetDefaultImpl(List<Result<T>> results) {
        this.results = Collections.synchronizedSortedSet(new TreeSet<Result<T>>());
        this.results.addAll(results);
        listeners = new ArrayList<ResultSetListener>();
    }

    public ResultSetDefaultImpl(Result<T> seed, int size) {
        this.seedResult = seed;
        this.maxSize = size;

        listeners = new ArrayList<ResultSetListener>();
        results = Collections.synchronizedSortedSet(new TreeSet<Result<T>>(new Comparator<Result<T>>() {

            @Override
            public int compare(Result<T> o1, Result<T> o2) {
                try {
                    double dist1 = seedResult.getScore() - o1.getScore();
                    double dist2 = seedResult.getScore() - o2.getScore();
                    if (o1.equals(o2)) {
                        return 0;
                    }
                    if (dist1 == dist2) {
                        return -1;
                    }
                    return new Double(dist1).compareTo(new Double(dist2));
                } catch (Exception ex) {
                    System.out.println("[Error]: Possible reason " + ex.toString());
                    return 0;
                }
            }
        }));
    }

    @Override
    public boolean add(Result<T> tResult) {

        if (maxSize == 0) {
            for (ResultSetListener lst : listeners) {
                lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.ADDED, tResult));
            }
            return results.add(tResult);
        } else {
            if (results.size() < maxSize) {
                results.add(tResult);
                worseResult = results.last();
                lowestScore = seedResult.getScore() - worseResult.getScore();
                for (ResultSetListener lst : listeners) {
                    lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.ADDED, tResult));
                }
                return true;
            } else {
                double score = seedResult.getScore() - tResult.getScore();
                if (score >= lowestScore) {
                    return false;
                } else {
                    results.remove(worseResult);
                    results.add(tResult);
                    worseResult = results.last();
                    lowestScore = seedResult.getScore() - worseResult.getScore();
                    for (ResultSetListener lst : listeners) {
                        lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.ADDED, tResult));
                    }
                    return true;
                }
            }
        }
    }

    @Override
    public boolean addAll(Collection<Result<T>> c) {
        boolean added = true;
        for (Result<T> r : c) {
            added = add(r);
        }
        return added;
    }

    @Override
    public boolean remove(Result<T> r) {

        if (maxSize == 0) {
            for (ResultSetListener lst : listeners) {
                lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.REMOVED, r));
            }
            return results.remove(r);
        } else {

            if (results.contains(r)) {
                boolean value = results.remove(r);
                if (results.size() > 0) {
                    worseResult = results.last();
                    try {
                        lowestScore = seedResult.getScore() - worseResult.getScore();
                    } catch (Exception ex) {
                        System.out.println("[Error]: Problem when calculating the "
                                + "distance between points. Possible reason: " + ex.toString());
                    }
                } else {
                    worseResult = null;
                    lowestScore = 0;
                }
                return value;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = true;
        for (Object res : c) {
            removed = remove((Result<T>) res);
        }
        return removed;
    }

    @Override
    public boolean contains(Result<T> o) {
        return results.contains(o);
    }

    @Override
    public boolean containsResultObject(Object resultObject){
        for (Result<T> r : results) {
            if (r.getResultObject().equals(resultObject)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return results.containsAll(c);
    }

    protected float sigmoid(float f) {
        double result = 0f;
        result = -1d + 2d / (1d + Math.exp(-2d * f / 0.6));
        return (float) (1d - result);
    }

    @Override
    public void invertScores() {
        List<Result<T>> invertedResults = new ArrayList<Result<T>>();
        for (Result<T> r : results) {
            invertedResults.add(r);
            r.setScore(1f - r.getScore());
        }

        results.clear();
        results.addAll(invertedResults);
    }

    // TODO - Precalculate maxScore in ResultSetDefaultImpl
    @Override
    public void normalizeScores() {
        double maxScore = 0;
        for (Result result : results) {
            if (result.getScore() > maxScore) {
                maxScore = result.getScore();
            }
        }
        if (maxScore == 0) // Avoid division by zero
        {
            return;
        }
        for (Result result : results) {
            result.setScore(result.getScore() / maxScore);
        }
    }

    @Override
    public double getScore(Object resultObject){
        for (Result<T> r: results) {
            if (r.getResultObject().equals(resultObject)){
                return r.getScore();
            }
        }
        return 0;   //the value was not found
    }

    @Override
    public int getSize() {
        return results.size();
    }

    @Override
    public Result<T> getFirst() {
        return results.first();
    }

    @Override
    public Result<T> getLast() {
        return results.last();
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public void clear() {
        results.clear();
        for (ResultSetListener lst : listeners) {
            lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.CLEARED, null));
        }
    }

    @Override
    public ResultSetDefaultImpl<T> getCopy() {
        List<Result> r = new ArrayList<Result>();
        r.addAll(results);
        return new ResultSetDefaultImpl(r);
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

    @Override
    public Iterator<Result<T>> iterator() {
        return results.iterator();
    }

    @Override
    public boolean registerListener(ResultSetListener listener) {
        return listeners.add(listener);
    }
}
