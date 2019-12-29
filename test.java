package huffman_coder;
import java.io.*;

public class test implements Serializable {

	int test_int = 1;
    private static final long serialVersionUID = 1234L;

	public test(){};

	public static void main(String[] args) {
		Huffman huff = new Huffman();
		huff.readText("Haley is special because reasons. Here are some of them. She is helpful. She is kind.\nHere is a new line. And, there are always symbols: $#@");
		huff.createHuffmanTree();
		huff.encode();
		huff.writeHuffman("test.Object");
		huff.readHuffman("test.Object");
		try { System.out.println(huff.getText()); } catch(NullPointerException e) {System.out.println("No text");}
	}

}