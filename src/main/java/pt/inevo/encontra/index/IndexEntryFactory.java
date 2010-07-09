package pt.inevo.encontra.index;



/**
 * Encontra IndexEntry Builder
 */
public interface IndexEntryFactory<O extends AbstractObject,E extends IndexEntry> {
    E createIndexEntry(O object);
    public abstract Object getObjectId(E entry);
}
