package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.Entry;
import java.io.Serializable;

/**
 * Meta-Object of the framework.
 * Represent an element that can be indexed in the framework.
 * This is the lower level of an object that can be indexed by the framework.
 * @param <ID>
 * @param <O>
 */
public class IndexedObject<ID extends Serializable, O> extends Entry<ID, O> {

    /**
     * The name of the indexed object.
     */
    protected String name;

    /**
     * The boost value of the indexed object.
     */
    protected double boost;

    public IndexedObject(ID id, String name, O obj, double boost) {
        super(id, obj);
        this.name = name;
        this.boost = boost;
    }

    public IndexedObject() {
    }

    public IndexedObject(ID identifier, O object) {
        super(identifier, object);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBoost() {
        return boost;
    }

    public void setBoost(double boost) {
        this.boost = boost;
    }

    /**
     * An IndexedObject is equal to another when they have the same id.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IndexedObject) {

            IndexedObject res = (IndexedObject) obj;
            if (id.equals(res.getId())) {
                return true;
            }
        }
        return false;
    }
}
