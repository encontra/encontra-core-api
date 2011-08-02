package pt.inevo.encontra.query.criteria;

import pt.inevo.encontra.query.CriteriaQuery;
import pt.inevo.encontra.query.Order;
import pt.inevo.encontra.query.Predicate;

/**
 * Generic interface for the Criteria Query Builder.
 * All Criteria Builders should implement this interface.
 * @param <T>
 */
public interface CriteriaBuilder<T> {

    <T> CriteriaQuery<T> createQuery(Class<T> resultClass);

    Predicate and(Predicate... restrictions);

    Predicate and(Expression<Boolean> x, Expression<Boolean> y);

    CriteriaQuery<Object> createQuery();

    Predicate not(Expression<Boolean> restriction);

    Predicate or(Predicate... restrictions);

    Predicate or(Expression<Boolean> x, Expression<Boolean> y);

    Order asc(Expression<?> x);

    Order desc(Expression<?> x);

    Predicate equal(Expression<?> x, Expression<?> y);

    Predicate equal(Expression<?> x, Object y);

    Predicate similar(Expression<?> x, Object y);

    Predicate similar(Object x, Object y);
}
