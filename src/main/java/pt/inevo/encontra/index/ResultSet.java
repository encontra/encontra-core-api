/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.index;

import java.util.Iterator;
import java.util.List;

/**
 * The result set of a query.
 * @author ricardo
 */
public class ResultSet {

    protected List<Result> results;
    protected Iterator<Result> iterator;

    public ResultSet(List<Result> results){
        this.results = results;
        this.iterator = results.iterator();
    }

    public Result [] getResults(){
        return results.toArray(new Result[0]);
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
