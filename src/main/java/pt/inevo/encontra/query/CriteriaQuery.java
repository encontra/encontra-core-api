package pt.inevo.encontra.query;

import pt.inevo.encontra.query.criteria.Expression;

/**
 * Criteria Query - Query for combining Criteria
 * @author ricardo
 */
public interface CriteriaQuery<T> extends Query {

    public CriteriaQuery<T> where(Expression<Boolean> restriction);

    public CriteriaQuery<T> where(Predicate... restrictions);

    public CriteriaQuery<T> distinct(boolean distinct);

    public CriteriaQuery<T> orderBy(Order... orders);

    public Class<T> getResultType();

    public Expression<Boolean> getRestriction();

    public Predicate getRestrictions();

    public <X> Path<X> from(Class<X> cls);
}
