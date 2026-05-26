#!/usr/bin/env bash
# Export Carbon.now.sh PNGs via carbon-now-cli (https://github.com/mixn/carbon-now-cli)
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
OUT="$ROOT/carbon/png"
mkdir -p "$OUT"

SETTINGS='{"theme":"nightowl","backgroundMode":"gradient","backgroundImage":null,"paddingVertical":"56px","paddingHorizontal":"56px","windowControls":true,"fontFamily":"Fira Code","fontSize":"14px","lineNumbers":true}'

for f in "$ROOT"/sources/tweet-*.java; do
  base=$(basename "$f" .java)
  echo "Carbon export: $base ..."
  npx --yes carbon-now-cli "$f" \
    --save-to "$OUT" \
    --save-as "$base" \
    --settings "$SETTINGS" \
    --skip-display 2>/dev/null || npx --yes carbon-now-cli "$f" --save-to "$OUT" --save-as "$base" --skip-display
done
echo "Done → $OUT"
