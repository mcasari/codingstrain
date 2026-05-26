#!/usr/bin/env python3
"""
Generate Carbon.now.sh-style images (SVG) and optional .drawio files for tweets.
Stdlib only for SVG generation.
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
CARBON_DIR = ROOT / "carbon"
SOURCES_DIR = ROOT / "sources"
INDEX_HTML = ROOT / "index.html"

# Carbon Night Owl–inspired theme (https://carbon.now.sh/)
THEME = {
    "bg_gradient_start": "#667eea",
    "bg_gradient_end": "#764ba2",
    "window_bg": "#21252b",
    "editor_bg": "#011627",
    "dot_red": "#ff5f56",
    "dot_yellow": "#ffbd2e",
    "dot_green": "#27c93f",
    "line_number": "#4b6479",
    "text": "#d6deeb",
    "keyword": "#c792ea",
    "annotation": "#ffcb6b",
    "string": "#c3e88d",
    "type": "#ffcb6b",
    "method": "#82aaff",
    "comment": "#637777",
    "number": "#f78c6c",
    "punctuation": "#7fdbca",
}

FONT = "ui-monospace, 'Cascadia Code', 'Fira Code', 'JetBrains Mono', Consolas, monospace"
FONT_SIZE = 14
LINE_HEIGHT = 22
CHAR_W = 8.45
LINE_NUM_W = 48
OUTER_PAD = 40
TITLE_H = 36
INNER_PAD = 20
WINDOW_W = 680
MAX_CODE_LINES = 28


JAVA_KEYWORDS = frozenset({
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
    "class", "const", "continue", "default", "do", "double", "else", "enum",
    "extends", "final", "finally", "float", "for", "goto", "if", "implements",
    "import", "instanceof", "int", "interface", "long", "native", "new",
    "package", "private", "protected", "public", "return", "short", "static",
    "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
    "transient", "try", "void", "volatile", "while", "var", "record", "sealed",
    "permits", "yield", "true", "false", "null",
})


def xml_escape(text: str) -> str:
    return html.escape(text, quote=True)


def wrap_lines(text: str, width: int = 72) -> list[str]:
    lines: list[str] = []
    for paragraph in text.split("\n"):
        if not paragraph.strip():
            lines.append("")
            continue
        lines.extend(textwrap.wrap(paragraph, width=width) or [""])
    return lines


def tweet_source(tweet: dict) -> str:
    """Content shown inside the Carbon code window."""
    if tweet.get("code"):
        return tweet["code"].rstrip()
    body = tweet["body"].rstrip()
    return "\n".join(f"// {line}" if line.strip() else "//" for line in body.split("\n"))


def tokenize_java_line(line: str) -> list[tuple[str, str]]:
    """Return (css_class, text) segments for one line."""
    segments: list[tuple[str, str]] = []
    i = 0
    n = len(line)

    while i < n:
        if line[i] in " \t":
            j = i + 1
            while j < n and line[j] in " \t":
                j += 1
            segments.append(("text", line[i:j]))
            i = j
            continue

        if line.startswith("//", i) or line.startswith("/*", i):
            segments.append(("comment", line[i:]))
            break

        if line[i] in '"\'':
            q = line[i]
            j = i + 1
            while j < n:
                if line[j] == "\\" and j + 1 < n:
                    j += 2
                    continue
                if line[j] == q:
                    j += 1
                    break
                j += 1
            segments.append(("string", line[i:j]))
            i = j
            continue

        if line[i] == "@" and i + 1 < n and line[i + 1].isalpha():
            j = i + 1
            while j < n and (line[j].isalnum() or line[j] in "_."):
                j += 1
            segments.append(("annotation", line[i:j]))
            i = j
            continue

        if line[i].isdigit() or (line[i] == "." and i + 1 < n and line[i + 1].isdigit()):
            j = i
            while j < n and (line[j].isdigit() or line[j] in "._"):
                j += 1
            segments.append(("number", line[i:j]))
            i = j
            continue

        if line[i].isalpha() or line[i] == "_":
            j = i
            while j < n and (line[j].isalnum() or line[j] == "_"):
                j += 1
            word = line[i:j]
            nxt = j
            while nxt < n and line[nxt] in " \t":
                nxt += 1
            if word in JAVA_KEYWORDS:
                cls = "keyword"
            elif nxt < n and line[nxt] == "(":
                cls = "method"
            elif word[0].isupper():
                cls = "type"
            else:
                cls = "text"
            segments.append((cls, word))
            i = j
            continue

        segments.append(("punctuation", line[i]))
        i += 1

    return segments


def line_width_chars(line: str) -> float:
    return max(len(line), 1) * CHAR_W


def build_carbon_svg(tweet: dict) -> str:
    tid = tweet["id"]
    module = tweet.get("module", "")
    tags = tweet.get("tags", "")
    source = tweet_source(tweet)
    lines = source.split("\n")
    if len(lines) > MAX_CODE_LINES:
        lines = lines[: MAX_CODE_LINES - 1] + [lines[MAX_CODE_LINES - 1] + " …"]

    num_lines = len(lines)
    max_chars = max((len(l) for l in lines), default=20)
    code_w = max(WINDOW_W - LINE_NUM_W - INNER_PAD * 2, int(max_chars * CHAR_W) + 24)
    window_w = LINE_NUM_W + code_w + INNER_PAD * 2
    window_h = TITLE_H + INNER_PAD * 2 + num_lines * LINE_HEIGHT
    total_w = window_w + OUTER_PAD * 2
    total_h = window_h + OUTER_PAD * 2 + 36  # watermark

    wx = OUTER_PAD
    wy = OUTER_PAD
    editor_y = wy + TITLE_H + INNER_PAD

    t = THEME
    style = f"""
      .kw {{ fill: {t['keyword']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .ann {{ fill: {t['annotation']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .str {{ fill: {t['string']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .typ {{ fill: {t['type']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .met {{ fill: {t['method']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .com {{ fill: {t['comment']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .num {{ fill: {t['number']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .pun {{ fill: {t['punctuation']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .txt {{ fill: {t['text']}; font-family: {FONT}; font-size: {FONT_SIZE}px; }}
      .ln {{ fill: {t['line_number']}; font-family: {FONT}; font-size: {FONT_SIZE}px; text-anchor: end; }}
      .wm {{ fill: rgba(255,255,255,0.55); font-family: system-ui, sans-serif; font-size: 11px; }}
      .cap {{ fill: rgba(255,255,255,0.75); font-family: system-ui, sans-serif; font-size: 12px; }}
    """

    cls_map = {
        "keyword": "kw",
        "annotation": "ann",
        "string": "str",
        "type": "typ",
        "method": "met",
        "comment": "com",
        "number": "num",
        "punctuation": "pun",
        "text": "txt",
    }

    code_parts: list[str] = []
    for idx, line in enumerate(lines):
        y = editor_y + (idx + 1) * LINE_HEIGHT - 6
        ln_x = wx + INNER_PAD + LINE_NUM_W - 12
        code_x = wx + INNER_PAD + LINE_NUM_W
        code_parts.append(
            f'  <text x="{ln_x}" y="{y}" class="ln">{idx + 1}</text>'
        )
        x = code_x
        for kind, text in tokenize_java_line(line):
            css = cls_map.get(kind, "txt")
            esc = xml_escape(text)
            w = len(text) * CHAR_W
            code_parts.append(
                f'  <text x="{x:.1f}" y="{y}" class="{css}">{esc}</text>'
            )
            x += w

    code_xml = "\n".join(code_parts)
    caption = xml_escape(f"Tweet #{tid} · {module}  {tags}")

    return f'''<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" width="{total_w}" height="{total_h}" viewBox="0 0 {total_w} {total_h}">
  <defs>
    <linearGradient id="bg" x1="0%" y1="0%" x2="100%" y2="100%">
      <stop offset="0%" style="stop-color:{t['bg_gradient_start']}"/>
      <stop offset="100%" style="stop-color:{t['bg_gradient_end']}"/>
    </linearGradient>
    <filter id="shadow" x="-20%" y="-20%" width="140%" height="140%">
      <feDropShadow dx="0" dy="8" stdDeviation="12" flood-color="#000" flood-opacity="0.35"/>
    </filter>
    <style>{style}</style>
  </defs>
  <rect width="100%" height="100%" fill="url(#bg)"/>
  <g filter="url(#shadow)">
    <rect x="{wx}" y="{wy}" width="{window_w}" height="{window_h}" rx="10" fill="{t['window_bg']}"/>
    <rect x="{wx}" y="{wy}" width="{window_w}" height="{TITLE_H}" rx="10" fill="{t['window_bg']}"/>
    <rect x="{wx}" y="{wy + TITLE_H - 10}" width="{window_w}" height="10" fill="{t['window_bg']}"/>
    <circle cx="{wx + 20}" cy="{wy + 18}" r="6" fill="{t['dot_red']}"/>
    <circle cx="{wx + 40}" cy="{wy + 18}" r="6" fill="{t['dot_yellow']}"/>
    <circle cx="{wx + 60}" cy="{wy + 18}" r="6" fill="{t['dot_green']}"/>
    <rect x="{wx}" y="{wy + TITLE_H}" width="{window_w}" height="{window_h - TITLE_H}" rx="0" fill="{t['editor_bg']}"/>
    <rect x="{wx}" y="{wy + window_h - 10}" width="{window_w}" height="10" rx="10" fill="{t['editor_bg']}"/>
  </g>
{code_xml}
  <text x="{OUTER_PAD}" y="{total_h - 14}" class="cap">{caption}</text>
  <text x="{total_w - OUTER_PAD}" y="{total_h - 14}" class="wm" text-anchor="end">codingstrain · carbon style</text>
</svg>
'''


# --- Legacy drawio (optional, kept for diagrams.net users) ---

CARD_W = 560


def build_drawio(tweet: dict) -> str:
    tid = tweet["id"]
    source = tweet_source(tweet)
    module = tweet.get("module", "")
    tags = tweet.get("tags", "")
    code_lines = source.split("\n")
    height = max(280, 120 + len(code_lines) * 18 + 80)

    code_display = xml_escape(source.replace("\n", "&#xa;"))
    header = xml_escape(f"Tweet #{tid} · codingstrain")
    footer = xml_escape(f"Module: {module}  {tags}")

    return f'''<mxfile host="app.diagrams.net" agent="codingstrain-generator" version="22.1.0" type="device">
  <diagram id="{uuid.uuid4()}" name="Tweet {tid}">
    <mxGraphModel pageWidth="600" pageHeight="{height}">
      <root>
        <mxCell id="0"/><mxCell id="1" parent="0"/>
        <mxCell id="code" value="{code_display}" style="rounded=1;fillColor=#011627;fontColor=#d6deeb;fontFamily=Courier New;fontSize=11;whiteSpace=wrap;" vertex="1" parent="1">
          <mxGeometry x="20" y="60" width="520" height="{height - 100}" as="geometry"/>
        </mxCell>
        <mxCell id="hdr" value="{header}" style="text;fontSize=14;fontStyle=1;fontColor=#333;" vertex="1" parent="1">
          <mxGeometry x="20" y="20" width="400" height="30" as="geometry"/>
        </mxCell>
        <mxCell id="ftr" value="{footer}" style="text;fontSize=10;fontColor=#666;" vertex="1" parent="1">
          <mxGeometry x="20" y="{height - 30}" width="520" height="24" as="geometry"/>
        </mxCell>
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
'''


def build_combined_drawio(tweets: list[dict]) -> str:
    parts = ['<mxfile host="app.diagrams.net" agent="codingstrain-generator" version="22.1.0">']
    for tweet in tweets:
        single = build_drawio(tweet)
        m = re.search(r"<diagram[^>]*>.*?</diagram>", single, re.DOTALL)
        if m:
            parts.append(m.group(0))
    parts.append("</mxfile>")
    return "\n  ".join(parts)


def build_index(tweets: list[dict]) -> str:
    rows = []
    for t in tweets:
        tid = t["id"]
        rows.append(
            f"""    <article>
      <h2>Tweet #{tid}</h2>
      <img src="carbon/tweet-{tid:02d}.svg" alt="Tweet {tid} Carbon" width="720"/>
      <p class="links">
        <a href="carbon/tweet-{tid:02d}.svg" download>Download SVG</a> ·
        <a href="sources/tweet-{tid:02d}.java" download>Source .java</a> ·
        <a href="https://carbon.now.sh/" target="_blank" rel="noopener">Edit on Carbon</a>
      </p>
    </article>"""
        )
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <title>codingstrain X Posts — Carbon style</title>
  <style>
    body {{ font-family: system-ui, sans-serif; max-width: 760px; margin: 2rem auto; padding: 0 1rem; background: #0d1117; color: #e6edf3; }}
    article {{ background: #161b22; border-radius: 12px; padding: 1rem; margin-bottom: 2rem; border: 1px solid #30363d; }}
    h2 {{ margin: 0 0 .75rem; font-size: 1rem; color: #8b949e; }}
    .links a {{ color: #58a6ff; }}
    .banner {{ background: linear-gradient(135deg, #667eea, #764ba2); color: #fff; padding: 1.25rem; border-radius: 12px; margin-bottom: 2rem; }}
    .banner a {{ color: #fff; }}
  </style>
</head>
<body>
  <div class="banner">
    <h1>Spring Boot &amp; Java — Carbon images</h1>
    <p>{len(tweets)} code cards in <a href="https://carbon.now.sh/">Carbon.now.sh</a> style.
       Paste <code>sources/tweet-XX.java</code> into Carbon to tweak theme/export PNG.</p>
  </div>
{chr(10).join(rows)}
</body>
</html>
"""


def main() -> None:
    tweets = json.loads(TWEETS_FILE.read_text(encoding="utf-8"))
    DRAWIO_DIR.mkdir(parents=True, exist_ok=True)
    CARBON_DIR.mkdir(parents=True, exist_ok=True)
    SOURCES_DIR.mkdir(parents=True, exist_ok=True)

    for tweet in tweets:
        tid = tweet["id"]
        stem = f"tweet-{tid:02d}"
        (CARBON_DIR / f"{stem}.svg").write_text(build_carbon_svg(tweet), encoding="utf-8")
        (SOURCES_DIR / f"{stem}.java").write_text(tweet_source(tweet) + "\n", encoding="utf-8")
        (DRAWIO_DIR / f"{stem}.drawio").write_text(build_drawio(tweet), encoding="utf-8")

    (DRAWIO_DIR / "all-tweets.drawio").write_text(build_combined_drawio(tweets), encoding="utf-8")
    INDEX_HTML.write_text(build_index(tweets), encoding="utf-8")

    readme = f"""# Generated X Post Assets (Carbon style)

{len(tweets)} tweet images in [Carbon.now.sh](https://carbon.now.sh/) style.

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
"""
    (ROOT / "README.md").write_text(readme, encoding="utf-8")
    print(f"Generated {len(tweets)} Carbon SVGs → {CARBON_DIR}")
    print(f"  sources/ → paste into carbon.now.sh")
    print(f"  preview  → {INDEX_HTML}")


if __name__ == "__main__":
    main()
