package pt.inevo.encontra.engine;

import pt.inevo.encontra.common.ResultSet;
import pt.inevo.encontra.common.ResultSetDefaultImpl;
import pt.inevo.encontra.index.search.AbstractSearcher;
import pt.inevo.encontra.query.CriteriaQuery;
import pt.inevo.encontra.query.Query;
import pt.inevo.encontra.query.QueryParser;
import pt.inevo.encontra.query.QueryParserNode;
import pt.inevo.encontra.query.criteria.Expression;
import pt.inevo.encontra.storage.IEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Interface for the query processor.
 *
 * @param <E> the type of objects the processor must know how to handle
 */
public abstract class IQueryProcessor<E extends IEntity> {

    /**
     * The result type class.
     */
    protected Class resultClass;

    /**
     * Parses the queries to an internal representation.
     */
    protected QueryParser queryParser;

    /**
     * Keep track of the searcher that holds this IQueryProcessor
     */
    protected AbstractSearcher topSearcher;

    /**
     * Map with the different processors for the available operators
     */
    private Map<Class<? extends Expression>, QueryOperatorProcessor> operatorsProcessors;

    /**
     * Logger for the Query Processors
     */
    protected Logger logger;

    public IQueryProcessor(){
        this(null);
    }

    public IQueryProcessor(Class clazz) {
        resultClass = clazz;
        operatorsProcessors = new HashMap<Class<? extends Expression>, QueryOperatorProcessor>();
    }

    public QueryParser getQueryParser() {
        return queryParser;
    }

    public void setQueryParser(QueryParser parser) {
        this.queryParser = parser;
    }

    public AbstractSearcher getTopSearcher() {
        return topSearcher;
    }

    public void setTopSearcher(AbstractSearcher topSearcher) {
        this.topSearcher = topSearcher;
    }

    public Map<Class<? extends Expression>, QueryOperatorProcessor> getOperatorsProcessors() {
        return operatorsProcessors;
    }

    public void setOperatorsProcessors(Map<Class<? extends Expression>, QueryOperatorProcessor> operatorsProcessors) {
        this.operatorsProcessors = operatorsProcessors;
    }

    /**
     * Takes an internal representation of the query and processes it.
     *
     * @param node the root node of the internal query representation
     * @return the results of the query
     */
    public ResultSet process(QueryParserNode node) {
        if (getOperatorsProcessors().containsKey(node.predicateType)){
            QueryOperatorProcessor operator = getOperatorsProcessors().get(node.predicateType);
            return operator.process(node);
        } else {
            logger.info("No operator was found for: " + node.predicateType.getName());
            return new ResultSetDefaultImpl<E>();
        }
    }

    /**
     * Method that breaks down the supplied CriteriaQuery into its internal
     * representation and sends it to the necessary searchers to retrieve the
     * results.
     * @param query must be an instance of a CriteriaQuery
     * @return the results of the query
     */
    public ResultSet search(Query query) {
        if (query instanceof CriteriaQuery) {
            QueryParserNode node = queryParser.parse(query);
            return process(node);
        } else {
            String message = "Query must be a CriteriaQuery";
            logger.log(Level.SEVERE, message);

            throw new RuntimeException(message);
        }
    }
}
