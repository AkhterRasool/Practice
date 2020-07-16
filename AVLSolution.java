import java.util.*;

public class AVLSolution {
	public static void main(String[] args) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter total elements\t");
			int total = scanner.nextInt();
			Set<Integer> keys = getRandomUniqueElements(total);
			System.out.println("Adding the following elements:");
			System.out.println(keys.toString());
			AVLTree avlTree = new AVLTree();
			keys.forEach(avlTree::insert);		
			avlTree.printBFS();
			System.out.print("Enter value to delete: \t");
			int toDelete = scanner.nextInt();
			System.out.println("Node to delete: " + toDelete);
			avlTree.delete(toDelete);
			avlTree.printBFS();
	}


	private static Set<Integer> getRandomUniqueElements(int total) {
		Random random = new Random();
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < total; i++) {
			int generated = -1;
			do {
				generated = random.nextInt(total * 10);
			} while (generated < 0 || set.contains(generated));
			set.add(generated);
		}
		return set;
	}
}
