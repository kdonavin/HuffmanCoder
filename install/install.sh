#!/bin/bash

#System-specific variables - Change as you wish
HUFFDIR=$HOME/HuffmanCoder
LINK=$HOME/bin/huffman_coder.run 

#A script to create a Java archive file
javac $HUFFDIR/huffman_coder/*.java
jar cfmv $HUFFDIR/huffman_coder.jar $HUFFDIR/Manifest.txt $HUFFDIR/huffman_coder/*.class #cfmv - create file, with manifest, verbose

#Concatenate files
cat $HUFFDIR/install/stub_exec.sh $HUFFDIR/huffman_coder.jar > $HUFFDIR/huffman_coder.run
chmod +x $HUFFDIR/huffman_coder.run

#Create link in User bin
if test -L $LINK; then
	echo 'Overwriting Link'
	rm $LINK
	ln -s $HUFFDIR/huffman_coder.run $LINK
else
	ln -s $HUFFDIR/huffman_coder.run $LINK
fi
