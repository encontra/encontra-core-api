package pt.inevo.encontra.benchmark;


public class BenchmarkEntry<T> {

    private Benchmark benchmark;
    private String key;

    private long start;
    private long stop;

    public BenchmarkEntry(Benchmark benchmark,String key){
        this.key=key;
        this.benchmark=benchmark;
        start=System.currentTimeMillis();
    }

    public void stop(){
        stop=System.currentTimeMillis();
        benchmark.stop(this);
    }

    @Override
    public String toString() {
        return key + " - " + (stop-start) + " ms";
    }
}
