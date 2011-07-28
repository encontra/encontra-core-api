package pt.inevo.encontra.engine;

import pt.inevo.encontra.common.ResultSet;
import pt.inevo.encontra.query.QueryParserNode;

public abstract class QueryOperatorProcessor<T> {

    protected IQueryProcessor queryProcessor;
    protected String name;

    public abstract ResultSet<T> process(QueryParserNode node);

    public String getName() {
        return name;
    }

    public IQueryProcessor getQueryProcessor(){
        return queryProcessor;
    }

    public void setQueryProcessor(IQueryProcessor processor) {
        this.queryProcessor = processor;
    }

}
