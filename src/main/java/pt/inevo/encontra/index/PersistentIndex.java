package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.IEntity;
import pt.inevo.encontra.storage.IEntry;


/**
 * A class that represents a Index that can stored (saved)
 * @author ricardo
 */
public interface PersistentIndex<E extends IEntry> extends Index<E>, Storable{
    
}
