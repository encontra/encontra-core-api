package pt.inevo.encontra.index;

/**
 * Represents a Generic result of the query execution.
 * @author ricardo
 */
public class Result<T> implements Comparable {

    protected T object;
    protected double similarity; //only used in similarity related queries
    
    public Result(T object) {
        this.object = object;
    }

    public T getResult() {
        return object;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity){
        this.similarity=similarity;
    }

    @Override
    public String toString() {
        return "(" + object.toString() + ", " + similarity + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Result) {

            Result res = (Result) obj;
            if (object.equals(res.getResult())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.object != null ? this.object.hashCode() : 0);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.similarity) ^ (Double.doubleToLongBits(this.similarity) >>> 32));
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Result)) {
            return 0;
        } else {
            int compareValue = (int) Math.signum(((Result) o).similarity - similarity);
            // Bugfix after hint from Kai Jauslin
            if (compareValue == 0 && !(object.equals(((Result) o).getResult())))
                compareValue = hashCode() - o.hashCode();
            return compareValue;
        }
    }
}
