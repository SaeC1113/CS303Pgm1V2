import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

class Main {

    public static ArrayList<SortStats> sortData = new ArrayList<>();

    public static void main(String[] args) {
        String input1 = "data1.txt";
        String input2 = "data2.txt";
        String input3 = "data3.txt";
        String reportFile = "report.txt";
 
        //load ArrayLists to be sorted
        ArrayList<Integer> list1 = new ArrayList<>();
        int currentFile = 1;
        String fileName = " ";
        while (currentFile <= 3){
            list1.clear();
            try {
                BufferedReader reader;
                String line;

                //load correct file int current list
                switch (currentFile) {
                    case 1:
                        reader    = new BufferedReader(new FileReader(input1));
                        fileName = "1 (random)";
                        while ((line = reader.readLine()) != null){ 
                            String[] tokens = line.split(",");
                            loadArrayList(list1, tokens);
                        }
                        break;
                    case 2:
                        reader    = new BufferedReader(new FileReader(input2));
                        fileName = "2 (ascending)";
                        while ((line = reader.readLine()) != null){ 
                            String[] tokens = line.split(",");
                            loadArrayList(list1, tokens);
                        }
                        break;
                    case 3:
                        reader    = new BufferedReader(new FileReader(input3));
                        fileName = "3 (descending)";
                        while ((line = reader.readLine()) != null){ 
                            String[] tokens = line.split(",");
                            loadArrayList(list1, tokens);
                        }
                        break;
                }

                Sorts.InsertionSort(list1, fileName);
                Sorts.MergeSortWrapper(list1, fileName);
                //run Radix sort on list 1
                //NOTE: I am providing the maximum value you will encounter in the data set
                Sorts.RadixSort(list1, fileName, 9999);
                currentFile ++;

            } catch (IOException e) {
                System.err.println("File Read Error: " + e.getMessage());
                return;
            }
        }
        try {
            PrintWriter reportWriter = new PrintWriter(new FileWriter(reportFile));
            Collections.sort(sortData);
            reportWriter.printf("%-20s %-15s %20s %20s %20s %20s\n",
                            "Sort Name","File","Swaps", "Comparisons", "Loops", "  NanoSeconds");
            for (SortStats s: sortData){
                reportWriter.printf("%-20s %-15s %,20d %,20d %,20d %,20d ns\n",
                                s.sortName,s.fileName, s.swaps,s.comparisons, s.loops, s.timeNano);
            }
            reportWriter.close();
        } catch (IOException e) {
            System.err.println("File Error: " + e.getMessage());
            return;
        }
    }

    public static void loadArrayList(ArrayList<Integer> list, String[] tokens){
        for (int j = 0; j < tokens.length; j++){
            try {
                Integer tempInt = Integer.parseInt(tokens[j]);
                list.add(tempInt);
            }
            catch (Exception e) {
                System.err.println("File Read Error: " + e.getMessage());
            }
        }
    }
}
