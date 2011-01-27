package pt.inevo.encontra.common;

/**
 * Represents a Generic result of the query execution.
 * @author ricardo
 */
public class Result<T> implements Comparable {

    protected T object;
    protected double score;
    
    public Result(T object) {
        this.object = object;
    }

    public T getResultObject() {
        return object;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score){
        this.score = score;
    }

    @Override
    public String toString() {
        return "(" + object.toString() + ", " + score + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Result) {
            Result res = (Result) obj;
            if (object.equals(res.getResultObject())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.object != null ? this.object.hashCode() : 0);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.score) ^ (Double.doubleToLongBits(this.score) >>> 32));
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Result)) {
            return 0;
        } else {
            int compareValue = (int) Math.signum(((Result) o).score - score);
            // Bugfix after hint from Kai Jauslin
            if (compareValue == 0 && !(object.equals(((Result) o).getResultObject())))
                compareValue = hashCode() - o.hashCode();
            return compareValue;
        }
    }
}
