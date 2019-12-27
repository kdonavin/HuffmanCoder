package huffman_coder;

public class test {

	public static void main(String[] args) {
    	Huffman huff = new Huffman();
		huff.readText("Hello world!");
		System.out.println(huff.getText());
	}

}