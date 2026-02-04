public class SortStats implements Comparable<SortStats>{
    public String sortName;
    public String fileName;
    public long comparisons;
    public long swaps;
    public long loops;
    public long timeNano;

    public SortStats(String n, String fn){
        sortName = n;
        fileName = fn;
        comparisons = 0;
        swaps = 0;
        loops = 0;
        timeNano = 0;
    }
     public int compareTo(SortStats other){
        int comp = sortName.compareTo(other.sortName);
        if (comp > 0)
            return 1;
        else if (comp < 0)
            return -1;
        else
            return 0;
    }

}
