package pt.inevo.encontra.query.criteria;

import pt.inevo.encontra.query.Query;

/**
 * Type for query expressions.
 * @param <T> the type of the expression
 */
public interface Expression<T> extends Query {

    /**
     * Accept visit from the given visitor. The receiver is responsible
     * to propagate the visitor to the constituent sub-nodes if any.
     *
     * @param visitor a processor to walk the nodes of a tree.
     */
    void acceptVisit(ExpressionVisitor visitor);
}