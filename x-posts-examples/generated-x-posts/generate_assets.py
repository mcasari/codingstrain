#!/usr/bin/env python3
"""
Generate diagrams.net (.drawio) and SVG image files for each tweet.
Stdlib only — no pip dependencies.
"""

from __future__ import annotations

import html
import json
import re
import textwrap
import uuid
from pathlib import Path

ROOT = Path(__file__).resolve().parent
TWEETS_FILE = ROOT / "tweets.json"
DRAWIO_DIR = ROOT / "drawio"
SVG_DIR = ROOT / "svg"
INDEX_HTML = ROOT / "index.html"

# Card dimensions (diagrams.net + SVG)
CARD_W = 560
CARD_H_BASE = 120
CODE_H_PER_LINE = 18
BODY_LINE_H = 22
PADDING = 24


def xml_escape(text: str) -> str:
    return (
        text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace('"', "&quot;")
    )


def wrap_lines(text: str, width: int = 52) -> list[str]:
    lines: list[str] = []
    for paragraph in text.split("\n"):
        if not paragraph.strip():
            lines.append("")
            continue
        lines.extend(textwrap.wrap(paragraph, width=width) or [""])
    return lines


def estimate_height(body: str, code: str | None) -> int:
    body_lines = wrap_lines(body, 50)
    h = PADDING * 2 + 50 + len(body_lines) * BODY_LINE_H
    if code:
        code_lines = code.split("\n")
        h += 20 + len(code_lines) * CODE_H_PER_LINE + PADDING
    h += 40  # footer
    return max(h, 280)


def build_drawio(tweet: dict) -> str:
    tid = tweet["id"]
    body = tweet["body"]
    code = tweet.get("code")
    module = tweet.get("module", "")
    tags = tweet.get("tags", "")
    height = estimate_height(body, code)

    body_display = xml_escape(body.replace("\n", "&#xa;"))
    header = xml_escape(f"Tweet #{tid} · codingstrain")
    footer = xml_escape(f"Module: {module}  {tags}")

    cells = [
        '        <mxCell id="0"/>',
        '        <mxCell id="1" parent="0"/>',
        # Background card
        f'''        <mxCell id="bg" value="" style="rounded=1;whiteSpace=wrap;html=1;fillColor=#15202b;strokeColor=#38444d;strokeWidth=2;arcSize=8;" vertex="1" parent="1">
          <mxGeometry x="20" y="20" width="{CARD_W}" height="{height}" as="geometry"/>
        </mxCell>''',
        # Header bar
        f'''        <mxCell id="hdr" value="" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#1DA1F2;strokeColor=none;arcSize=0;" vertex="1" parent="1">
          <mxGeometry x="20" y="20" width="{CARD_W}" height="44" as="geometry"/>
        </mxCell>''',
        f'''        <mxCell id="hdr-txt" value="{header}" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=middle;fontColor=#ffffff;fontSize=14;fontStyle=1;spacingLeft=16;" vertex="1" parent="1">
          <mxGeometry x="20" y="20" width="{CARD_W - 20}" height="44" as="geometry"/>
        </mxCell>''',
        # Body
        f'''        <mxCell id="body" value="{body_display}" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=top;fontColor=#e7e9ea;fontSize=13;spacingLeft=20;spacingTop=12;whiteSpace=wrap;" vertex="1" parent="1">
          <mxGeometry x="20" y="72" width="{CARD_W - 40}" height="{height - 120}" as="geometry"/>
        </mxCell>''',
    ]

    y_code = 72 + len(wrap_lines(body, 50)) * 20 + 10
    if code:
        code_display = xml_escape(code.replace("\n", "&#xa;"))
        code_h = len(code.split("\n")) * CODE_H_PER_LINE + 24
        cells.append(
            f'''        <mxCell id="code-bg" value="" style="rounded=1;whiteSpace=wrap;html=1;fillColor=#192734;strokeColor=#38444d;strokeWidth=1;" vertex="1" parent="1">
          <mxGeometry x="36" y="{y_code}" width="{CARD_W - 72}" height="{code_h}" as="geometry"/>
        </mxCell>'''
        )
        cells.append(
            f'''        <mxCell id="code" value="{code_display}" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=top;fontColor=#89cff0;fontSize=11;fontFamily=Courier New;spacingLeft=12;spacingTop=8;whiteSpace=wrap;" vertex="1" parent="1">
          <mxGeometry x="36" y="{y_code}" width="{CARD_W - 72}" height="{code_h}" as="geometry"/>
        </mxCell>'''
        )

    cells.append(
        f'''        <mxCell id="ftr" value="{footer}" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=middle;fontColor=#71767b;fontSize=10;spacingLeft=20;" vertex="1" parent="1">
          <mxGeometry x="20" y="{height - 8}" width="{CARD_W - 40}" height="28" as="geometry"/>
        </mxCell>'''
    )

    diagram_id = str(uuid.uuid4())
    cells_xml = "\n".join(cells)

    return f'''<mxfile host="app.diagrams.net" agent="codingstrain-generator" version="22.1.0" type="device">
  <diagram id="{diagram_id}" name="Tweet {tid}">
    <mxGraphModel dx="1200" dy="800" grid="1" gridSize="10" guides="1" tooltips="1" connect="0" arrows="0" fold="1" page="1" pageScale="1" pageWidth="600" pageHeight="{height + 40}" math="0" shadow="0">
      <root>
{cells_xml}
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
'''


def svg_text_block(lines: list[str], x: int, y: int, line_height: int, css_class: str) -> str:
    parts = []
    for i, line in enumerate(lines):
        esc = html.escape(line)
        parts.append(
            f'    <text x="{x}" y="{y + i * line_height}" class="{css_class}">{esc}</text>'
        )
    return "\n".join(parts)


def build_svg(tweet: dict) -> str:
    tid = tweet["id"]
    body = tweet["body"]
    code = tweet.get("code")
    module = tweet.get("module", "")
    tags = tweet.get("tags", "")
    height = estimate_height(body, code)

    body_lines = wrap_lines(body, 48)
    body_svg = svg_text_block(body_lines, 28, 100, BODY_LINE_H, "body")

    code_svg = ""
    if code:
        y0 = 100 + len(body_lines) * BODY_LINE_H + 16
        code_lines = code.split("\n")
        code_svg = f'  <rect x="24" y="{y0 - 18}" width="{CARD_W - 48}" height="{len(code_lines) * CODE_H_PER_LINE + 28}" rx="6" class="code-bg"/>\n'
        code_svg += svg_text_block(code_lines, 36, y0, CODE_H_PER_LINE, "code")

    return f'''<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" width="{CARD_W}" height="{height}" viewBox="0 0 {CARD_W} {height}">
  <defs>
    <style>
      .card {{ fill: #15202b; stroke: #38444d; stroke-width: 2; }}
      .header {{ fill: #1DA1F2; }}
      .title {{ fill: #ffffff; font: bold 14px sans-serif; }}
      .body {{ fill: #e7e9ea; font: 13px sans-serif; }}
      .code {{ fill: #89cff0; font: 11px monospace; }}
      .code-bg {{ fill: #192734; stroke: #38444d; stroke-width: 1; }}
      .footer {{ fill: #71767b; font: 10px sans-serif; }}
    </style>
  </defs>
  <rect x="0" y="0" width="{CARD_W}" height="{height}" rx="12" class="card"/>
  <rect x="0" y="0" width="{CARD_W}" height="44" rx="12" class="header"/>
  <rect x="0" y="32" width="{CARD_W}" height="12" class="header"/>
  <text x="20" y="28" class="title">Tweet #{tid} · codingstrain</text>
{body_svg}
{code_svg}
  <text x="20" y="{height - 16}" class="footer">Module: {html.escape(module)}  {html.escape(tags)}</text>
</svg>
'''


def build_index(tweets: list[dict]) -> str:
    rows = []
    for t in tweets:
        tid = t["id"]
        rows.append(
            f"""    <article>
      <h2><a href="drawio/tweet-{tid:02d}.drawio">Tweet #{tid}</a></h2>
      <img src="svg/tweet-{tid:02d}.svg" alt="Tweet {tid}" width="560"/>
      <p class="links">
        <a href="drawio/tweet-{tid:02d}.drawio" download>Download .drawio</a> ·
        <a href="svg/tweet-{tid:02d}.svg" download>Download .svg</a>
      </p>
      <pre>{html.escape(t['body'][:200])}{'…' if len(t['body']) > 200 else ''}</pre>
    </article>"""
        )
    body = "\n".join(rows)
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <title>codingstrain X Posts — Spring Boot &amp; Java</title>
  <style>
    body {{ font-family: system-ui, sans-serif; max-width: 640px; margin: 2rem auto; padding: 0 1rem; background: #f5f8fa; }}
    article {{ background: #fff; border-radius: 12px; padding: 1rem; margin-bottom: 2rem; box-shadow: 0 1px 3px rgba(0,0,0,.1); }}
    h2 {{ margin: 0 0 .5rem; font-size: 1rem; }}
    pre {{ font-size: 12px; color: #536471; white-space: pre-wrap; }}
    .links a {{ color: #1DA1F2; }}
    .banner {{ background: #1DA1F2; color: #fff; padding: 1rem; border-radius: 8px; margin-bottom: 2rem; }}
  </style>
</head>
<body>
  <div class="banner">
    <h1>Spring Boot &amp; Java X Posts</h1>
    <p>60 tweet cards · Open <code>.drawio</code> files in <a href="https://app.diagrams.net/">diagrams.net</a> to edit or export PNG/PDF.</p>
  </div>
{body}
</body>
</html>
"""


def build_combined_drawio(tweets: list[dict]) -> str:
    """Single .drawio file with one tab per tweet (open all in diagrams.net)."""
    parts = [
        '<mxfile host="app.diagrams.net" agent="codingstrain-generator" version="22.1.0" type="device">'
    ]
    for tweet in tweets:
        single = build_drawio(tweet)
        diagram_match = re.search(
            r"<diagram[^>]*>.*?</diagram>", single, re.DOTALL
        )
        if diagram_match:
            parts.append(diagram_match.group(0))
    parts.append("</mxfile>")
    return "\n  ".join(parts)


def main() -> None:
    tweets = json.loads(TWEETS_FILE.read_text(encoding="utf-8"))
    DRAWIO_DIR.mkdir(parents=True, exist_ok=True)
    SVG_DIR.mkdir(parents=True, exist_ok=True)

    for tweet in tweets:
        tid = tweet["id"]
        stem = f"tweet-{tid:02d}"
        (DRAWIO_DIR / f"{stem}.drawio").write_text(
            build_drawio(tweet), encoding="utf-8"
        )
        (SVG_DIR / f"{stem}.svg").write_text(build_svg(tweet), encoding="utf-8")

    (DRAWIO_DIR / "all-tweets.drawio").write_text(
        build_combined_drawio(tweets), encoding="utf-8"
    )
    INDEX_HTML.write_text(build_index(tweets), encoding="utf-8")

    readme = f"""# Generated X Post Assets

{len(tweets)} tweet cards generated from `tweets.json`.

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
"""
    (ROOT / "README.md").write_text(readme, encoding="utf-8")
    print(f"Generated {len(tweets)} .drawio + {len(tweets)} .svg + all-tweets.drawio")
    print(f"  drawio/ → {DRAWIO_DIR}")
    print(f"  svg/    → {SVG_DIR}")
    print(f"  preview → {INDEX_HTML}")


if __name__ == "__main__":
    main()
