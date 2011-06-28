package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.IEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Factory interface for creating indexed objects.
 */
public interface IndexedObjectFactory {

    class IndexedField{
        public Serializable id;
        public String name;
        public Object object;
        public double boost;
        public IndexedField(Serializable id,String name, Object obj, double boost){
            this.id=id;
            this.name=name;
            this.object=obj;
            this.boost=boost;
        }
    }

    /**
     * Given an instance of IEntity, decomposes the entity into several indexed objects, accordingly to the
     * indexed fields.
     * @param bean the entity to be decomposed
     * @return a list of indexed objects
     * @throws IndexingException if an error occurs when decomposing the bean
     */
    public List<IndexedObject> processBean(IEntity bean) throws IndexingException;
}