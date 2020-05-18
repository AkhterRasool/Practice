import java.util.Arrays;

public class GroupIntersectingLinkedList {
	private static Integer[][] group;
	private static boolean[][] traversedNode;

	public static void main(String[] args) {
		Node[] input = getInputData();
		group = new Integer[input.length][input.length];
		traversedNode = new boolean[input.length][1000];

		System.out.println("Input is as follows: ");
		print(input);
		System.out.println();
		System.out.println();
		
		group(input);
		System.out.println("Groups are sorted as follows where lists are intersected: ");
		print(group);
	}

	private static Node[] getInputData() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		Node node7 = new Node(7);
		Node node8 = new Node(8);
		Node node9 = new Node(9);
		Node node10 = new Node(10);

		Node[] input = new Node[4];
		node1.setNext(node2);
		node2.setNext(node3);
		node3.setNext(node4);

		node5.setNext(node6);
		node6.setNext(node4);

		node7.setNext(node8);
		node8.setNext(node9);

		node10.setNext(node2);

		input[0] = node1;
		input[1] = node5;
		input[2] = node7;
		input[3] = node10;
		return input;
	}

	private static void print(Node[] nodeArr) {
		for (int i = 0; i < nodeArr.length; i++) {
			Node head = nodeArr[i];
			System.out.print("[" + i + "]" + " => ");
			while (head != null) {
				System.out.print(head.getData() + " -> ");
				head = head.next;
			}	

			System.out.println("null");
		}
	}
		
	private static void print(Integer[][] group) {
		for (int i = 0; i < group.length; i++) {
			if (group[i][0] != null) {
				System.out.print("Group " + (i + 1) + ": => ");
				for (int j = 0; j < group[i].length && group[i][j] != null; j++) {
					int idx = group[i][j];
					System.out.print(idx + " ");
				}
				System.out.println();
			}
		}
	}

	private static void group(Node[] nodeArr) {
		int groupsCovered = 0;
		for (int i = 0; i < nodeArr.length; i++) {
			boolean groupFound = false;
			Node head = nodeArr[i];
			while (head != null) {
				int data = head.getData();
				int arrEntryHavingSameNode = getArrEntryHavingSameNode(data, traversedNode);
				if (arrEntryHavingSameNode == -1) {
					traversedNode[i][data] = true;
				} else {
					addToExistingGroup(i, arrEntryHavingSameNode, group);
					groupFound = true;
					break; //because we found a group.
				}	
				head = head.next;
			}

			if (!groupFound) {
				group[groupsCovered++][0] = i;	
			}
		}
	}

	private static int getArrEntryHavingSameNode(int data, boolean[][] traversedNode) {
		for (int i = 0; i < traversedNode.length; i++) {
			if (traversedNode[i][data]) {
				return i;
			}
		}
		return -1;
	}

	private static void addToExistingGroup(int newArrEntry, int existingArrEntry, Integer[][] groupTable) {
		for (int i = 0; i < groupTable.length; i++) {
			for (int j = 0; groupTable[i][j] != null; j++) { 
				if (groupTable[i][j] == existingArrEntry) {
					//Find a spot to insert in the same row.
					while (groupTable[i][j] != null) {
						j++;
					}
					groupTable[i][j] = newArrEntry;
					return;
				}
			}
		}
	}
}	



class Node {
	private int data;
	public Node next;

	public Node(int data) {
		this.data = data;
	}

	public int getData() {
		return this.data;
	}
		
	public void setNext(Node nextNode) {
		this.next = nextNode;
	}
}
