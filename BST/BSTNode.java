package col106.assignment3.BST;

public class BSTNode<T extends Comparable, E extends Comparable>{
    
    public T key;
    public E value;
    public BSTNode<T, E> parent;
    public BSTNode<T, E> left;
    public BSTNode<T, E> right;

    public BSTNode(T key, E value){
        this.parent = null;
        this.key = key;
        this.value = value;
        left = null;
        right = null;
    }

}