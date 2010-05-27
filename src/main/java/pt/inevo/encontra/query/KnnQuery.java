/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.query;

import pt.inevo.encontra.index.AbstractObject;
import pt.inevo.encontra.index.ResultSet;

/**
 * K neighrest neighour Query - Retrieve the K most similar elements
 * @author ricardo
 */
public class KnnQuery implements Query {

    protected AbstractObject queryObject;

    public KnnQuery(AbstractObject queryObject){
        this.queryObject = queryObject;
    }

    public AbstractObject getQueryObject(){
        return queryObject;
    }

    @Override
    public ResultSet execute() {
        return new ResultSet(null);
    }

    @Override
    public QueryType getType() {
        return QueryType.KNN;
    }

}
