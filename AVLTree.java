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
		
		public boolean hasOneChild() {
			return !hasTwoChildren() && !isLeaf();
		}

		public boolean hasTwoChildren() {
			return left != null && right != null;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public Node getDirectingNode(int data) {
			return data <= this.data ? left : right;	
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
		balanceAncestors(getAllAncestors(insertedNode), insertedNode.data);
	}

	public void balanceAncestors(Stack<Node> ancestors, int data) {
		while (!ancestors.isEmpty()) {
			Node parent = ancestors.pop();
			if (!isBalanced(parent)) {
				String rotation = getRotationType(parent, data);
				Node rotatedNode = rotate(parent, rotation);
				try {
					Node grandParent =  ancestors.peek();
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
			bf = hl - hr;
		}
		return bf == 1 || bf == 0 || bf == -1;
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
			curr = parent.getDirectingNode(data);
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
	
	public void delete(int data) {
		Node effectedNode = binaryDelete(this.avlRoot, data);
		if (effectedNode != null) {
			balanceAncestors(getAllAncestors(effectedNode), data);
		}
	}	

	private Node binaryDelete(Node parent, int data) {
		Node nodeToDelete = parent.data == data ? parent : parent.getDirectingNode(data);
		while (nodeToDelete != null && nodeToDelete.data != data) {
			parent = nodeToDelete;
			nodeToDelete = parent.getDirectingNode(data);
		}

		if (nodeToDelete == null) {
			return null;
		} else if (nodeToDelete.hasTwoChildren()) {
			Node smallest = getSmallest(nodeToDelete.right);
			int smallestNum = smallest.data;
			parent = binaryDelete(nodeToDelete, smallest.data);	
			nodeToDelete.data = smallestNum;
		} else {
			Node childOfCurr = nodeToDelete.left != null ? nodeToDelete.left : nodeToDelete.right;
			if (parent.isLeaf()) { 
				this.avlRoot = null;
				parent = null;
			} else if (parent == nodeToDelete) {
				Node nodeToSwap = parent.left != null ? parent.left : parent.right;
				int tempData = nodeToSwap.data;
				Node tempParent = binaryDelete(parent, tempData);
				parent.data = tempData;
				parent = tempParent;
			} else if (nodeToDelete.isLeftOf(parent)) {
				parent.left = childOfCurr;
			} else {
				parent.right = childOfCurr;
			}
		}	
		return parent;
	}

	public Node getSmallest(Node parent) {
		if (parent != null) 
			while (parent.left != null)	
				parent = parent.left;

		return parent; 
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

