
package huffman_coder;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Kirkwood Donavin
 */
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
      File file = new File(filename);
      x.readText("This report will be an investigation of the commercial success of underserved states, districts and territories (i.e., AK, AR, DC, DE, HI, IA, ID, KS, KY, LA, ME, MO, MS, MT, ND`, NE, NV, OK, PR, RI, SC, SD, TN, UT, VT, WV, WY). It is meant to extend the underserved-state analysis previously included in TechLink's report on the economic impact of the AF's SBIR program.");
      x.createHuffmanTree();
      x.printHuffmanCodes();
      x.encode();
      System.out.println(x.getEncode());
    } 

    //Interactive demo
    else{
      x.readText("");
      Scanner user_input = new Scanner( System.in );
      String input = "0";
      System.out.println("Welcome to the Huffman Coder!\nAn interactive demo");
      while(input.charAt(0) != '6'){
        System.out.print("\nPlease select an option number: \n\t"
          + "1) Read in text \n\t"
          + "2) Show visual Huffman Tree \n\t"
          + "3) Display Huffman Code table \n\t"
          + "4) Encode text \n\t"
          + "5) Decode text \n\t"
          + "6) Quit\n"
          + ">> ");
        input = user_input.nextLine();
        if(input.length() == 0){
          System.out.println("You must select an option!");
          input = " "; //Avoid an empty string to continue while loop
        } else{
          switch (input.charAt(0)){
            case '1': 
              System.out.print("Enter the text to be encoded: ");
              String s = user_input.nextLine();
              x.readText(s);
              x.createHuffmanTree();
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
              if(x.huffmanExists()){
                x.encode();
                System.out.println("\nEncoded Text:\n" + x.getEncode());
              } else{
                System.out.println("Error: Text must first be entered.");                            
              }
              break;       
            case '5':
              if(x.huffmanExists() && x.getEncode()!=null){
              x.decode();
                System.out.println("\nDecoded Text:\n" + x.getDecode());
              } else{
                System.out.println("Error: There is no encoded text to decode.");
              }
              break;   
            case '6' | 'q':
              System.out.println("Exiting Huffman Coder. \n");
              break;
            default: System.out.println("You must select a valid option!");
              break;
          }
        }
      }
    }
  }
}
