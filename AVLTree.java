import java.util.*;

class AVLTree {
	private static final int MAX_ROTATIONS = 2;

	private Node avlRoot;

	class Node {
		int data;
		Node left;
		Node right;

		public Node(int data) {
			this.data = data;
		}
		
		public boolean isLeftOf(Node node) {
			return this.data <= node.data;
		}

		public void print() {
			String leftData = left == null ? "Null" : String.valueOf(left.data);
			String rightData = right == null ? "Null" : String.valueOf(right.data);
			System.out.println(leftData + " - " + this.data + " - " + rightData);
		}
	}
	
	public void printBFS() {
		System.out.print("BFS: ");
		bfs(avlRoot);
		System.out.println();
	}

	public void printInorder() {
		System.out.print("Inorder: " );
		inorder(avlRoot);
		System.out.println();
	}

	private void inorder(Node root) {
		if (root == null) {
			return;
		}
		inorder(root.left);
		System.out.print(root.data + " ");
		inorder(root.right);
	}

	public void bfs(Node node) {
		Queue<Node> queue = new LinkedList<>();
		queue.add(node);

		while (!queue.isEmpty()) {
			Node currNode = queue.remove();
			System.out.print((currNode == null ? currNode : currNode.data) + " ");
			if (currNode == null) continue;
			queue.add(currNode.left);
			queue.add(currNode.right);
		}
	}
	
	public void insert(int data) {
		Node insertedNode = binaryInsert(data);
		Stack<Node> ancestors = getAllAncestors(insertedNode);
		Node rotatedNode = null;
		while (!ancestors.isEmpty()) {
			Node parent = ancestors.pop();
			if (!isBalanced(parent)) {
				String rotation = getRotationType(parent, insertedNode.data);
				rotatedNode = rotate(parent, rotation);
				Node grandParent = null;
				try {
					grandParent =  ancestors.peek();
					if (parent.isLeftOf(grandParent)) { 
						grandParent.left = rotatedNode;
					} else {
						grandParent.right = rotatedNode;
					}
				} catch (EmptyStackException e) {
					this.avlRoot = rotatedNode;
				}
			}
		}
	}

	private boolean isBalanced(Node node) {
		int bf = 0;
		if (node != null) {
			int hl = getHeight(node.left);
			int hr = getHeight(node.right);
			bf = Math.abs(hl - hr);
		}
		return bf == 1 || bf == 0;
	}

	private int getHeight(Node node) {
		if (node == null) {
			return -1;
		}

		return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
	}
	
	private String getRotationType(Node node, int data) {
		String rotation = "";	
		for (int i = 1; i <= MAX_ROTATIONS; i++) {
			if (data < node.data) {
				node = node.left;
				rotation += "L";
			} else if (data > node.data) {
				node = node.right;
				rotation += "R";
			}
		}
		return rotation;
	}
	
	private Node rotate(Node node, String rotation) {
		switch (rotation) {
			case "LL": return LLRotate(node);
			case "LR": return LRRotate(node);
			case "RR": return RRRotate(node);
			case "RL": return RLRotate(node);
			default: return node;
		}
	}
	
	private Stack<Node> getAllAncestors(Node node) {
		Node curr = avlRoot;
		Stack<Node> ancestorStack = new Stack<>();	
		while (curr != null && curr != node) {
			ancestorStack.push(curr);
			curr = node.data <= curr.data ? curr.left : curr.right;
		}		
		return curr == null ? new Stack<>() : ancestorStack;
	}

	private Node binaryInsert(int data) {
		Node node = new Node(data);
		if (avlRoot == null) {
			avlRoot = node;
			return avlRoot;
		}

		Node parent = avlRoot;
		Node curr = null;
		do {
			curr = data <= parent.data ? parent.left : parent.right;
			if (curr != null) {
				parent = curr;
			}
		} while (curr != null);
		if (data <= parent.data) {
			parent.left = node;
		} else {
			parent.right = node;
		}

		return node;
	}

	/*	ROTATIONS HERE		*/
	private Node LLRotate(Node root) {
		Node b = root.left;

		Node bRight = b.right;
		b.right = root;
		root.left = bRight;

		return b;
	}

	private Node RRRotate(Node root) {
		Node b = root.right;

		Node bLeft = b.left;
		root.right = bLeft;
		b.left = root;

		return b;
	}

	private Node LRRotate(Node root) {
		Node b = root.left;
		Node c = b.right;

		b.right = c.left;
		c.left = b;
		root.left = c.right;
		c.right = root;

		return c;
	}

	private Node RLRotate(Node root) {
		Node b = root.right;
		Node c = b.left;

		b.left = c.right;
		root.right = c.left;
		c.left = root;
		c.right = b;
		
		return c;
	}

}

