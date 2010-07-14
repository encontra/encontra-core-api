package pt.inevo.encontra.storage;

import java.io.Serializable;


public interface ObjectStorage<ID extends Serializable,E extends IEntity<ID>> extends EntityStorage<ID,E>{
}
