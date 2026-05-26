# Generated X Post Assets

60 tweet cards generated from `tweets.json`.

## Files

| Folder | Format | Use |
|--------|--------|-----|
| `drawio/` | `.drawio` | Open in [diagrams.net](https://app.diagrams.net/) — edit, export PNG/SVG/PDF |
| `drawio/all-tweets.drawio` | Multi-tab | All 60 tweets in one file (one tab each) |
| `svg/` | `.svg` | Ready-to-post vector images (browser, design tools) |
| `index.html` | Preview | Open locally to browse and download all cards |

## Open in diagrams.net

1. Go to https://app.diagrams.net/
2. **File → Open from → Device**
3. Select any `drawio/tweet-XX.drawio` file
4. **File → Export as → PNG** (or PDF) for raster download

## Regenerate

```bash
python3 generate_assets.py
```

## Edit tweets

Edit `tweets.json`, then run the generator again.
