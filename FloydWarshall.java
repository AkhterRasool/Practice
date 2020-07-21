import java.util.Stack;

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
		for (int startVertex = 0; startVertex < minCostMatrix.length; startVertex++) {
			for (int endVertex = 0; endVertex < minCostMatrix.length; endVertex++) {
				if (startVertex != endVertex) {
					Stack<Integer> path = new Stack<>();
					path.push(startVertex + 1);
					fillShortestPath(origMatrix, startVertex, endVertex, minCostMatrix[startVertex][endVertex], path);
					System.out.print(String.format("(%d, %d) == ", startVertex + 1, endVertex + 1));
					System.out.println(path.toString().replaceAll(", ", " -> "));
				}
			}
		} 
	}	

	private static void fillShortestPath(int[][] origMatrix, int sourceVertex, int destVertex, int shortestDistance, Stack<Integer> path) {
		for (int endVertex = 0; endVertex < origMatrix.length; endVertex++) {	
			if (endVertex != sourceVertex && origMatrix[sourceVertex][endVertex] != NO_PATH) {
				int remainingDistance = shortestDistance - origMatrix[sourceVertex][endVertex];
				if (remainingDistance >= 0) {
					path.push(endVertex + 1);
					if (remainingDistance == 0 && endVertex == destVertex) break;
					fillShortestPath(origMatrix, endVertex, destVertex, remainingDistance, path);
					if (path.peek() == (endVertex + 1)) { //No new path was found, so we pop in order to backtrack. 
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
			for (int startVertex = 0; startVertex < newMatrix.length; startVertex++) {
				for (int endVertex = 0; endVertex < newMatrix[startVertex].length; endVertex++) {
					if (endVertex == intermediateVertex) {
						newMatrix[startVertex][endVertex] = minCostMatrix[startVertex][endVertex];
					} else if (endVertex != startVertex) {
						int directDistance = minCostMatrix[startVertex][endVertex];
						int intermediateDistance = minCostMatrix[startVertex][intermediateVertex] + minCostMatrix[intermediateVertex][endVertex];
						newMatrix[startVertex][endVertex] = Math.min(directDistance, intermediateDistance);
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
