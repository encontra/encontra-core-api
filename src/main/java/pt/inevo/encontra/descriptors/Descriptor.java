package pt.inevo.encontra.descriptors;

import pt.inevo.encontra.common.distance.HasDistance;
import pt.inevo.encontra.storage.IEntry;

public interface Descriptor extends HasDistance<Descriptor>, IEntry {
    
    public String getName();

}