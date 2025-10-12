#!/bin/bash

echo "[HuffmanCoder Installer] Starting installation..."
#System-specific variables
HUFFDIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "[HuffmanCoder Installer] Project directory set to $HUFFDIR"

#Java Compilation
echo "[HuffmanCoder Installer] Compiling Java source files..."
cd "$HUFFDIR"
javac huffman_coder/*.java
echo "[HuffmanCoder Installer] Creating JAR file..."
jar cfmv huffman_coder.jar Manifest.txt huffman_coder/*.class #cfmv -> create file, with manifest, verbose
echo "[HuffmanCoder Installer] JAR file created: $HUFFDIR/huffman_coder.jar"

#Shell Script Wrapper
echo "[HuffmanCoder Installer] Creating shell script wrapper in $BINDIR..."
BINDIR="$HOME/.local/bin" #Change this to your preferred bin directory and ensure it is in your PATH
mkdir -p "$BINDIR"
echo '#!/bin/bash
java -jar /home/deck/HuffmanCoder/huffman_coder.jar "$@"' > "$BINDIR/huffman"
chmod +x "$BINDIR/huffman"
echo "[HuffmanCoder Installer] Wrapper script created: $BINDIR/huffman"

#Clean up
echo "[HuffmanCoder Installer] Cleaning up class files..."
rm $HUFFDIR/huffman_coder/*.class
echo "[HuffmanCoder Installer] Installation complete! You can now run 'huffman' from the command line."