package pt.inevo.encontra.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The result set of a query.
 * Contains a list of Result's that can be iterated.
 * @author ricardo
 */
public class ResultSet extends ArrayList<Result>{

    public ResultSet(){
        super();
    }

    public ResultSet(List<Result> results){
        super();
        addAll(results);
    }


    @Override
    public String toString(){
        String resultSet = "[";
        for (Result res: this){
            resultSet += res.toString() + ", ";
        }
        resultSet += "]";

        return resultSet;
    }

    private float sigmoid(float f) {
        double result = 0f;
        result = -1d + 2d / (1d + Math.exp(-2d * f / 0.6));
        return (float) (1d - result);
    }

        /**
     * Returns the score of the document at given position.
     * Please note that the score in this case is a distance,
     * which means a score of 0 denotes the best possible hit.
     * The result list starts with position 0 as everything
     * in computer science does.
     *
     * @param position defines the position
     * @return the score of the document at given position. The lower the better (its a distance measure).
     */
    public double getScore(int position) {
        return get(position).getSimilarity();
    }

    public void invertScores(){
        for (Result result : this) {
            result.setSimilarity(1f - result.getSimilarity());
        }
    }

    public void normalizeScores(float maxDistance){
        for (Result result : this) {
             result.setSimilarity(result.getSimilarity() / maxDistance);
        }
    }
}
