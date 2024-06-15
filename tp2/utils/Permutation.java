package utils;
// Java program to print all combination of size
// r in an array of size n
import java.util.ArrayList;
public class Permutation {

    /* arr[]  ---> Input Array
    data[] ---> Temporary array to store current combination
    start & end ---> Starting and Ending indexes in arr[]
    index  ---> Current index in data[]
    r ---> Size of a combination to be printed */
    public static void combinationUtil(int arr[], int n, int k,
                                       int index, int data[], int i,
                                       ArrayList<int[]> combinations)
    {
        // Current combination is ready to be printed, 
        // print it
        if (index == k) {
            combinations.add(data.clone());
            return;
        }

        // When no more elements are there to put in data[]
        if (i >= n)
            return;

        // current is included, put next at next
        // location
        data[index] = arr[i];
        combinationUtil(arr, n, k, index + 1,
                data, i + 1, combinations);

        // current is excluded, replace it with
        // next (Note that i+1 is passed, but
        // index is not changed)
        combinationUtil(arr, n, k, index, data, i + 1, combinations);
    }


    // The main function that prints all combinations
    // of size r in arr[] of size n. This function 
    // mainly uses combinationUtil()
}
/* This code is contributed by Devesh Agrawal */