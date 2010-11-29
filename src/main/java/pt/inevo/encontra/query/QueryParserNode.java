package pt.inevo.encontra.query;

import java.util.ArrayList;
import java.util.List;
import pt.inevo.encontra.query.criteria.Expression;

/**
 * The generic internal structure for the parsed query.
 * @author Ricardo
 */
public class QueryParserNode {
    
    public List<QueryParserNode> childrenNodes = new ArrayList<QueryParserNode>();
    public Class predicateType;
    public Expression predicate;
    public String field;
    public Object fieldObject;
    public boolean distinct;
}
