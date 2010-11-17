package pt.inevo.encontra.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import pt.inevo.encontra.index.IndexedObject;
import pt.inevo.encontra.index.ResultSet;
import pt.inevo.encontra.index.search.CombinedSearcher;
import pt.inevo.encontra.index.search.Searcher;
import pt.inevo.encontra.query.CriteriaQuery;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.QueryParser;
import pt.inevo.encontra.query.QueryParserNode;

/**
 * Interface for the query processor
 */
public abstract class QueryProcessor<E extends IndexedObject> extends CombinedSearcher<E> {

    protected QueryParser queryParser;

    public QueryProcessor() {
        super();
    }

    public QueryParser getQueryParser () {
        return queryParser;
    }

    public abstract ResultSet process(Stack<QueryParserNode> node);

    @Override
    public ResultSet search(Query query) {
        if (query instanceof CriteriaQuery) {
            //QueryParser must be set
            Stack<QueryParserNode> nodes = queryParser.parse(query);
            return process(nodes);
        }
        return new ResultSet();
    }
}
