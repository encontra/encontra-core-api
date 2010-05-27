/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.index;

import pt.inevo.encontra.query.Query;

/**
 * Searchar through a generic index.
 * @author ricardo
 */
public class IndexSearcher {

    protected Index index;

    public IndexSearcher(Index idx){
        this.index = idx;
    }

    public Result[] search(Query query){
        /*if (index.getClass().equals(PersistentIndex.class)){
            return index.search(query);
        }*/
        return new Result [0];
    }

}
