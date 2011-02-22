package pt.inevo.encontra.common;

import pt.inevo.encontra.common.distance.HasDistance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The result set of a query.
 * Contains a list of Result's that can be iterated.
 *
 * @author Ricardo
 */
public class ResultSetDefaultImpl<T> implements ResultSet<T> {

    private List<ResultSetListener> listeners;
    private SortedSet<Result<T>> results;
    private Result<T> seedResult, worseResult;
    private double lowestScore;
    private int maxSize;
    private Object owner;

    public ResultSetDefaultImpl() {
        this(new ArrayList<Result<T>>());
    }

    public ResultSetDefaultImpl(List<Result<T>> results) {
        this.results = Collections.synchronizedSortedSet(new TreeSet<Result<T>>(new ResultComparator(null)));
        this.results.addAll(results);
        listeners = new ArrayList<ResultSetListener>();
    }

    public ResultSetDefaultImpl(Result<T> seed, int size) {
        this.seedResult = seed;
        this.maxSize = size;

        listeners = new ArrayList<ResultSetListener>();
        results = Collections.synchronizedSortedSet(new TreeSet<Result<T>>(new ResultComparator(seed)));
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    class ResultComparator implements Comparator<Result<T>> {

        private Result<T> seed;

        ResultComparator (Result<T> seed) {
            this.seed = seed;
        }

        @Override
        public int compare(Result<T> o1, Result<T> o2) {
            try {
                //checks the order
                double dist1 = 0, dist2 = 0;
                if (seed != null) {
                    if (o1.getResultObject() instanceof HasDistance && o2.getResultObject() instanceof HasDistance){
                        dist1 = ((HasDistance) seed.getResultObject()).getDistance(o1.getResultObject());
                        dist2 = ((HasDistance) seed.getResultObject()).getDistance(o2.getResultObject());
                    } else {
                        dist1 = seed.getScore() - o1.getScore();
                        dist2 = seed.getScore() - o2.getScore();
                    }

                    if (dist1 == dist2) {   //have the same distance but they don't have the same object
                        return -1;
                    }
                    else return new Double(dist1).compareTo(new Double(dist2));
                } else {
                    if (o1.getScore() == o2.getScore()) {
                        return -1;
                    }
                    else return new Double(o1.getScore()).compareTo(new Double(o2.getScore()));
                }

            } catch (Exception ex) {
                System.out.println("[Error]: Possible reason " + ex.toString());
                return 0;
            }
        }
    }

    @Override
    public boolean add(Result<T> tResult) {

        if (maxSize == 0) {
            for (ResultSetListener lst : listeners) {
                lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.ADDED, tResult, owner));
            }
            return results.add(tResult);
        } else {
            if (results.size() < maxSize) {
                if (!results.add(tResult)) {
                    System.out.println("That should not have happened.");
                }
                worseResult = results.last();

                if (seedResult.getResultObject() instanceof HasDistance) {
                    lowestScore = ((HasDistance)seedResult.getResultObject()).getDistance(worseResult.getResultObject());
                } else {
                    lowestScore = seedResult.getScore() - worseResult.getScore();
                }
                for (ResultSetListener lst : listeners) {
                    lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.ADDED, tResult, owner));
                }
                return true;
            } else {
                double score = 0;
                if (seedResult.getResultObject() instanceof HasDistance) {
                    score = ((HasDistance)seedResult.getResultObject()).getDistance(tResult.getResultObject());
                } else {
                    score = seedResult.getScore() - tResult.getScore();
                }

                if (score <= lowestScore) {
                    return false;
                } else {
                    boolean removed = remove(worseResult);
                    results.add(tResult);
                    worseResult = results.last();
                    
                    if (seedResult.getResultObject() instanceof HasDistance) {
                        lowestScore = ((HasDistance)seedResult.getResultObject()).getDistance(worseResult.getResultObject());
                    } else {
                        lowestScore = seedResult.getScore() - worseResult.getScore();
                    }
                    
                    for (ResultSetListener lst : listeners) {
                        lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.ADDED, tResult, owner));
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
                lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.REMOVED, r, owner));
            }
            return results.remove(r);
        } else {

            if (results.contains(r)) {
                boolean value = results.remove(r);
                if (results.size() > 0) {
                    worseResult = results.last();
                    try {

                        if (seedResult.getResultObject() instanceof HasDistance){
                            lowestScore = ((HasDistance)seedResult.getResultObject()).getDistance(worseResult.getResultObject());
                        } else {
                            lowestScore = seedResult.getScore() - worseResult.getScore();
                        }

                        
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
    public boolean containsResultObject(Object resultObject) {
        for (Result<T> r : results) {
            if (r.getResultObject().equals(resultObject)) {
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
    public double getScore(Object resultObject) {
        for (Result<T> r : results) {
            if (r.getResultObject().equals(resultObject)) {
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
    public void setMaxSize(int newSize) {
        this.maxSize = newSize;
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
    public Result<T> getSeed() {
        return seedResult;
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public void clear() {
        results.clear();
        for (ResultSetListener lst : listeners) {
            lst.handleEvent(new ResultSetEvent(ResultSetEvent.Event.CLEARED, null, owner));
        }
    }

    @Override
    public ResultSetDefaultImpl<T> getCopy() {
        List<Result> r = new ArrayList<Result>();
        r.addAll(results);
        return new ResultSetDefaultImpl(r);
    }

    @Override
    public ResultSet<T> getFirstResults(int value){
        List<Result<T>> r = new ArrayList<Result<T>>();
        Iterator<Result<T>> it = results.iterator();
        int howMany = 0;
        while(it.hasNext() && howMany < value){
            r.add(it.next());
            howMany++;
        }
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
