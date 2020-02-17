public class BSTNode<T, E extends Comparable>{
    
    public T key;
    public E value;
    public BSTNode<T, E> left;
    public BSTNode<T, E> right;

    public BSTNode(T key, E value){
        this.key = key;
        this.value = value;
        left = null;
        right = null;
    }

}