import java.util.Stack;
import java.util.Arrays;

public class FloydWarshall {
	
	private static final int NO_PATH = 999; 

	public static void main(String[] args) {
		int[][] origMatrix = new int[][] {
			{0, 1, NO_PATH, 1, 5},
			{9, 0, 3, 2, NO_PATH},
			{NO_PATH, NO_PATH, 0, 4, NO_PATH},
			{NO_PATH, NO_PATH, 2, 0, 3},
			{3, NO_PATH, NO_PATH, NO_PATH, 0}
		};

		int[][] minCostMatrix = getMinimumCostMatrix(origMatrix);	
		System.out.println("Original Matrix");
		print(origMatrix);
		System.out.println();
		System.out.println("Minimum cost Matrix");
		print(minCostMatrix);
		System.out.println();
		printShortestPathForAllPairs(origMatrix, minCostMatrix);
	}

	private static void printShortestPathForAllPairs(int[][] origMatrix, int[][] minCostMatrix) {
		for (int row = 0; row < minCostMatrix.length; row++) {
			for (int col = 0; col < minCostMatrix.length; col++) {
				if (row != col) {
					Stack<Integer> path = new Stack<>();
					path.push(row + 1);
					fillShortestPath(origMatrix, row, col, minCostMatrix[row][col], path);
					System.out.print(String.format("(%d, %d) == ", row + 1, col + 1));
					System.out.println(path.toString().replaceAll(", ", " -> "));
				}
			}
		} 
	}	

	private static void fillShortestPath(int[][] origMatrix, int sourceVertex, int destVertex, int shortestDistance, Stack<Integer> path) {
		for (int col = 0; col < origMatrix.length; col++) {	
			if (col != sourceVertex && origMatrix[sourceVertex][col] != NO_PATH) {
				int remainingDistance = shortestDistance - origMatrix[sourceVertex][col];
				if (remainingDistance >= 0) {
					path.push(col + 1);
					if (remainingDistance == 0 && col == destVertex) break;
					fillShortestPath(origMatrix, col, destVertex, remainingDistance, path);
					if (path.peek() == (col + 1)) { //No new path was found, so we pop in order to backtrack. 
						path.pop();
					}
				}
			}
		}
	}

	private static int[][] getMinimumCostMatrix(int[][] origMatrix) {
		int[][] minCostMatrix = origMatrix;
		for (int intermediateVertex = 0; intermediateVertex < minCostMatrix.length; intermediateVertex++) {
			int[][] newMatrix = new int[minCostMatrix.length][minCostMatrix.length];
			for (int row = 0; row < newMatrix.length; row++) {
				for (int col = 0; col < newMatrix[row].length; col++) {
					if (col == intermediateVertex) {
						newMatrix[row][col] = minCostMatrix[row][col];
					} else if (col != row) {
						int directDistance = minCostMatrix[row][col];
						int intermediateDistance = minCostMatrix[row][intermediateVertex] + minCostMatrix[intermediateVertex][col];
						newMatrix[row][col] = Math.min(directDistance, intermediateDistance);
					}
				}
			}
			minCostMatrix = newMatrix;
		}
		return minCostMatrix;
	}
	
	private static void print(int[][] arr) {
		System.out.println();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				System.out.print(String.format("%4d  ", arr[i][j]));
			}
			System.out.println();
		}	
	}
}
