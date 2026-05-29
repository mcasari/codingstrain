# Generated X Post Assets

63 posts styled like **@mario_casari** tweets from `twitter-2026-05-15`.

## Style

| Element | Format |
|---------|--------|
| Tweet text | `💡` / `🚀` hook + 3 `✅` bullets + hashtags (tweet #61 layout) |
| Code | [CodePen](https://codepen.io/) Carbon-style pens (syntax highlighting, large embed) |
| Diagrams | [diagrams.net](https://app.diagrams.net/) `.drawio` (architecture only, all tweets) |
| Java tips | `💡 Java tip: …` |

## Files

| Folder | Contents |
|--------|----------|
| `codepen/` | Standalone HTML page per tweet with a CodePen Prefill embed |
| `png/` | Diagram PNG exports (one per tweet) |
| `drawio/` | diagrams.net architecture diagrams |
| `sources/` | `.java` snippets — copy into a CodePen pen to edit |
| `index.html` | Preview tweet text + CodePen embeds |

## Regenerate

```bash
python3 generate_assets.py --skip-png   # fast: codepen + index + tweets.json
python3 generate_assets.py              # also exports diagram PNGs (slow)
```

Diagram PNGs: `npx draw.io-export`. Code pens load via [CodePen Prefill embeds](https://blog.codepen.io/documentation/prefill-embeds/).
