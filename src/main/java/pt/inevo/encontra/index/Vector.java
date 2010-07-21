package pt.inevo.encontra.index;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Vector for numeric types
 * @param <T>
 */
public class Vector<T extends Number> implements Cloneable, Iterable<T>{

    protected int size;
    protected T[] values;
    protected Class<T> typeT;

    public Vector(Class<T> type, int size) {
        this.size = size;
        this.values = (T[])Array.newInstance(type,size);
        this.typeT = type;
    }

    /** Construct a new instance using provided values */
    public Vector(Class<T> type, T[] values) {
        this.values = values;
        this.size = values.length;
        this.typeT = type;
    }

    public int size() {
        return this.size;
    }

    public T get(int index) {
        return values[index];
    }

    public void set(int index, T value) {
        values[index] = value;
    }

    public Vector normalize() {
        return divide(Math.sqrt(dotSelf()));
    }

    public Vector normalize(double power) {
        return divide(norm(power));
    }

    public Vector divide(double x) {
        if (x == 1.0) {
            return clone();
        }
        Vector result = clone();
        Iterator<T> iter = result.iterateNonZero();
        int index=0;
        while (iter.hasNext()) {
            T element = iter.next();
            set(index,(T) new Double(element.doubleValue() / x));
            index++;
        }
        return result;
    }

    public double norm(double power) {
        if (power < 0.0) {
            throw new IllegalArgumentException("Power must be >= 0");
        }
        // we can special case certain powers
        if (Double.isInfinite(power)) {
            double val = 0.0;
            Iterator<T> iter = this.iterateNonZero();
            while (iter.hasNext()) {
                val = Math.max(val, Math.abs(iter.next().doubleValue()));
            }
            return val;
        } else if (power == 2.0) {
            return Math.sqrt(dotSelf());
        } else if (power == 1.0) {
            double val = 0.0;
            Iterator<T> iter = this.iterateNonZero();
            while (iter.hasNext()) {
                val += Math.abs(iter.next().doubleValue());
            }
            return val;
        } else if (power == 0.0) {
            // this is the number of non-zero elements
            double val = 0.0;
            Iterator<T> iter = this.iterateNonZero();
            while (iter.hasNext()) {
                val += iter.next().doubleValue() == 0 ? 0 : 1;
            }
            return val;
        } else {
            double val = 0.0;
            Iterator<T> iter = this.iterateNonZero();
            while (iter.hasNext()) {
                T element = iter.next();
                val += Math.pow(element.doubleValue(), power);
            }
            return Math.pow(val, 1.0 / power);
        }
    }
    @Override
    public Vector<T> clone() {
        return new Vector<T>(typeT, values.clone());
    }

    /**
     * Determines whether this {@link Vector} represents the same logical vector as another
     * object. Two {@link Vector}s are equal (regardless of implementation) if the value at
     * each index is the same, and the cardinalities are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector) {
            return Arrays.equals(values, ((Vector) o).values);
        }
        return super.equals(0);
    }

    public double dotSelf() {
        double result = 0.0;
        int max = size();
        for (int i = 0; i < max; i++) {
            double value = this.get(i).doubleValue();
            result += value * value;
        }
        return result;
    }

    public double dot(Vector x) {
        assert(size() == x.size());

        if (this == x) {
            return dotSelf();
        }

        double result = 0;

        for (int i = 0; i < x.size(); i++) {
            result += this.values[i].doubleValue() * x.get(i).doubleValue();
        }
        return result;

    }

    public double getLengthSquared() {
        return dotSelf();
    }

    public double getDistanceSquared(Vector v) {
        assert(size == v.size());

        return getLengthSquared() + v.getLengthSquared() - 2 * this.dot(v);
    }
    
    /**
     * Returns an iterator that traverses this Vector from 0 to cardinality-1, in that order.
     */
    public Iterator<T> iterateNonZero() {
        return new NonZeroIterator();
    }

    @Override
    public Iterator<T> iterator() {
        return new AllIterator();
    }

    private final class NonZeroIterator implements Iterator<T> {

        private int index = 0;

        private NonZeroIterator() {
            goToNext();
        }

        private void goToNext() {
            while (index < size() && values[index].doubleValue() == 0.0) {
                index++;
            }
        }

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public T next() {
            if (index >= size()) {
                throw new NoSuchElementException();
            } else {
                T el=values[index];
                index++;
                goToNext();
                return el;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final class AllIterator implements Iterator<T> {
        private int index = -1;
        private AllIterator() {
        }

        @Override
        public boolean hasNext() {
            return index+1 < size();
        }

        @Override
        public T next() {
            if (index+1 >= size()) {
                throw new NoSuchElementException();
            } else {
                index++;
                return values[index];
            }
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}