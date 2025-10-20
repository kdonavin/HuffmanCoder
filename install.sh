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

# Parse args
print_usage() {
	cat <<USAGE
Usage: $0 [--bindir DIR]

Options:
	--bindir DIR, -b DIR   Install wrapper and JAR into DIR (default: $HOME/.local/bin)
	--help                 Show this help message
USAGE
}

while [ $# -gt 0 ]; do
	case "$1" in
		--bindir|-b)
			BINDIR="$2"
			shift 2
			;;
		--help)
			print_usage
			exit 0
			;;
		--*)
			echo "Unknown option: $1" >&2
			print_usage
			exit 1
			;;
		*)
			echo "Unexpected argument: $1" >&2
			print_usage
			exit 1
			;;
	esac
done

#Shell Script Wrapper
BINDIR="${BINDIR:-$HOME/.local/bin}" #Change this to your preferred bin directory and ensure it is in your PATH
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

# Automated smoke test: run the wrapper with --help (timeout after 5s if available)
echo "[HuffmanCoder Installer] Running smoke test..."
SMOKE_CMD=("$WRAPPER_PATH" "--help")
SMOKE_RC=0
if command -v timeout >/dev/null 2>&1; then
	timeout 5s "${SMOKE_CMD[@]}" >/dev/null 2>&1 || SMOKE_RC=$?
else
	# fallback: run in background and give it 5s
	"${SMOKE_CMD[@]}" >/dev/null 2>&1 &
	_smoke_pid=$!
	sleep 5
	if kill -0 "$_smoke_pid" >/dev/null 2>&1; then
		kill "$_smoke_pid" >/dev/null 2>&1 || true
		SMOKE_RC=124
	else
		wait "$_smoke_pid" || SMOKE_RC=$?
	fi
fi

if [ "$SMOKE_RC" -eq 0 ]; then
	echo "[HuffmanCoder Installer] Smoke test passed. The 'huffman' command should work from your PATH."
else
	if [ "$SMOKE_RC" -eq 124 ]; then
		echo "[HuffmanCoder Installer] Smoke test timed out (the program didn't exit quickly). Please run '$WRAPPER_PATH --help' manually to verify." >&2
	else
		echo "[HuffmanCoder Installer] Smoke test failed with exit code $SMOKE_RC. You can run '$WRAPPER_PATH --help' to diagnose." >&2
	fi
fi

#Clean up
echo "[HuffmanCoder Installer] Cleaning up class files..."
rm $HUFFDIR/huffman_coder/*.class
echo "[HuffmanCoder Installer] Installation complete! You can now run 'huffman' from the command line."