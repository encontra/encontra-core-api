/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.inevo.encontra.query;

import pt.inevo.encontra.index.AbstractObject;

/**
 * Approximate K Nearest Neighbour Query - uses stop conditions to accelerate
 * the Knn Query
 * @author ricardo
 */
public class AproximateKnnQuery extends KnnQuery {

    public AproximateKnnQuery(AbstractObject queryObject){
        super(queryObject);
    }
}
