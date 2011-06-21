package pt.inevo.encontra.query;

import pt.inevo.encontra.query.criteria.Expression;
import pt.inevo.encontra.query.criteria.StorageCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * The generic internal structure for the parsed query.
 * @author Ricardo
 */
public class QueryParserNode {

    /**
     * If the query parsed is a compound one (for example, AND or OR), then this list represents the
     * children nodes of the predicate.
     */
    public List<QueryParserNode> childrenNodes;

    /**
     * The type of the predicate, for example, AND, OR, SIMILAR, etc.
     */
    public Class predicateType;

    /**
     * The expression representing the predicate
     */
    public Expression predicate;

    /**
     * The field name to be queried.
     */
    public String field;

    /**
     * If the field is specified, than this represents the object we want to use in the query.
     */
    public Object fieldObject;

    /**
     * Indicates if duplicate results are allowed.
     */
    public boolean distinct;

    /**
     * The limit number of results to be retrieved.
     */
    public int limit;

    /**
     * A storage criteria if any is available.
     */
    public StorageCriteria criteria;

    public QueryParserNode() {
        childrenNodes = new ArrayList<QueryParserNode>();
    }
}
