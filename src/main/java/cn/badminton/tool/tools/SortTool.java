package cn.badminton.tool.tools;

import java.util.ArrayList;
import java.util.List;

public class SortTool {

    public static void insertionSort(Integer[] arr) {
        for (Integer i = 1; i < arr.length; i++) {
            Integer val = arr[i], j = i;
            while (j > 0 && val < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = val;
        }
    }

    public static<T> List<List<T>> fourPlayerToCombine(List<T> elements){
        List<List<T>> pairs = new ArrayList<>();
        for (int i = 0; i < elements.size(); i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                List<T> pair = new ArrayList<>();
                pair.add(elements.get(i));
                pair.add(elements.get(j));
                pairs.add(pair);
            }
        }
        return pairs;
    }
}
