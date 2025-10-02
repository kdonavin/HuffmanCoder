#!/bin/bash

#System-specific variables
HUFFDIR="$(cd "$(dirname "$0")/.." && pwd)"

#A script to create a Java archive file
javac $HUFFDIR/huffman_coder/*.java
jar cfmv $HUFFDIR/huffman_coder.jar $HUFFDIR/Manifest.txt $HUFFDIR/huffman_coder/*.class #cfmv - create file, with manifest, verbose

#Concatenate files
cat $HUFFDIR/install/stub_exec.sh $HUFFDIR/huffman_coder.jar > $HUFFDIR/huffman_coder.run
chmod +x $HUFFDIR/huffman_coder.run

#Clean up
rm $HUFFDIR/huffman_coder/*.class