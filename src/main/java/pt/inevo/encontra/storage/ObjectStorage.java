package pt.inevo.encontra.storage;

import java.io.Serializable;


public interface ObjectStorage<ID extends Serializable,E extends StorableObject<ID, ?, ?>> extends EntryStorage<ID,E>{
}
