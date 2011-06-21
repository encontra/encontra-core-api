package pt.inevo.encontra.query;

import pt.inevo.encontra.query.criteria.Expression;
import pt.inevo.encontra.query.criteria.StorageCriteria;

/**
 * Criteria Query - Query for combining Criteria
 * @author ricardo
 */
public interface CriteriaQuery<T> extends Query {

    /**
     * Sets the where expression for the CriteriaQuery.
     * @param restriction the where expression of the query
     * @return a CriteriaQuery composed with the supplied restriction
     */
    public CriteriaQuery<T> where(Expression<Boolean> restriction);

    /**
     * Sets the where predicate for the CriteriaQuery.
     * @param restrictions a list of predicates to be composed in the where predicate
     * @return
     */
    public CriteriaQuery<T> where(Predicate... restrictions);

    /**
     * Allow or disable duplicate results.
     * @param distinct boolean value that indicates if duplicate results are allowed
     * @return a CriteriaQuery with the distinct property set
     */
    public CriteriaQuery<T> distinct(boolean distinct);

    /**
     * Sets the maximum number of results that the result of processing this query should have.
     * @param value the maximum number of results
     * @return a CriteriaQuery with the limit value set
     */
    public CriteriaQuery<T> limit(int value);

    /**
     * Sets the ordering for the CriteriaQuery.
     * @param orders a list of ordering expressions
     * @return a CriteriaQuery with the order by clause set
     */
    public CriteriaQuery<T> orderBy(Order... orders);

    /**
     * Gets the class that represents the result type of the CriteriaQuery.
     * @return a Class that represents the result type of the CriteriaQuery
     */
    public Class<T> getResultType();

    /**
     * Gets the restrictions used in the CriteriaQuery under the form of a Expression.
     * @return
     */
    public Expression<Boolean> getRestriction();

    /**
     * Gets the restrictions used in the CriteriaQuery.
     * @return a predicate that represents the restrictions to be used in the CriteriaQuery
     */
    public Predicate getRestrictions();

    /**
     * Checks if the results of the CriteriaQuery should have duplicates.
     * @return true if duplicates are allowed, of false otherwise
     */
    public boolean isDistinct();

    /**
     * Gets the limit specified in the CriteriaQuery.
     * @return the maximum number of results that the processing of the query should return
     */
    public int getLimit();

    /**
     * Gets the StorageCriteria specified in the query (if any is available)
     * @return
     */
    public StorageCriteria getCriteria();

    /**
     * Sets the StorageCriteria for the CriteriaQuery.
     * @param criteria the storage criteria to be set
     * @return a compound CriteriaQuery with the supplied StorageCriteria
     */
    public CriteriaQuery setCriteria(StorageCriteria criteria);

    /**
     * Gets a Path from a given Class.
     * @param cls
     * @param <X>
     * @return
     */
    public <X> Path<X> from(Class<X>  cls);
}
