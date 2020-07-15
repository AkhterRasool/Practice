import java.util.*;

public class AVLSolution {
	public static void main(String[] args) {
			Set<Integer> keys = getRandomUniqueElements(60);
			System.out.println("Adding the following elements:");
			System.out.println(keys.toString());
			AVLTree avlTree = new AVLTree();
			keys.forEach(avlTree::insert);		
			avlTree.printInorder();
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
