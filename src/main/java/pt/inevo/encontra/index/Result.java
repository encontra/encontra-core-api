package pt.inevo.encontra.index;

/**
 * Represents a Generic result of the query execution.
 * @author ricardo
 */
public class Result {

    protected AbstractObject object;
    protected double similarity; //only used in similarity related queries

    public Result(AbstractObject object) {
        this.object = object;
    }

    public AbstractObject getResultObject() {
        return object;
    }

    public double getSimilarity() {
        return similarity;
    }

    @Override
    public String toString() {
        return "(" + object.toString() + ", " + similarity + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Result) {

            Result res = (Result) obj;
            if (object.equals(res.getResultObject()) && similarity == res.getSimilarity()) {
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
}
