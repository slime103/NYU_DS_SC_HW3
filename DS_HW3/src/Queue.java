import java.util.NoSuchElementException;

public class Queue<T> {
	
	private Node<T> first;
	private Node<T> last;
	private int longestLength = 0;
	
	public Queue() {
		first = null;
		last = null;
	}
	
	public Queue(T data) {
		Node<T> n = new Node<T>(data);
		first = n;
		last = n;
	}
	
	public void add(T data) {
		Node<T> n = new Node<T>(data);
		if (first == null) {
			first = n;
		}
		else {
			last.setNext(n);
		}
		last = n;
		//Update longestLength
		if (length() > longestLength) {
			longestLength = length();
		}
	}
	
	public T remove() {
		if (first == null) {
			throw new NoSuchElementException();
		}
		Node<T> temp = first;
		first = first.getNext();
		return temp.getData();
	}
	
	public int length() {
		if (first == null) {
			return 0;
		}
		else {
			Node<T> current = first;
			int temp = 1;
			while (current.getNext() != null) {
				temp++;
				current = current.getNext();
			}
			return temp;
		}
	}
	
	public int getLength() {
		return longestLength;
	}
	
	public boolean isEmtpy() {
		if (first == null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		if(first == null) {
			return "The Queue is Empty.";
		}
		else {
			String list = "";
			Node<T> current = first;
			while (current.getNext() != null) {
				list += current.toString() + "\n";
				current = current.getNext();
			}
			return list + current.toString();
		}
	}
}