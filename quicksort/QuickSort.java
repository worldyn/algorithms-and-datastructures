import java.util.Collections;
import java.util.Random;

/**
 * QuickSort implementation.
 * This class is used for Kattis tests
 * @author Adam Jacobs
 * @version Feb 2017
 */
public class QuickSort implements IntSorter {
    Random rnd = new Random();
    
    /**
    * QUICKSORT baby!
    */
    
    @Override
    public void sort(int[] v) {
        qsort(v, 0, v.length - 1);
    }

    private void qsort(int[] a, int first, int last) {
        if (first >= last)
            return;

        if((last - first) < 40) {
            for(int i = first + 1; i <= last; i++) {
                int x = a[i];
                int j = i - 1;

                while(j >= first && a[j] > x) {
                    a[j+1] = a[j];
                    j--;
                }
                a[j+1] = x;
            }
            return;
        } else {
            // Partition the elements
            int pivot = partition(a, first, last);
            qsort(a, first, pivot);
            qsort(a, pivot + 1, last);
        }
    }

    private int partition(int[] a, int first, int last) {
        int num1 = a[rnd.nextInt(last-first + 1) + first];
        int num2 = a[rnd.nextInt(last-first + 1) + first];
        int num3 = a[rnd.nextInt(last-first + 1) + first];

        // get middle element of the random numbers!
        int pivot = a[rnd.nextInt(last-first + 1) + first];

        int i = first - 1;
        int j = last + 1;
        int tmp;

        while(true) {
            do {
                j--;
            } while (a[j] > pivot);

            do {
                i++;
            } while (a[i] < pivot);

            if(i < j) {
                tmp = a[i];
                a[i] = a[j];
                a[j] = tmp;
            } else {
                return j;
            }
        }
    }
}

