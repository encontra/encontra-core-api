package pt.inevo.encontra.index;


import pt.inevo.encontra.storage.IEntity;

import java.io.Serializable;
import java.util.List;


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
    public List<IndexedObject> processBean(IEntity bean) throws IndexingException;


}
