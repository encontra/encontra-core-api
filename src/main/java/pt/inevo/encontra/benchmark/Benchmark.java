package pt.inevo.encontra.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Benchmark {
    String key;
    List<BenchmarkEntry> log=new ArrayList<BenchmarkEntry>();
    Queue<BenchmarkEntry> stack = new ConcurrentLinkedQueue<BenchmarkEntry>();

    public Benchmark(String key){
        this.key=key;
    }

    public BenchmarkEntry start(String key){
        BenchmarkEntry entry=new BenchmarkEntry(this,key);
        stack.add(entry);
        log.add(entry);

        return entry;
    }

    public void stop(BenchmarkEntry benchmarkEntry) {
        BenchmarkEntry entry=stack.remove();
        if(!entry.equals(benchmarkEntry)){
             // TODO - Error some benchmark was not properly stopped!
        }
    }

    @Override
    public String toString() {
        String str = key + " - Benchmark{";
        for(BenchmarkEntry entry : log){
            str+=entry.toString();
        }
        str += '}';
        return str;
    }
}
