package huffman_coder;
import java.util.ArrayList;

public class HuffmanTree {
    private Node root;
    private int size;
    
    //Constructor
    HuffmanTree(Node r){
        this.root = r;
        this.size = calculateSize();
    }
    
    
    public void PrintTree(){
        ArrayList<Boolean> connect = new ArrayList<Boolean>();
        for(int i = 1; i < 20; i++){ //Max depth of 20
            connect.add(true); 
        }
        PrintTreeRec(this.root, 0, connect, null);
    }
    
    /**
     * Traverses the tree in pre-order
     * @param localRoot the local subtree root.
     */
    public void PrintTreeRec(Node localRoot, int level, ArrayList<Boolean> connect, String branch){
        if(localRoot != null){
            for(int i = 0; i+1 < level; i ++){
                if(connect.get(i)){
                    System.out.print("|");
                }
                System.out.print("\t");
            }

            //Split
            if(level>0){
                System.out.print(branch + ">-----");
            }

            if(localRoot.getKey()!='\0'){
                System.out.print("(" + localRoot.getKey() + ":" + localRoot.getWeight()+") \n");
            } else{
                System.out.print("(" + localRoot.getWeight()+") \n");
            }
            connect.set(level + 1, Boolean.TRUE);
            PrintTreeRec(localRoot.getLeft(), level + 1, connect, "0"); 
            connect.set(level, Boolean.FALSE);
            connect.set(level + 1, Boolean.TRUE);
            PrintTreeRec(localRoot.getRight(), level + 1, connect, "1");
        }
    }
    
    //Accessor
    public Node getRoot(){
        return this.root;
    }
    
    public int getSize(){
        return this.size;
    }

    /**
     * calculateSize calculates the number of characters stored in the Huffman
     * Tree. It ignores connecting inner nodes.
     * @return 
     */
    private int calculateSize() {
        int s = 0;
        if(this.root == null){
            return s;
        }
        Node r = this.root;
        return calculateSizeRec(r, s);
    }

    private int calculateSizeRec(Node n, int s) {
        if(n.getKey() != '\0'){            
            s = s + 1;
        } else{
            s = calculateSizeRec(n.getLeft(),s);
            s = calculateSizeRec(n.getRight(),s);
        }
        return s;
    }
    
}
