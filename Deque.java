import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	private final Node head;
	private final Node tail;
	private int size;

	public Deque() {
		this.head = new Node();
		this.tail = new Node();
		this.head.next = this.tail;
		this.tail.pre = this.head;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int size() {
		return this.size;
	}

	public void addFirst(Item item) {
		checkNullItem(item);
		Node newNode = new Node(item);
		insertAfter(this.head, newNode);
	}

	public void addLast(Item item) {
		checkNullItem(item);
		Node newNode = new Node(item);
		insertAfter(this.tail.pre, newNode);
	}

	private void insertAfter(Node a, Node insert) {
		this.size++;
		Node b = a.next;
		insert.next = b;
		insert.pre = a;
		a.next = insert;
		b.pre = insert;
	}

	private static void checkNullItem(Object item) {
		if (item == null)
			throw new java.lang.IllegalArgumentException();
	}

	public Item removeFirst() {
		if (this.size == 0)
			throw new java.util.NoSuchElementException();

		return remove(this.head.next);
	}

	public Item removeLast() {
		if (this.size == 0)
			throw new java.util.NoSuchElementException();

		return remove(this.tail.pre);
	}

	private Item remove(Node n) {
		this.size--;
		Node a = n.pre;
		Node b = n.next;
		n.next = null;
		n.pre = null;
		a.next = b;
		b.pre = a;
		return n.item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {

		private Node cur = head;

		@Override
		public boolean hasNext() {
			return cur.next != tail;
		}

		@Override
		public Item next() {
			if (!this.hasNext())
				throw new java.util.NoSuchElementException();

			cur = cur.next;
			return cur.item;
		}

	}

	private class Node {
		Item item;
		Node next;
		Node pre;

		Node() {
		}

		Node(Item item) {
			this.item = item;
		}
	}

	public static void main(String[] args) {
		Deque<Integer> dq = new Deque<>();

		dq.addFirst(1);
		dq.addFirst(2);
		dq.addLast(3);

		System.out.println(dq.removeLast());
		System.out.println(dq.removeLast());
		System.out.println(dq.removeLast());

	}

}
