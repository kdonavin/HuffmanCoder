/**
 * Author: Kirkwood Donavin
 */

package huffman_coder;


import java.lang.Comparable;

/*
 * 
 */
public class Node implements Comparable<Node> {
    private char key;
    private int weight;
    private Node leftChild;
    private Node rightChild;

    //Constructors
    Node(char c, int w){
        this.key = c;
        this.weight = w;
        this.leftChild = null;
        this.rightChild = null;
    }

    Node(Node l, Node r){
        this.key = '\0';
        this.weight = l.weight + r.weight;
        this.leftChild = l;
        this.rightChild = r;
    }

    @Override
    public int compareTo(Node o) {
        if(o == null){
            throw new NullPointerException();
        }
        if(this.weight < o.weight){
            return -1;
        } else if(o.weight == this.weight){
            return 0;
        } else{
            return 1;
        }
    }
    
    public boolean equals(Node o){
        if(o == null){
            throw new NullPointerException();
        }
        return this.compareTo(o) == 0;
    }

    /**
     * toString method prints contents of node key and weight.
     * @return String in the following format {key=weight}
     */
    public String toString(){
        String s = "{'"+ this.key + "'=" + this.weight + "}";
        System.out.println(s);
        return s;
    }
    //Accessor methods
    public char getKey(){
        return this.key;
    }
    
    public int getWeight(){
        return this.weight;
    }
    
    public Node getLeft(){
        return this.leftChild;
    }
    
    public Node getRight(){
        return this.rightChild;
    }
}


