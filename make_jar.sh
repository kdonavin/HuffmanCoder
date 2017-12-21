#!/bin/bash

#A script to create a Java archive file
javac huffman_coder/*.java
jar cfmv huffman_coder.jar Manifest.txt huffman_coder/*.class #cfmv - creat file, with manifest, verbose