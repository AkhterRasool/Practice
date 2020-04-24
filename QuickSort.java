import java.util.*;
public class QuickSort {

	private static final int TEST_CASES = 20;
	private static final int BOUND = 1000;
	private static final Random randomNumGenerator = new Random();

	public static void main(String[] args) {
		int testsPassed = 0;
		for (int i = 0; i < TEST_CASES; i++) {
			int[] arr = generateRandomlyOrderedNumbers(50);
			System.out.println("TEST CASE: " + Arrays.toString(arr));
			quickSort(arr);
			String sortedMessage = null;
			if (isSorted(arr)) {
				testsPassed++;
				sortedMessage = "Sorted";
			} else {
				sortedMessage = "Not Sorted";
			}
			System.out.println(Arrays.toString(arr) + " => " + sortedMessage);
			System.out.println();
		}

		
		if (testsPassed == TEST_CASES) {
			System.out.println("All Tests have passed.");
		} else {
			System.out.println("Something failed.");
		}
	}

	private static int[] generateRandomlyOrderedNumbers(int count) {
		int[] arr = new int[count];	
		for (int i = 0; i < count; i++) {
			int randomNum = randomNumGenerator.nextInt(BOUND);
			arr[i] = randomNum;
		}
		return arr;
	}

	private static boolean isSorted(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1]) return false;
		}
		return true;
	}
	
	private static void quickSort(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	private static void quickSort(int[] arr, int start, int end) {
		if (start >= end) return;
		int i = start;
		int j = end;
		int pivot = start;
		boolean pivotSorted = false;

		while (!pivotSorted) {
			while (i < end && arr[i] <= arr[pivot]) i++;
			while (j > start && arr[j] >= arr[pivot]) j--;
			
			int otherPos = i < j ? i : pivot;
			if (otherPos == pivot) pivotSorted = true;
			
			int temp = arr[j];
			arr[j] = arr[otherPos];
			arr[otherPos] = temp;
		}
		
		pivot = j;
		quickSort(arr, start, pivot - 1);
		quickSort(arr, pivot + 1, end);
	}
}
