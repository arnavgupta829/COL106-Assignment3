package col106.assignment3.Heap;

public class Heap<T, E extends Comparable> implements HeapInterface <T, E> {
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
	private Object[] keys;
	private Object[] values;
	private int storedLength;
	private int maxLength;
	private int levels;

	public Heap(){
		keys = new Object[1];
		values = new Object[1];
		this.levels = 0;
		this.maxLength = 1;
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
		int leftPos = (2*pos)+1;
		int rightPos = (2*pos)+2;
		if((leftPos > maxLength && rightPos > maxLength) || (keys[leftPos] == null && keys[rightPos] == null))
			return true;
		return false;
	}

	private void swap(int p1, int p2){
		T temp = (T)keys[p1];
		keys[p1] = keys[p2];
		keys[p2] = temp;

		E temp2 = (E)values[p1];
		values[p1] = values[p2];
		values[p2] = temp2;
	}

	private void makeHeap(int pos){
		if(isLeaf(pos))
			return;
		if(values[pos].compareTo(values[returnLeft(pos)]) < 0 || values[pos].compareTo(values[returnRight(pos)]) < 0){
			if(values[returnLeft(pos)].compareTo(values[returnRight(pos)]) < 0){
				swap(pos, returnLeft(pos));
				makeHeap(returnLeft(pos));
			}
			else{
				swap(pos, returnRight(pos));
				makeHeap(returnRight(pos));
			}
		}
	}

	public void insert(T key, E value) {
		//write your code here
		System.out.println("Inserting: "+key+", "+value);
		if(storedLength >= maxLength){
			int newLength = maxLength+(2^(++levels));
			Object[] newKeys = new Object[newLength];
			Object[] newVals = new Object[newLength];
			maxLength = newLength;
			for(int i = 0; i < storedLength; i++){
				newKeys[i] = keys[i];
				newVals[i] = values[i];
			}
			keys = newKeys;
			values = newVals;
		}
		keys[storedLength] = key;
		values[storedLength] = value;
		int curr = storedLength++;
		while(curr > 0 && values[curr].compareTo(values[parent(curr)]) > 0){
			swap(curr, parent(curr));
			curr = parent(curr);
		}
	}

	public E extractMax() {
		System.out.println("Extracting max:");
		//write your code here
		E max = (E)values[0];
		values[0] = values[storedLength-1];
		keys[0] = keys[storedLength-1];
		storedLength--;
		makeHeap(0);
		return max;
	}

	public void delete(T key) {
		//write your code here
		System.out.println("Deleting element with key "+key+":");
		int pos = 0;
		for(int i = 0; i<storedLength; i++){
			if(key == keys[i])
				pos = i;
		}
		values[pos] = values[storedLength-1];
		keys[pos] = values[storedLength-1];
		storedLength--;
		makeHeap(pos);

	}

	public void increaseKey(T key, E value) {
		//write your code here
		System.out.println("Updating key "+key+" to value "+value+":");
		int pos = 0;
		for(int i = 0; i<storedLength; i++){
			if(key == keys[i])
				pos = i;
		}
		values[pos] = value;
		while(pos > 0 && values[pos].compareTo(values[parent(pos)]) > 0){
			swap(pos, parent(pos));
			pos = parent(pos);
		}
	}

	public void printHeap() {
		//write your code here
		System.out.println("Printing heap in level order:");
		for(int i = 0; i<storedLength; i++){
			System.out.println(keys[i]+", "+values[i]);
		}
	}	
}
