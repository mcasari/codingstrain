# Generated X Post Assets (Carbon style)

60 tweet images in [Carbon.now.sh](https://carbon.now.sh/) style.

## Files

| Folder | Format | Use |
|--------|--------|-----|
| `carbon/` | `.svg` | Carbon-style code images — post on X or convert to PNG |
| `sources/` | `.java` | Paste into [carbon.now.sh](https://carbon.now.sh/) to customize theme & export |
| `drawio/` | `.drawio` | Optional — edit in diagrams.net |
| `index.html` | Preview | Browse all cards locally |

## Export PNG from Carbon (official)

1. Open https://carbon.now.sh/
2. Paste content from `sources/tweet-XX.java` (or drag the file)
3. Pick theme (e.g. **Night Owl**, **One Dark**)
4. **Export → PNG**

## Batch PNG via CLI (optional)

```bash
./export_carbon_png.sh
```

Requires Node.js; uses [carbon-now-cli](https://github.com/mixn/carbon-now-cli).

## Regenerate SVGs

```bash
python3 generate_assets.py
```

Edit `tweets.json` first if you change copy or code snippets.
