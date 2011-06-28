package pt.inevo.encontra.index.search;

import pt.inevo.encontra.common.Result;

import java.util.List;

/**
 * Iterates over the results of a query
 * @author Ricardo
 * @param <T> the type of results
 */
public interface ResultsProvider<T> {

    public Result<T> getNext();
    public List<Result<T>> getNext(int next);
}
