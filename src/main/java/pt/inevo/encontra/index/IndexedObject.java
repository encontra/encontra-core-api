package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.Entry;
import java.io.Serializable;

/**
 * Meta-Object of the framework.
 * Represent an element that can be indexed in the framework.
 * @author ricardo
 */
public class IndexedObject<ID extends Serializable, O> extends Entry<ID, O> {

    protected String name;
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
