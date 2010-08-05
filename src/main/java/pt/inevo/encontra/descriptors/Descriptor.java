package pt.inevo.encontra.descriptors;

import java.io.Serializable;
import pt.inevo.encontra.common.distance.HasDistance;
import pt.inevo.encontra.storage.IEntry;

public interface Descriptor extends HasDistance<Descriptor>, IEntry, Serializable {
    
    public String getName();
}