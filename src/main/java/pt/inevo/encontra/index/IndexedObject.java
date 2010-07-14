package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;

import java.io.Serializable;


/**
 * Meta-Object of the framework
 * @author ricardo
 */
public class IndexedObject<ID extends Serializable,O> implements IEntry<ID,O> {

    protected String name;
    protected O object=null;
    protected ID id=null;
    public double boost;


    public IndexedObject(ID id,String name, O obj, double boost){
        this.id=id;
        this.name=name;
        this.object=obj;
        this.boost=boost;
    }
    public IndexedObject(){}

    public IndexedObject(ID identifier, O object){
        this.id = identifier;
        this.object = object;
    }

    /**
     * Gets the object of this object.
     * @return the object
     */
    public O getValue() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setValue(O object) {
        this.object = object;
    }

    /**
     * @return the id
     */
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id=id;
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
