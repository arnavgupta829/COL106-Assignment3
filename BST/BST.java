package col106.assignment3.BST;

public class BST<T, E extends Comparable> implements BSTInterface<T, E>  {
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
		if(root == null){
			root = new BSTNode<T, E>(key, value);
		}
		else{
			BSTNode<T, E> temp = root;
			loop: while (true){
				if(temp.value > value){
					if(temp.left == null){
						temp.left = new BSTNode<T, E>(key, value);
						break loop;
					}
					temp = temp.left;
				}
				else{
					if(temp.right == null){
						temp.right = new BSTNode<T, E>(key, value);
						break loop;
					}
					temp = temp.right;
				}
			}
		}

    }

    public void update(T key, E value) {
		//write your code here
		
    }

    public void delete(T key) {
		//write your code here
    }

    public void printBST() {
		//write your code here
		BSTNode<T, E> curr = root;
		int height = getHeight(curr);
		for(int i = 1; i<h+1; i++)
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