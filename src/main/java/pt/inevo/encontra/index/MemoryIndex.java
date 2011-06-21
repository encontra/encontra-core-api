package pt.inevo.encontra.index;

import pt.inevo.encontra.storage.IEntry;

/**
 * Represents a Index that only exists in the memory.
 * @author ricardo
 */
public interface MemoryIndex<E extends IEntry> extends Index<E> {
}
