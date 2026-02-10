import java.util.ArrayList;

public class Sorts {

    /*
     * PRE: inputList != null
     * POST: Adds SortStats for insertion sort run on a COPY of inputList
     */
    public static void InsertionSort(ArrayList<Integer> inputList, String fileName) {
        int i;
        int j;
        int temp;

        SortStats ss = new SortStats("Insertion", fileName);
        long start = System.nanoTime();
        ArrayList<Integer> numbers = new ArrayList<>(inputList);

        for (i = 1; i < numbers.size(); ++i) {
            j = i;
            ss.loops++;
            while (j > 0 && numbers.get(j) < numbers.get(j - 1)) {
                ss.loops++;        
                ss.comparisons++;  
                ss.swaps++;       

                temp = numbers.get(j - 1);
                numbers.set(j - 1, numbers.get(j));
                numbers.set(j, temp);
                j--;
            }
        }

        ss.timeNano = System.nanoTime() - start;
        Main.sortData.add(ss);
    }

    /*
     * PRE: inputList != null
     * POST: Adds SortStats for merge sort run on a COPY of inputList
     */
    public static void MergeSortWrapper(ArrayList<Integer> inputList, String fileName) {
        SortStats ss = new SortStats("MergeSort", fileName);
        long start = System.nanoTime();
        ArrayList<Integer> numbers = new ArrayList<>(inputList);
        MergeSort(numbers, ss);

        ss.timeNano = System.nanoTime() - start;
        Main.sortData.add(ss);
    }

    /*
     * PRE: numbers != null; ss != null
     * POST: returns a NEW sorted ArrayList containing same elements as numbers
     */
    public static ArrayList<Integer> MergeSort(ArrayList<Integer> numbers, SortStats ss) {
        ss.comparisons++;
        if (numbers.size() <= 1) return numbers;

        int mid = numbers.size() / 2;

        ArrayList<Integer> left = new ArrayList<>(numbers.subList(0, mid));
        ArrayList<Integer> right = new ArrayList<>(numbers.subList(mid, numbers.size()));
        left = MergeSort(left, ss);
        right = MergeSort(right, ss);

        return merge(left, right, ss);
    }

    /*
     * PRE: left != null; right != null; ss != null; left and right are sorted
     * POST: returns merged sorted list with all elements from left and right
     */
    public static ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right, SortStats ss) {
        ArrayList<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            ss.loops++;
            ss.comparisons++;
            if (left.get(i) <= right.get(j)) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        ss.comparisons++;
        while (i < left.size()) {
            ss.loops++;
            result.add(left.get(i));
            i++;
        }

        ss.comparisons++;
        while (j < right.size()) {
            ss.loops++;
            result.add(right.get(j));
            j++;
        }

        return result;
    }

    /*
     * PRE:
     *  - inputList != null
     *  - All values are NON-NEGATIVE integers (0 or greater)
     *  - maxValue >= maximum value in inputList
     * POST:
     *  - A COPY of inputList is sorted in ascending order (original inputList unchanged)
     *  - ss.comparisons stays 0 (radix is non-comparison sort)
     *  - ss.loops / ss.swaps reflect "reasonable" counting of work
     *  - ss.timeNano is set
     *  - ss is added to Main.sortData
     */
    public static void RadixSort(ArrayList<Integer> inputList, String fileName, int maxValue) {
        SortStats ss = new SortStats("RadixSort", fileName);
        long start = System.nanoTime();

        ArrayList<Integer> numbers = new ArrayList<>(inputList);
        int n = numbers.size();
        if (n <= 1) {
            ss.timeNano = System.nanoTime() - start;
            Main.sortData.add(ss);
            return;
        }

        int[] output = new int[n];
        int[] count = new int[10]; 
        for (int exp = 1; maxValue / exp > 0; exp *= 10) {
            ss.loops++;

            for (int i = 0; i < 10; i++) {
                ss.loops++;
                count[i] = 0;
            }

            for (int i = 0; i < n; i++) {
                ss.loops++;
                int digit = (numbers.get(i) / exp) % 10;
                count[digit]++;
            }

            for (int i = 1; i < 10; i++) {
                ss.loops++;
                count[i] += count[i - 1];
            }

            for (int i = n - 1; i >= 0; i--) {
                ss.loops++;
                int value = numbers.get(i);
                int digit = (value / exp) % 10;

                int pos = count[digit] - 1;
                output[pos] = value;
                count[digit]--;

                ss.swaps++;
            }

            for (int i = 0; i < n; i++) {
                ss.loops++;
                numbers.set(i, output[i]);
                ss.swaps++;
            }
        }

        ss.timeNano = System.nanoTime() - start;
        Main.sortData.add(ss);
    }
}
