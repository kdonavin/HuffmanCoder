#!/bin/bash

echo "[HuffmanCoder Installer] Starting installation..."
#System-specific variables
HUFFDIR="$(cd "$(dirname "$0")" && pwd)"
echo "[HuffmanCoder Installer] Project directory set to $HUFFDIR"

#Java Compilation
echo "[HuffmanCoder Installer] Compiling Java source files..."
cd "$HUFFDIR"
javac huffman_coder/*.java
echo "[HuffmanCoder Installer] Creating JAR file..."
jar cfmv huffman_coder.jar Manifest.txt huffman_coder/*.class #cfmv -> create file, with manifest, verbose
echo "[HuffmanCoder Installer] JAR file created: $HUFFDIR/huffman_coder.jar"

#Shell Script Wrapper
BINDIR="$HOME/.local/bin" #Change this to your preferred bin directory and ensure it is in your PATH
echo "[HuffmanCoder Installer] Creating shell script wrapper in $BINDIR..."
mkdir -p "$BINDIR"
# Move the JAR into the bindir (overwrite if present)
mv -f "$HUFFDIR/huffman_coder.jar" "$BINDIR/huffman_coder.jar"

# A robust wrapper that uses the absolute path to the JAR.
WRAPPER_PATH="$BINDIR/huffman"
cat > "$WRAPPER_PATH" <<'EOF'
#!/usr/bin/env bash
# Find java executable
JAVA_EXEC="$(command -v java || true)"
if [ -z "$JAVA_EXEC" ]; then
	echo "Error: 'java' not found in PATH. Please install Java or add it to PATH." >&2
	exit 2
fi

# Resolve the directory containing this wrapper (in case of symlink)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]:-$0}")" && pwd)"
JAR_PATH="$SCRIPT_DIR/huffman_coder.jar"

if [ ! -f "$JAR_PATH" ]; then
	echo "Error: $JAR_PATH not found. Did the installer move huffman_coder.jar to $SCRIPT_DIR?" >&2
	exit 3
fi

exec "$JAVA_EXEC" -jar "$JAR_PATH" "$@"
EOF

chmod +x "$WRAPPER_PATH"
echo "[HuffmanCoder Installer] Wrapper script created: $WRAPPER_PATH"

#Clean up
echo "[HuffmanCoder Installer] Cleaning up class files..."
rm $HUFFDIR/huffman_coder/*.class
echo "[HuffmanCoder Installer] Installation complete! You can now run 'huffman' from the command line."