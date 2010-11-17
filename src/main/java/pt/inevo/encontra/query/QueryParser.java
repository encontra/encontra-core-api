package pt.inevo.encontra.query;

import java.util.Stack;
import pt.inevo.encontra.query.criteria.ExpressionVisitor;

/**
 * The interface for the Query Parser mechanism.
 * @author Ricardo
 */
public interface QueryParser extends ExpressionVisitor {

    /**
     * Parses a query and converts it to an internal representation.
     * @param query the query to be parsed
     * @return the structured that represents the parsed query
     */
    public Stack<QueryParserNode> parse(Query query);
}
