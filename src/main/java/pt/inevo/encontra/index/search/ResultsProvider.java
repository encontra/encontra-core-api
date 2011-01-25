package pt.inevo.encontra.index.search;

import java.util.List;
import pt.inevo.encontra.index.Result;

/**
 * Iterates over the results of a query
 * @author Ricardo
 */
public interface ResultsProvider<T> {

    public Result<T> getNext();
    public List<Result<T>> getNext(int next);
}
