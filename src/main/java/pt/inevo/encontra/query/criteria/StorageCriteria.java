package pt.inevo.encontra.query.criteria;

public class StorageCriteria {

    public final String arg;
    public StorageCriteria(String criteria) {
        this.arg = criteria;
    }

    public String getCriteria(){
        return arg;
    }
}
