package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.IEntity;


/**
 * A class that represents a Index that can stored (saved)
 * @author ricardo
 */
public interface PersistentIndex<E extends IndexedObject> extends Index<E>, Storable{
    
}
