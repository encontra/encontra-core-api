package pt.inevo.encontra.common;

import java.util.Collection;

/**
 * Generic interface for the ResultSet.
 * Holds a set of Results of type <T>.
 * @author Ricardo
 */
public interface ResultSet<T> extends Iterable<Result<T>> {

    /**
     * Adds a new Result to the ResultSet.
     * @param result the result to add
     * @return true if the operation is successfully finished, or false otherwise
     */
    public boolean add(Result<T> result);

    /**
     * Adds a collection of Results to the ResultSet.
     * @param c the results to be added
     * @return true if the operation is successfully finished (all the Results are added), or false otherwise
     */
    public boolean addAll(Collection<Result<T>> c);

    /**
     * Removes all the Results from the ResultSet.
     */
    public void clear();

    /**
     * Checks if a Result is contained in the ResultSet.
     * @param o the object to be checked
     * @return true if the Result is contained, or false otherwise
     */
    public boolean contains(Result<T> o);

    /**
     * Checks if a resultObject (the object inside a Result) exists in any of the available Results.
     * This helps handling operations looking for the same resultObject, but with different scores.
     * @param resultObject the object to be checked
     * @return true if a Result contains the object, or false otherwise
     */
    public boolean containsResultObject(Object resultObject);

    /**
     * Checks if a collection of Results are contained in the ResultSet.
     * @param c the Results to be checked
     * @return true if all the Results are contained, or false otherwise
     */
    public boolean containsAll(Collection<?> c);

    /**
     * Checks if the ResultSet is empty.
     * @return true if the ResultSet has no elements, or false otherwise
     */
    public boolean isEmpty();

    /**
     * Inverts the scores of the Results contained in the ResultSet.
     */
    public void invertScores();

    /**
     * Normalizes the score of all the Results in the ResultSet.
     */
    public void normalizeScores();

    /**
     * Gets the score of a Result given the contained resultObject.
     * @param resultObject the object  contained in a ResultSet
     * @return the score of the desired Result
     */
    public double getScore(Object resultObject);

    /**
     * Removes a Result from the ResultSet.
     * @param o the Result to be removed
     * @return true if the removal is successfully finished, or false otherwise
     */
    public boolean remove(Result<T> o);

    /**
     * Removes a collection of Results from the ResultSet.
     * @param c the list of results to be removed
     * @return true if all the results are successfully removed, or false otherwise
     */
    public boolean removeAll(Collection<?> c);

    /**
     * Gets the number of Results in the ResultSet.
     * @return the size of the ResultSet
     */
    public int getSize();

    /**
     * Sets the maximum size of Results in the ResultSet. Constraints the maximum number of Results a ResultSet can hold.
     * @param newSize
     */
    public void setMaxSize(int newSize);

    /**
     * Returns a exact copy of the current ResultSet.
     * @return a copy of the ResultSet
     */
    public ResultSet<T> getCopy();

    /**
     * If the ResultSet is intended to be ordered accordingly to a seed, then this gets the seed of the ResultSet.
     * @return the Result the acts as the seed of the ResultSet
     */
    public Result<T> getSeed();

    /**
     * Gets the first Result of the ResultSet.
     * @return the first Result in the ResultSet
     */
    public Result<T> getFirst();

    /**
     * Gets the last Result of the ResultSet.
     * @return the last Result in the ResultSet
     */
    public Result<T> getLast();

    /**
     * Gets the first n Results of the ResultSet
     * @param n the desired number of Results
     * @return a new ResultSet with the first n Results of the current ResultSet
     */
    public ResultSet<T> getFirstResults(int n);

    /**
     * Sets the Owner of the ResultSet. Can be used to identify the Result, or to pass in the ResultSetEvents.
     * @param o the owner of the ResultSet
     */
    public void setOwner(Object o);

    /**
     * Gets the Owner of the ResultSet.
     * @return the object that owns the ResultSet
     */
    public Object getOwner();

    /**
     * Listener Method. Registers a ResultSetListener to handle events triggered by the ResultSet.
     * @param listener the listener to be registered
     * @return true if the listener is successfully registered, of false otherwise
     */
    public boolean registerListener(ResultSetListener<T> listener);
}
