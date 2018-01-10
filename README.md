# HuffmanCoder

*An interactive command line program for compressing text with Huffman coding.*

This program uses a priority queue of character frequency nodes (least have priority) to construct a Huffman table and Huffman (binary) tree for encoding and decoding character strings. The Huffman tree places the most infrequent characters at greater depths and vice versa. It then may be traversed to create the Huffman table that is a character-to-binary key. That table may be used to encode the most frequent characters in a string with the fewest bits of binary values. The tree may then be used to decode those Huffman codes by searching the tree based on the binary code value until a character value leaf is located.

This program was written in Java by Kirkwood Donavin in 2016 for a Data Structures and Algorithms course @ Montana State University. It is merely a demonstration of how to perform Huffman coding, and can not (yet!) actually convert files into compressed binary files. 

## How-To

In order to use this HuffmanCoder application, please build a `huffman_coder.jar` file from `*.class` files that will be compiled from the `*.java` files in this repository. If you are using `bash` scripting language on your machine, this may accomplished by executing the included `make_jar.sh` script:

1. Execute the `make_jar.sh` script to make a `huffman_coder.jar` application
2. Run HuffmanCoder application with `run.sh`. With no arguments, the command-line application. Alternatively provide a text file as a command-line argument to be compressed [currently returned as a String of 1s and 0s]. 
