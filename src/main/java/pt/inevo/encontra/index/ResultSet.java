package pt.inevo.encontra.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The result set of a query.
 * Contains a list of Result's that can be iterated.
 * @author ricardo
 */
public class ResultSet<T> implements Collection<Result<T>> {

    private List<Result<T>> results;

    public ResultSet(){
        this(new ArrayList<Result<T>>());
    }

    public ResultSet(List<Result<T>> results){
        this.results=results;
    }

    public boolean contains(Result<T> res){
        return results.contains(res);
    }

    public int indexOf(Result<T> res){
        return results.indexOf(res);
    }

    public Result<T> get(int index){
        return results.get(index);
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
        return results.get(position).getSimilarity();
    }

    public void invertScores(){
        for (Result result : results) {
            result.setSimilarity(1f - result.getSimilarity());
        }
    }

    public void normalizeScores(double maxDistance){
        for (Result result : results) {
            result.setSimilarity(result.getSimilarity() / maxDistance);
        }
    }

    @Override
    public int size() {
        return results.size();
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return results.contains(o);
    }

    @Override
    public Iterator<Result<T>> iterator() {
        return results.iterator();
    }

    @Override
    public T[] toArray() {
        return (T[]) results.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return  (T[]) results.toArray(a);
    }

    @Override
    public boolean add(Result<T> tResult) {
        return results.add(tResult);
    }

    @Override
    public boolean remove(Object o) {
        return results.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return results.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Result<T>> c) {
        return results.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return results.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return results.retainAll(c);
    }

    @Override
    public void clear() {
        results.clear();
    }
}
