import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private static final int MIN_SIZE = 16;
	private Item[] ar;
	private int size;

	public RandomizedQueue() {
		this.ar = (Item[]) new Object[MIN_SIZE];
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int size() {
		return this.size;
	}

	public void enqueue(Item item) {
		if (item == null)
			throw new java.lang.IllegalArgumentException();
		sizeup();
		this.ar[size++] = item;
	}

	public Item dequeue() {
		if (this.size == 0)
			throw new java.util.NoSuchElementException();

		int i = StdRandom.uniform(0, size);
		Item rtn = this.ar[i];
		this.ar[i] = this.ar[size - 1];
		this.ar[size - 1] = null;
		size--;
		sizedown();
		return rtn;
	}

	private void sizeup() {
		if (this.size < this.ar.length)
			return;

		Item[] newar = (Item[]) new Object[this.ar.length * 2];
		System.arraycopy(this.ar, 0, newar, 0, this.size);
		this.ar = newar;
	}

	private void sizedown() {
		if (this.ar.length == MIN_SIZE || this.size >= this.ar.length / 4)
			return;

		Item[] newar = (Item[]) new Object[this.ar.length / 2];
		System.arraycopy(this.ar, 0, newar, 0, this.size);
		this.ar = newar;
	}

	public Item sample() {
		if (this.size == 0)
			throw new java.util.NoSuchElementException();

		int i = StdRandom.uniform(0, size);
		return this.ar[i];
	}

	public Iterator<Item> iterator() {
		return new RQIterator();
	}

	private class RQIterator implements Iterator<Item> {

		private final int[] is = StdRandom.permutation(size);
		private int i = 0;

		@Override
		public boolean hasNext() {
			return i < size;
		}

		@Override
		public Item next() {
			if (!this.hasNext())
				throw new java.util.NoSuchElementException();

			return ar[is[i++]];
		}

	}

	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<>();

		for (int i = 0; i < 50; i++)
			rq.enqueue(i);

		for (int i : rq)
			System.out.println(i);

		System.out.println("========");
		for (int i = 0; i < 50; i++)
			System.out.println(rq.dequeue());

	}

}
