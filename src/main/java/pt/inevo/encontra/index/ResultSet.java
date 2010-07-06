package pt.inevo.encontra.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The result set of a query.
 * Contains a list of Result's that can be iterated.
 * @author ricardo
 */
public class ResultSet {

    protected List<Result> results;
    protected Iterator<Result> iterator;

    public ResultSet(){
        this.results = new ArrayList<Result>();
        this.iterator = results.iterator();
    }

    public ResultSet(List<Result> results){
        this.results = results;
        this.iterator = results.iterator();
    }

    public Result [] getResults(){
        return results.toArray(new Result[0]);
    }

    public void setResults(List<Result> results){
        this.results.addAll(results);
    }

    public void addResult(Result r){
        results.add(r);
    }

    public void removeResult(Result r){
        results.remove(r);
    }

    public Result getFirst(){
        return results.get(0);
    }

    public Result getNext(){
        if (iterator.hasNext())
            return iterator.next();
        else return null;       //TO DO - must make this more robust
    }

    public Result getLast(){
        return results.get(results.size());
    }

    public int getSize(){
        return results.size();
    }

    public boolean hasNext(){
        return iterator.hasNext();
    }

    public boolean contains(Result res){
        return results.contains(res);
    }

    @Override
    public String toString(){
        String resultSet = "[";
        for (Result res: results){
            resultSet += res.toString() + ", ";
        }
        resultSet += "]";

        return resultSet;
    }
}
