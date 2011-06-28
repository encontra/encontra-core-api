package pt.inevo.encontra.storage;

import java.io.Serializable;

/**
 * Persistence interface for objects Storage.
 * @author Ricardo
 * @param <ID> object's id primary key type
 * @param <E> the storable object type
 */
public interface ObjectStorage<ID extends Serializable,E extends IEntity<ID>> extends EntityStorage<ID,E>{
}
