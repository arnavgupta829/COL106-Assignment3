package col106.assignment3.Heap;
import java.util.ArrayList;

public class Heap<T extends Comparable, E extends Comparable> implements HeapInterface <T, E> {
	/*
	 * Do not touch the code inside the upcoming block
	 * If anything tempered your marks will be directly cut to zero
	 */
	public static void main() {
		HeapDriverCode HDC = new HeapDriverCode();
		System.setOut(HDC.fileout());
	}
	/*
	 * end code
	 */

	// write your code here
	private ArrayList<T> keys = new ArrayList<>();
	private ArrayList<E> values = new ArrayList<>();
	private int storedLength;

	public Heap(){
		this.storedLength = 0;
	}

	private int parent(int pos){
		return (pos-1)/2;
	}

	private int returnRight(int pos){
		return (2*pos)+1;
	}

	private int returnLeft(int pos){
		return (2*pos)+2;
	}

	private boolean isLeaf(int pos){
		if(pos+1 > ((storedLength)/2) && pos < storedLength)
			return true;
		return false;
	}

	private void swap(int p1, int p2){
		T temp = keys.get(p1);
		keys.set(p1, keys.get(p2));
		keys.set(p2, temp);

		E temp2 = values.get(p1);
		values.set(p1, values.get(p2));
		values.set(p2, temp2);

	}

	private void makeHeap(int pos){
		if(pos > storedLength-1)
			return;
		if(isLeaf(pos))
			return;
		if(values.get(pos).compareTo(values.get(returnLeft(pos))) < 0 || values.get(pos).compareTo(values.get(returnRight(pos))) < 0){
			if(values.get(returnLeft(pos)).compareTo(values.get(returnRight(pos))) > 0){
				swap(pos, returnLeft(pos));
				makeHeap(returnLeft(pos));
			}
			else{
				swap(pos, returnRight(pos));
				makeHeap(returnRight(pos));
			}
		}
	}

	public int getSize(){
		return keys.size();
	}

	public void insert(T key, E value) {
		//write your code here
		storedLength++;
		int curr = 0;
		if(storedLength >= keys.size()){
			keys.add(key);
			values.add(value);
		}
		else{
			keys.set(storedLength-1, key);
			values.set(storedLength-1, value);
		}
		curr = storedLength-1;
		while(curr > 0 && values.get(curr).compareTo(values.get(parent(curr))) > 0){
			swap(curr, parent(curr));
			curr = parent(curr);
		}
	}

	public E extractMax() {
		//write your code here
		E max = values.get(0);
		// values[0] = values[storedLength-1];
		// keys[0] = keys[storedLength-1];
		values.set(0, values.get(storedLength-1));
		keys.set(0, keys.get(storedLength-1));
		storedLength--;
		makeHeap(0);
		return max;
	}

	public T peekTopKey(){
		return keys.get(0);
	}

	public boolean keyExists(T key){
		for(int i = 0; i<keys.size(); i++){
			if(keys.get(i).equals(key))
				return true;
		}
		return false;
	}

	public void delete(T key) {
		//write your code here
		int pos = 0;
		for(int i = 0; i<storedLength; i++){
			if(key.equals(keys.get(i)))
				pos = i;
		}
		values.set(pos, values.get(storedLength-1));
		keys.set(pos, keys.get(storedLength-1));
		storedLength--;
		makeHeap(pos);
		increaseKey(keys.get(pos), values.get(pos));
	}

	public E getValue(T key){
		for(int i = 0; i<keys.size(); i++){
			if(key.equals(keys.get(i)))
				return values.get(i);
		}
		return null;
	}

	public void increaseKey (T key, E value) {
		//write your code here
		int pos = -1;
		for(int i = 0; i<storedLength; i++){
			if(key.equals(keys.get(i)))
				pos = i;
		}
		// values[pos] = value;
		if(pos == -1)
			return;
		values.set(pos, value);
		while(pos > 0 && values.get(pos).compareTo(values.get(parent(pos))) > 0){
			swap(pos, parent(pos));
			pos = parent(pos);
		}
	}

	public void decreaseKey(T key, E value){
		int pos = 0;
		for(int i = 0; i<storedLength; i++){
			if(key.equals((keys.get(i))))
				pos = i;
		}
		values.set(pos, value);
		CheckWhileDecrease(pos);
	}

	private void CheckWhileDecrease(int pos){
		int SS = storedLength-1;
		if(pos > SS)
			return;
		if(isLeaf(pos))
			return;
		if(returnLeft(pos)>=SS || returnRight(pos)>=SS)
			return;
		if(values.get(pos).compareTo(values.get(returnLeft(pos))) < 0 || values.get(pos).compareTo(values.get(returnRight(pos))) < 0){
			if(values.get(returnLeft(pos)).compareTo(values.get(returnRight(pos))) > 0){
				swap(pos, returnLeft(pos));
				makeHeap(returnLeft(pos));
			}
			else{
				swap(pos, returnRight(pos));
				makeHeap(returnRight(pos));
			}
		}
	}

	public T getKeyAtIndex(int pos){
		return keys.get(pos);
	}

	public void printHeap() {
		//write your code here
		for(int i = 0; i<storedLength; i++){
			System.out.println(keys.get(i)+", "+values.get(i));
		}
	}
}
