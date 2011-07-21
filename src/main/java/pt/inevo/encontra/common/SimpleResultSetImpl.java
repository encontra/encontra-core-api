package pt.inevo.encontra.common;

import pt.inevo.encontra.common.distance.HasDistance;

import java.util.*;

/**
 * A result set that keeps the results ordered by their insertion ordered
 * @author Ricardo
 */
public class SimpleResultSetImpl<T> implements ResultSet<T> {

    private List<ResultSetListener> listeners;
    private List<Result<T>> results;
    private Result<T> seedResult, worseResult;
    private double lowestScore;
    private int maxSize;
    private Object owner;

    public SimpleResultSetImpl() {
        this(new ArrayList<Result<T>>());
    }

    public SimpleResultSetImpl(List<Result<T>> results) {
        this.results = new ArrayList<Result<T>>();
        this.results.addAll(results);
        listeners = new ArrayList<ResultSetListener>();
    }

    public SimpleResultSetImpl(Result<T> seed, int size) {
        this.seedResult = seed;
        this.maxSize = size;

        listeners = new ArrayList<ResultSetListener>();
        this.results = new ArrayList<Result<T>>();
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
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
                worseResult = results.get(results.size()-1);

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
                    worseResult = results.get(results.size() - 1);
                    
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
                    worseResult = results.get(results.size() - 1);
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
        return results.get(0);
    }

    @Override
    public Result<T> getLast() {
        return results.get(results.size() - 1);
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
    public SimpleResultSetImpl<T> getCopy() {
        List<Result> r = new ArrayList<Result>();
        r.addAll(results);
        return new SimpleResultSetImpl(r);
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
        return new SimpleResultSetImpl(r);
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
