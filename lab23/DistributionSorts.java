import java.util.Arrays;

public class DistributionSorts {

    /* Destructively sorts ARR using counting sort. Assumes that ARR contains
       only 0, 1, ..., 9. */
    public static void countingSort(int[] arr) {
        int[] counts = new int[10];
        int[] starts = new int[10];
        int[] sorted = new int[arr.length];
        for (int k : arr) {
            counts[k]++;
        }
        for (int j = 1; j < counts.length; j++){
            starts[j] = starts[j-1] + counts[j-1];
        }
        for (int i : arr) {
            sorted[starts[i]] = i;
            starts[i]++;
        }
        for (int m = 0; m < sorted.length; m++){
            arr[m] = sorted[m];
        }
        // - start at starts' 0 index and put 0 in sorted, add 1 to starts' 0 index until 0's index is one less than
        // 1's index, once that happens move to 1;
    }

    /* Destructively sorts ARR using LSD radix sort. */
    public static void lsdRadixSort(int[] arr) {
        int maxDigit = mostDigitsIn(arr);
        for (int d = 0; d < maxDigit; d++) {
            countingSortOnDigit(arr, d);
        }
    }

    /* A helper method for radix sort. Modifies ARR to be sorted according to
       DIGIT-th digit. When DIGIT is equal to 0, sort the numbers by the
       rightmost digit of each number. */
    private static void countingSortOnDigit(int[] arr, int digit) {
        int[] counts = new int[10];
        int[] starts = new int[10];
        int[] sorted = new int[arr.length];
        for (int k : arr) {
            counts[(int) (k / Math.pow(10, digit) % 10)]++;
        }
        for (int j = 1; j < counts.length; j++){
            starts[j] = starts[j-1] + counts[j-1];
        }
        for (int i : arr) {
            sorted[starts[(int) (i / Math.pow(10, digit) % 10)]] = i;
            starts[(int) (i / Math.pow(10, digit) % 10)]++;
        }
        for (int m = 0; m < sorted.length; m++){
            arr[m] = sorted[m];
        }
    }

    /* Returns the largest number of digits that any integer in ARR has. */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }

    /* Returns a random integer between 0 and 9999. */
    private static int randomInt() {
        return (int) (10000 * Math.random());
    }

    /* Returns a random integer between 0 and 9. */
    private static int randomDigit() {
        return (int) (10 * Math.random());
    }

    private static void runCountingSort(int len) {
        int[] arr1 = new int[len];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr1));
        countingSort(arr1);
        if (arr1 != null) {
            System.out.println("Should be sorted: " + Arrays.toString(arr1));
        }
    }

    private static void runLSDRadixSort(int len) {
        int[] arr2 = new int[len];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr2));
        lsdRadixSort(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));
    }

    public static void main(String[] args) {
        runCountingSort(20);
        runLSDRadixSort(3);
        runLSDRadixSort(30);
        runLSDRadixSort(1000);
    }
}