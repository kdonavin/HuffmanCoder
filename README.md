# HuffmanCoder

*An interactive command line program for compressing text with Huffman codes.*

Huffman coding is a method of lossless data compression where common characters are assigned shorter binary codes, while uncommon characters are reserved with longer codes. These binary codes can be used to retrieve the original character by traversing a pre-constructed Huffman binary tree that will end in a leaf node containing the original character. In this way, the text information can be stored using fewer bits, alongside a corresponding binary-tree key to retrieve _all_ the original textual information. 

This program uses a priority queue of character frequency nodes (least have priority) to construct a Huffman table and Huffman (binary) tree for encoding and decoding character strings. The Huffman tree places the most infrequent characters at greater depths and vice versa. It then may be traversed to create a Huffman Table that is a character-to-binary reference. That table may be used to encode the most frequent characters in a string with the fewest bits of binary values. The tree may then be used to decode those Huffman codes by searching the tree based on the binary code value until a character value leaf is located.

This program was created by Kirkwood Donavin starting in 2016 for a Data Structures and Algorithms course @ Montana State University. 

## How-To

To use the HuffmanCoder application, simply run the included `install.sh` script:

1. Execute the `install.sh` script to build the `huffman_coder.jar` and create a convenient executable wrapper script called `huffman`.
2. Ensure the executable (`huffman`) is placed in a directory that is in your system's `PATH` (e.g., `~/.local/bin`). The installer will do this by default if you have `~/.local/bin` in your `PATH`.
3. Run the HuffmanCoder application from the command line with:
	```
	huffman
	```
	With no arguments, `huffman` enters an interactive command-line application that demonstrates Huffman coding. Alternatively, you may provide a text file as a command-line argument to be compressed (currently returned as a String of 1s and 0s).
