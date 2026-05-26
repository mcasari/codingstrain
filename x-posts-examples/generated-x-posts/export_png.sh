#!/usr/bin/env bash
# Optional: batch-export .drawio → PNG (requires Node.js + draw.io-export).
# Usage: ./export_png.sh
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
OUT="$ROOT/png"
mkdir -p "$OUT"
for f in "$ROOT"/drawio/tweet-*.drawio; do
  base=$(basename "$f" .drawio)
  echo "Exporting $base ..."
  npx --yes draw.io-export "$f" -o "$OUT/${base}.png"
done
echo "Done. PNG files in $OUT"
