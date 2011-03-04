package pt.inevo.encontra.query;

import java.util.ArrayList;
import java.util.List;
import pt.inevo.encontra.query.criteria.Expression;
import pt.inevo.encontra.query.criteria.StorageCriteria;

/**
 * The generic internal structure for the parsed query.
 * @author Ricardo
 */
public class QueryParserNode {
    
    public List<QueryParserNode> childrenNodes;
    public Class predicateType;
    public Expression predicate;
    public String field;
    public Object fieldObject;
    public boolean distinct;
    public int limit;
    public StorageCriteria criteria;

    public QueryParserNode() {
        childrenNodes = new ArrayList<QueryParserNode>();
    }
}
