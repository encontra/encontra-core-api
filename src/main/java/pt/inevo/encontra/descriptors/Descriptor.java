package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.common.distance.HasDistance;
import pt.inevo.encontra.storage.IEntry;

import java.io.Serializable;

/**
 * Generic interface for a Descriptor.
 * A descriptor must be serializable, have a name, and perform distance calculations to other descriptors.
 */
public interface Descriptor extends HasDistance<Descriptor>, IEntry, Serializable {

    /**
     * Gets the name of the Descriptor.
     * @return the name of the descriptor
     */
    public String getName();
}