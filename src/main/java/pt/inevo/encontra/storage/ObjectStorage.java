package pt.inevo.encontra.storage;

import java.io.Serializable;

/**
 * Interface for the Storage of objects.
 * @author Ricardo
 * @param <ID>
 * @param <E>
 */
public interface ObjectStorage<ID extends Serializable,E extends IEntity<ID>> extends EntityStorage<ID,E>{
}
