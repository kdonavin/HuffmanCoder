#!/bin/bash

#A script to create a Java archive file
javac huffman_code/*.java
jar cfmv huffman_code.jar Manifest.txt huffman_code/*.class #cfmv - creat file, with manifest, verbose