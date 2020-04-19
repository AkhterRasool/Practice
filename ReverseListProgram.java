public class ReverseListProgram {
	
	public static void main(String[] args) {
		
		Node head = initLinkedList();
		System.out.println("Given List:");
		printList(head);
		head = reverseList(head);
		System.out.println("After reversing:");
		printList(head);
	}

	private static Node initLinkedList() {
		int startVal = 1;
		int endVal = 100;
		Node head = new Node(startVal);
		Node start = head;
		while (startVal < endVal) {
			head.setNext(new Node(++startVal));
			head = head.getNext();
		}
		return start;
	}

	private static void printList(Node head) {
		if (head == null) return;	
		while (head.getNext() != null) {
			System.out.print(head.getData() + " => ");
			head = head.getNext();
		}

		System.out.println(head.getData());
	}

	private static Node reverseList(Node head) {
		if (head == null) return null;

		Node nextNodePointer = head.getNext();
		head.setNext(null);
		while (nextNodePointer != null) {
			Node nextNodePointerDest = nextNodePointer.getNext();
			nextNodePointer.setNext(head);
			head = nextNodePointer;
			nextNodePointer = nextNodePointerDest;
		}

		return head;
	}
}

class Node {

	private int data;
	private Node next;

	public Node(int data) {
		this.data = data;
	}

	public void setNext(Node nextNode) {
		this.next = nextNode;
	}

	public int getData() {
		return this.data;
	}

	public Node getNext() {
		return this.next;
	}
}
