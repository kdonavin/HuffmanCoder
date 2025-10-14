package huffman_coder;

import java.nio.file.Files; //For command line file import
import java.nio.file.Path;
import java.nio.file.Paths; //For command line file import
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
      System.out.println("Welcome to the HuffmanCoder!\nAn interactive demo");
      while(input.charAt(0) != 'q'){
        System.out.print("\nPlease select an option number: \n\t"
          + "1) Read in text \n\t"
          + "2) Show visual Huffman Tree \n\t"
          + "3) Display Huffman Code table \n\t"
          + "4) Encode text \n\t"
          + "5) Decode text \n\t"
          + "q) Quit\n"
          + ">> ");
        input = user_input.nextLine();
        if(input.length() == 0){
          System.out.println("You must select an option!");
          input = " "; //Avoid an empty string to continue while loop
        } else{
          switch (input.charAt(0)){
            case '1': 
              System.out.print("Enter the filename containing the text to be encoded: ");
              String filename = user_input.nextLine();
              Path p = Paths.get(filename);
              try {
                String text = new String(Files.readAllBytes(p));
                x.readText(text);
                x.createHuffmanTree();
                System.out.println("Text loaded from file: " + filename);
              } catch(IOException e) {
                System.out.println("Error reading file: " + filename);
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

            //Quiting   
            case 'q':
              System.out.println("Exiting HuffmanCoder. \n");
              break;

            //Default
            default: System.out.println("You must select a valid option!");
              break;
          }
        }
      }
    }
  }
}
