package huffman_coder;
import java.io.*;// For writing and reading 
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.PriorityQueue;

/**
 *
 * @author kirkwooddonavin
 */
public class Huffman {
    
    private String text;
    private BitSet encode;
    private String decode;
    private Map<String,Integer> counts;
    private Map<String,String> codes;
    private HuffmanTree huffmanTree;
    
    //Constructor
    public Huffman(){
    }
    
    /**
     * @param s - a String of text to be compressed
     * @return m - a HashMap of frequencies of each individual character in the 
     * String
     */
    public Map<String,Integer> readText(String s){
        if(s == null){
            throw new NullPointerException();
        }
        if(s.isEmpty()){
            return this.counts;
        }
        this.text = s;
        String[] cArr = s.split("");
        Map<String,Integer> m  = new HashMap<>();
        for(String single : cArr){
            if(m.containsKey(single)){
                m.put(single,m.get(single)+1);
            } else{
                m.put(single,1);
            }
        }
        this.counts = m;
        return m;
    }
    
    /**
     * Creates a Huffman tree with the largest weighted keys near
     * the top and smallest weighted keys near the bottom
     * @return A HuffmanTree object, also stored in `huffmanTree'
     * field. Returns a null HuffmanTree if counts is null.
     */
    public HuffmanTree createHuffmanTree(){
        if(this.counts == null){
            return null;
        }
        Map<String,Integer> m = this.counts;
        Queue<Node> que = new PriorityQueue<>();
        for(String single: m.keySet()){
            Node n = new Node(single.toCharArray()[0], m.get(single));
            que.add(n);
        }
        //Saving Queue
        while(que.size()>=2){
            Node one = que.poll();
            Node two = que.poll();
            //Creating a new node with 'one' and 'two' and reinserting
            que.offer(new Node(one,two));
        }
        this.huffmanTree = new HuffmanTree(que.poll());
        //Saving commands by creating a code Map along with the Huffman Tree.
        this.createCodeMap();
        return this.huffmanTree;
    }
    
    /**
     * This method prints out each key, it's frequency and it's Huffman code.
     */
    public void printHuffmanCodes(){
        if(this.counts == null || this.codes == null){
            return;
        }
        System.out.println("\t"+"Huffman Codes");
        System.out.println("---------------------------");
        System.out.println("Key"+"\t"+ "Freq." + "\t" + "Code");
        for(String key: this.codes.keySet()){
            System.out.println(key + "\t" + this.counts.get(key) + 
                    "\t" + this.codes.get(key));
        }
    }

    /**
    * Prints out a 1s and 0s String representation of the compressed encode
    */
    public void printEncode(){
        //for(int i = 0, i < this.encode)
    }
    
    /**
     * Needs explanation. Where and why is this used. Causing unchecked conversion warning
     */
    private Map<String,String> createCodeMap(){
        HuffmanTree tree = this.huffmanTree;
        Map<String,String> m = new HashMap<>();
        if(tree.getSize()==1){
            m.put(Character.toString(tree.getRoot().getKey()), "0");
            this.codes = m;
        } else{
            this.codes = createCodeMapRec(tree.getRoot(),m, new StringBuffer());
        }
        return this.codes;
    }
    
    /**
     * Decodes the Huffman coded String of 0's and 1's and returns the original 
     * text
     * @return decoded text that was previously encoded with a Huffman Tree 
     */
    public String decode(){
        assert this.encode != null;
        assert this.huffmanTree != null;
        StringBuffer d = new StringBuffer();
        Node current = this.huffmanTree.getRoot();
        if(this.huffmanTree.getSize()>1){  //To avoid traversing if tree is just a root
            for(int i = 0; i < this.encode.length(); i++){
                if(this.encode.get(i) == false){
                    current = current.getLeft();
                } else {
                    current = current.getRight();
                }
                if(current.getKey() != '\0'){
                    d.append(String.valueOf(current.getKey()));
                    current = this.huffmanTree.getRoot();
                }
            }
        } else{
            d.append(String.valueOf(current.getKey()));
        }
        this.text = d.toString();
        this.encode = null; //avoid storing codes and text simultaneously
        return this.text;
    }
    
    /**
     * This method uses a Huffman code Map to encode each character in this 
     * program's text string into binary. Throws NullPointerException if either 
     * text or codes are null
     * @return encode, the encoded text
     */
    public BitSet encode() throws NullPointerException {
        if(this.text == null | this.codes == null){
            throw new NullPointerException();
        }
        StringBuffer s = new StringBuffer();
        String[] cArr = this.text.split("");
        for(String single: cArr){
            s.append(this.codes.get(single));
        }
        BitSet b = new BitSet(s.length()); //Compression

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '1'){
                b.set(i);
            }
        }
        this.encode = b;
        this.text = null; //avoid storing codes and text simultaneously
        return this.encode;
    }

    public Boolean huffmanExists(){
        return this.huffmanTree != null && this.codes != null;
    }
    
    //Access methods
    
    /**
     * Access method for this' count Map
     * @return A Map containing each key and it's weight (frequency) from the 
     * given text.
     */
    public Map<String, Integer> getCounts(){
        return this.counts;
    }
    
    /**
     * Access method for code Map
     * @return A Map containing each key and it's Huffman code.
     */
    public Map<String, String> getHuffmanCodes(){
        return this.codes;
    }
    
    public HuffmanTree getHuffmanTree(){
        return this.huffmanTree;
    }
    
    /**
     * Access method for encoded text
     * @return encoded text
     */
    public BitSet getEncode(){
        if(this.encode == null){
            throw new NullPointerException();
        }
        return this.encode;
    }
    
    /**
     * Access method for text
     * @return decoded text that was originally provided
     */
    public String getText(){
        return this.text;
    }
    
    /**
     * Write out huffman object to 'file_name'
     */
    public void writeHuffman(String file_name){
        try{
            FileOutputStream file = new FileOutputStream(file_name);
            try {
                ObjectOutputStream output = new ObjectOutputStream(file);
                output.writeObject(this);
                output.close();
            } catch(IOException e){
                System.out.println(e);
            }
        } catch(FileNotFoundException e){
            System.out.println(e);
        }
        
    } 
   
    /////Private Methods

    /**
     * A private recursive method that creates a Map the String sequence of left children 
     * (0's) and right children (1's) necessary to traverse in order to reach 
     * each key's String value in this program's HuffmanTree.
     * @param n - The current node
     * @param m - The current Map
     * @param code
     * @return 
     */
    private Map createCodeMapRec(Node n, Map<String,String> m, StringBuffer code) {
        if(n == null){
            throw new NullPointerException();
        }
        if(m == null){
            throw new NullPointerException();
        }
        if(n.getKey() != '\0'){
            m.put(String.valueOf(n.getKey()),code.toString());
        } else{
            //traverse left
            code.append('0');
            createCodeMapRec(n.getLeft(),m,code);
            code.deleteCharAt(code.length()-1);
            //traverse right
            code.append('1');
            createCodeMapRec(n.getRight(),m,code);
            code.deleteCharAt(code.length()-1);
        }
        return m;
    }
}
