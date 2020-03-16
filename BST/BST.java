package col106.assignment3.BST;

public class BST<T extends Comparable, E extends Comparable> implements BSTInterface<T, E>  {
	/* 
	 * Do not touch the code inside the upcoming block 
	 * If anything tempered your marks will be directly cut to zero
	*/
	public static void main() {
		BSTDriverCode BDC = new BSTDriverCode();
		System.setOut(BDC.fileout());
	}
	/*
	 * end code
	 * start writing your code from here
	 */
	
	//write your code here 
	private BSTNode<T, E> root = null;

	public void insert(T key, E value) {
		//write your code here
		BSTNode<T, E> toAdd = new BSTNode<>(key, value);
		root = insertRec(null, root, toAdd);

	}

	private BSTNode<T, E> insertRec(BSTNode<T, E> par, BSTNode<T, E> node, BSTNode<T, E> toAdd){
		if(node == null){
			toAdd.parent = par;
			node = toAdd;
			return node;
		}
		if(node.value.compareTo(toAdd.value) < 0)
			node.right = insertRec(node, node.right, toAdd);
		else if(node.value.compareTo(toAdd.value) > 0)
			node.left = insertRec(node, node.left, toAdd);
		return node;
	}

	public void update(T key, E value) {
		//write your code here
		deleteRec(root, key);
		insert(key, value);
	}

	public void delete(T key) {
		//write your code here
		root = deleteRec(root, key);
	}

	private BSTNode<T, E> deleteRec(BSTNode<T, E> node, T key){
		if(node == null)
			return node;
		else if(node.key.compareTo(key) != 0){
			node.left = deleteRec(node.left, key);
			node.right = deleteRec(node.right, key);
		}
		else{
			if(node.left == null)
				return node.right;
			else if(node.right == null)
				return node.left;

			BSTNode<T, E> succ = nextSucc(node.right);
//			if(succ.right == null)
//				succ.parent.left = null;
//			else
//				succ.parent.left = succ.right;
			if(succ.parent.key.equals(key)){
				node.key = succ.key;
				node.value = succ.value;
				if(succ.right == null)
					succ.parent.right = null;
				else
					succ.parent.right = succ.right;
			}
			else{
				node.key = succ.key;
				node.value = succ.value;
				if(succ.right == null)
					succ.parent.left = null;
				else
					succ.parent.left = succ.right;
			}
		}
		return node;
	}

	private BSTNode<T, E> nextSucc(BSTNode<T, E> node){
		BSTNode<T, E> temp = node;
		while(node.left != null){
			temp = node.left;
			node = node.left;
		}
		return temp;
	}

	public E valueOfKey(T key){
	    return recValueOfKey(root, key);
    }

    public E recValueOfKey(BSTNode<T, E> node, T key) {
	    if(node == null)
	    	return null;
	    if(node.key.equals(key))
	        return node.value;
	    E out = recValueOfKey(node.left, key);
	    if(out != null)
	    	return out;

	    E out2 = recValueOfKey(node.right, key);
	    return out2;
    }

//	public void inorder(BSTNode<T, E> node){
//		if(node.left != null)
//			inorder(node.left);
//		System.out.println(node.key+", "+node.value);
//		if(node.right != null)
//			inorder(node.right);
//	}

	public void printBST() {
		//write your code here
		BSTNode<T, E> curr = root;
		int height = getHeight(curr);
		for(int i = 1; i<height+1; i++)
			printCurrHeight(curr, i);
	}

	public void printCurrHeight(BSTNode node, int counter){
		if(node == null)
			return;
		if(counter == 1)
			System.out.println(node.key+", "+node.value);
		else if(counter > 1){
			printCurrHeight(node.left, counter-1);
			printCurrHeight(node.right, counter-1);
		}
	}

	public int getHeight(BSTNode node){
		if (node == null)
			return 0;
		else{
			int lheight = getHeight(node.left);
			int rheight = getHeight(node.right);
			if(lheight > rheight)
				return lheight+1;
			return rheight+1;
		}
	}

}