#!/bin/bash

#A script to create a Java archive file
javac ../huffman_coder/*.java
jar cfmv huffman_coder.jar Manifest.txt huffman_coder/*.class #cfmv - create file, with manifest, verbose

#Concatenate files
cat huffman_coder.jar stub_exec.sh > ../huffman_coder.run
chmod +x ../huffman_coder.run

#Install in User bin
ln -s ../huffman_coder.run $HOME/bin/huffman_coder 
