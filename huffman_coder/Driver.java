package huffman_coder;

import java.nio.file.Files; //For command line file import
import java.nio.file.Path;
import java.nio.file.Paths; //For command line file import
import java.io.IOException;
import java.util.Scanner;
import java.util.BitSet;

public class Driver {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    //Setup
    Huffman x = new Huffman();

    //Command-line input
    if (args.length > 0) {
      String filename = args[0];
      if (filename.endsWith(".huf")) {
        // Decode mode
        x.readHuffman(filename);
        try {
          x.decode();
          String outFile = filename.substring(0, filename.length() - 4);
          Files.write(Paths.get(outFile), x.getText().getBytes());
          System.out.println("Decoded text saved to: " + outFile);
        } catch (Exception e) {
          System.out.println("Error decoding Huffman file: " + filename);
        }
      } else {
        // Encode mode
        Path p = Paths.get(filename);
        try {
          String text = new String(Files.readAllBytes(p));  
          x.readText(text);
          x.createHuffmanTree();
          x.encode();
          String outFile = filename + ".huf";
          x.writeHuffman(outFile);
          System.out.println("Huffman object saved to: " + outFile);
        } catch(IOException e){
          System.out.println("Error reading or writing file: " + filename);
        }
      }
    }

    //Interactive demo
    else{
      x.readText("");
      Scanner user_input = new Scanner( System.in );
      String input = "0";
      String loadedFilename = null;
      System.out.println("Welcome to the HuffmanCoder!\nAn interactive demo");
        while(input.charAt(0) != 'q'){
          System.out.print("\nPlease select an option number: \n\t"
            + "1) Read in file \n\t"
            + "2) Show visual Huffman Tree \n\t"
            + "3) Display Huffman Code table \n\t"
            + "4) Encode text \n\t"
            + "5) Decode compressed data \n\t"
            + "6) Write out data \n\t"
            + "7) Print data summary \n\t"
            + "q) Quit\n"
            + ">> ");
        input = user_input.nextLine();
        if(input.length() == 0){
          System.out.println("You must select an option!");
          input = " "; //Avoid an empty string to continue while loop
        } else{
          switch (input.charAt(0)){
            case '1': 
              System.out.print("Enter the filename to load (.huf or text): ");
              loadedFilename = user_input.nextLine();
              if (loadedFilename.endsWith(".huf")) {
                x.readHuffman(loadedFilename);
                System.out.println("Huffman object loaded from file: " + loadedFilename);
              } else {
                Path p = Paths.get(loadedFilename);
                try {
                  String text = new String(Files.readAllBytes(p));
                  x.readText(text);
                  x.createHuffmanTree();
                  System.out.println("Text loaded from file: " + loadedFilename);
                } catch(IOException e) {
                  System.out.println("Error reading file: " + loadedFilename);
                  loadedFilename = null;
                }
              }
              break;
            case '2': 
              if(x.huffmanExists()){
                x.getHuffmanTree().PrintTree();
              } else{
                System.out.println("Error: Text must first be entered.");
              }
              break;
            case '3': 
              if(x.huffmanExists()){ 
                x.printHuffmanCodes();
              } else{
                System.out.println("Error: Text must first be entered.");                            
              }
              break; 
            case '4': 
              if(x.huffmanExists() && x.getText() != null){
                x.encode();
                System.out.println("\nText has been encoded successfully.");
              } else if(x.huffmanExists() && x.getText() == null){
                System.out.println("Error: Text has already been encoded.");
              } else{
                System.out.println("Error: Text must be provided to encode.");                            
              }
              break;       
            case '5':
              if(x.huffmanExists()){
                try{
                  x.decode();
                  System.out.println("\nDecoded Text:\n" + x.getText());
                } catch(NullPointerException e){
                  System.out.println("Error: There is no encoded text to decode.");
                }
              } else{
                System.out.println("Error: Text must be provided to decode.");
              }
              break;
            case '6':
              if (loadedFilename == null) {
                System.out.println("No file loaded. Please use option 1 first.");
              } else if (x.getEncode() != null) {
                // Check if filename has .huf extension
                String outFile = loadedFilename.endsWith(".huf") ? loadedFilename : loadedFilename + ".huf";
                x.writeHuffman(outFile);
                System.out.println("Huffman object saved to: " + outFile);
              } else if (x.getText() != null && loadedFilename.endsWith(".huf")) {
                // Decoded, save as original filename without .huf
                String outFile = loadedFilename.substring(0, loadedFilename.length() - 4);
                try {
                  Files.write(Paths.get(outFile), x.getText().getBytes());
                  System.out.println("Decoded text saved to: " + outFile);
                } catch (IOException e) {
                  System.out.println("Error writing decoded text to file: " + outFile);
                }
              } else if (x.getText() != null) {
                // Encoded, save Huffman object as filename.huf
                String outFile = loadedFilename;
                try {
                  Files.write(Paths.get(outFile), x.getText().getBytes());
                  System.out.println("Text saved to: " + outFile);
                } catch (IOException e) {
                  System.out.println("Error writing text to file: " + outFile);
                }
              } else {
                System.out.println("Nothing to write out.");
              }
              break;
            case '7':
              if (x.getText() != null) {
                String textToPrint;
                if (x.getText().length() > 500) {
                  textToPrint = x.getText().substring(0, 500);
                  System.out.println("First 500 characters of text:\n" + textToPrint + "...");
                } else {
                  textToPrint = x.getText();
                  System.out.println("Text:\n" + textToPrint);
                }
              } else if (x.getEncode() != null) {
                StringBuilder bitStr = new StringBuilder(500);
                int bitLen = x.getEncode().length();
                int maxBits = Math.min(500, bitLen);
                for (int i = 0; i < maxBits; i++) {
                  bitStr.append(x.getEncode().get(i) ? '1' : '0');
                }
                if (bitLen > 500) {
                  System.out.println("First 500 bits of encoded data:\n" + bitStr + "...");
                } else {
                  System.out.println("Encoded bits:\n" + bitStr);
                }
              } else {
                System.out.println("No data loaded to summarize.");
              }
              break;
            case 'q':
              System.out.println("Exiting HuffmanCoder. \n");
              break;
            default: System.out.println("You must select a valid option!");
              break;
          }
        }
      }
      user_input.close(); // Close Scanner to prevent resource leak
    }
  }
}
