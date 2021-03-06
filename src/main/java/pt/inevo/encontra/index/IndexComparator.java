package pt.inevo.encontra.index;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Object that compares the elements in the Index.
 * @author ricardo
 * @param <K>
 * @param <O>
 */
public interface IndexComparator<K extends Serializable, O extends IndexedObject>
        extends Serializable, Comparator<K> {

    /**
     * Compares its two arguments for order. Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * @param k the key to compare
     * @param o the object to be compared
     * @return a negative integer, zero, or a positive integer as the
     * 	       first argument is less than, equal to, or greater than the
     *	       second.
     * @throws ClassCastException if the arguments' types prevent them from
     * 	       being compared by this comparator.
     */
    public int indexCompare(K k, O o);

    /**
     * Returns the key (used for comparison) from an indexed object.
     * @param object the indexed (full) object
     * @return the extracted key
     */
    public K extractKey(O object);

    /**
     *
     * Indicates whether some other object is &quot;equal to&quot; this
     * comparator.  This method must obey the general contract of
     * {@link Object#equals(Object)}.  Additionally, this method can return
     * <tt>true</tt> <i>only</i> if the specified object is also a comparator
     * and it imposes the same ordering as this comparator.  Thus,
     * <code>comp1.equals(comp2)</code> implies that <tt>sgn(comp1.indexCompare(o1,
     * o2))==sgn(comp2.indexCompare(o1, o2))</tt> for every object reference
     * <tt>o1</tt> and <tt>o2</tt>.<p>
     *
     * Note that it is <i>always</i> safe <i>not</i> to override
     * <tt>Object.equals(Object)</tt>.  However, overriding this method may,
     * in some cases, improve performance by allowing programs to determine
     * that two distinct comparators impose the same order.
     *
     * @param   obj   the reference object with which to compare.
     * @return  <code>true</code> only if the specified object is also
     *		a comparator and it imposes the same ordering as this
     *		comparator.
     * @see Object#equals(Object)
     * @see Object#hashCode()
     */
    @Override
    public boolean equals(Object obj);
}