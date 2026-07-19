#!/usr/bin/env python3
"""Build CodePen Prefill embed markup for Java/Spring code snippets."""

from __future__ import annotations

import html
import json
import re
from typing import Any

CODEPEN_SCRIPT = "https://public.codepenassets.com/embed/index.js"

# Night Owl + Carbon window (matches generate_assets.py THEME)
CARBON_CSS = """html, body {
  margin: 0;
  width: 100%;
  height: 100%;
  min-height: 100vh;
  box-sizing: border-box;
}
body {
  display: flex;
  flex-direction: column;
  align-items: stretch;
  justify-content: stretch;
  padding: 0.5rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.carbon {
  flex: 1;
  display: flex;
  flex-direction: column;
  width: 100%;
  min-height: calc(100vh - 1rem);
  max-width: none;
  border-radius: 10px;
  box-shadow: 0 12px 48px rgba(0, 0, 0, 0.5);
  overflow: hidden;
}
.carbon-titlebar {
  display: flex;
  align-items: center;
  gap: 10px;
  height: 36px;
  padding: 0 16px;
  background: #252836;
  flex-shrink: 0;
}
.carbon-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  flex-shrink: 0;
}
.carbon-dot.red { background: #ff5f56; }
.carbon-dot.yellow { background: #ffbd2e; }
.carbon-dot.green { background: #27c93f; }
.carbon-module {
  margin-left: auto;
  font: 13px/1 system-ui, sans-serif;
  color: #8b949e;
  letter-spacing: 0.02em;
}
.carbon-editor {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-height: 0;
  background: #011627;
  padding: 1.5rem 2rem 1.75rem;
  overflow: auto;
}
.carbon-editor pre {
  margin: 0;
  flex: 1;
  font-family: "Fira Code", "JetBrains Mono", Consolas, monospace;
  font-size: 25px;
  line-height: 42px;
  white-space: pre;
  tab-size: 4;
}
.carbon-editor .kw { color: #c792ea; }
.carbon-editor .ann { color: #ffb86c; }
.carbon-editor .str { color: #c3e88d; }
.carbon-editor .typ { color: #ffcb6b; }
.carbon-editor .met { color: #82aaff; }
.carbon-editor .com { color: #5c6370; }
.carbon-editor .num { color: #f78c6c; }
.carbon-editor .pun { color: #89ddff; }
.carbon-editor .txt { color: #d6deeb; }
.carbon-editor.cols {
  flex-direction: row;
  gap: 1.75rem;
  align-items: stretch;
}
.carbon-editor.cols .col {
  flex: 1 1 0;
  min-width: 0;
  display: flex;
  flex-direction: column;
}
.carbon-editor.cols .col pre {
  flex: 1;
  font-size: 25px;
  line-height: 42px;
}
.carbon-editor.cols .col-divider {
  align-self: stretch;
  position: relative;
  width: 1px;
  background: #1d3b53;
  flex: 0 0 auto;
}
.carbon-editor.cols .col-divider::after {
  content: "\\2192";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background: #011627;
  border: 1px solid #1d3b53;
  color: #82aaff;
  font-size: 16px;
}
"""

JAVA_KEYWORDS = frozenset({
    "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char",
    "class", "const", "continue", "default", "do", "double", "else", "enum",
    "extends", "final", "finally", "float", "for", "goto", "if", "implements",
    "import", "instanceof", "int", "interface", "long", "native", "new",
    "package", "permits", "private", "protected", "public", "return", "sealed",
    "short", "static", "strictfp", "super", "switch", "synchronized", "this",
    "throw", "throws", "transient", "try", "void", "volatile", "while", "var",
    "record", "true", "false", "null",
})


def tokenize_java_line(line: str) -> list[tuple[str, str]]:
    segments: list[tuple[str, str]] = []
    i, n = 0, len(line)

    while i < n:
        if line[i] in " \t":
            j = i + 1
            while j < n and line[j] in " \t":
                j += 1
            segments.append(("text", line[i:j]))
            i = j
            continue

        if line.startswith("//", i):
            segments.append(("comment", line[i:]))
            break

        if line.startswith("/*", i):
            j = i + 2
            while j < n:
                if line.startswith("*/", j):
                    j += 2
                    break
                j += 1
            segments.append(("comment", line[i:j]))
            i = j
            continue

        if line[i] in '"\'':
            q, j = line[i], i + 1
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

        if line[i].isdigit():
            j = i
            while j < n and (line[j].isdigit() or line[j] in "._"):
                j += 1
            segments.append(("number", line[i:j]))
            i = j
            continue

        if line[i].isalpha() or line[i] == "_":
            j = i
            while j < n and (line[j].isalnum() or line[j] in "_"):
                j += 1
            word = line[i:j]
            nxt = j
            while nxt < n and line[nxt] in " \t":
                nxt += 1
            if word in JAVA_KEYWORDS:
                kind = "keyword"
            elif nxt < n and line[nxt] == "(":
                kind = "method"
            elif word[0].isupper():
                kind = "type"
            else:
                kind = "text"
            segments.append((kind, word))
            i = j
            continue

        segments.append(("punctuation", line[i]))
        i += 1

    return segments


def _span(kind: str, text: str) -> str:
  css = {
    "keyword": "kw",
    "annotation": "ann",
    "string": "str",
    "type": "typ",
    "method": "met",
    "comment": "com",
    "number": "num",
    "punctuation": "pun",
    "text": "txt",
  }.get(kind, "txt")
  return f'<span class="{css}">{html.escape(text)}</span>'


def highlight_java_html(code: str, module: str) -> str:
    lines = code.split("\n")
    body = "\n".join(
        "".join(_span(kind, text) for kind, text in tokenize_java_line(line))
        for line in lines
    )
    mod = html.escape(module)
    return (
        '<div class="carbon">'
        '<div class="carbon-titlebar">'
        '<span class="carbon-dot red"></span>'
        '<span class="carbon-dot yellow"></span>'
        '<span class="carbon-dot green"></span>'
        f'<span class="carbon-module">{mod}</span>'
        "</div>"
        '<div class="carbon-editor"><pre><code>'
        f"{body}</code></pre></div></div>"
    )


def _render_block(lines: list[str]) -> str:
    return "\n".join(
        "".join(_span(kind, text) for kind, text in tokenize_java_line(line))
        for line in lines
    )


_TYPE_DECL = re.compile(
    r"^(?:(?:public|private|protected|final|abstract|sealed|non-sealed|static|strictfp)\s+)*"
    r"(?:class|interface|enum|record)\b"
)


def _split_columns(lines: list[str], prefer_decl: int = 1) -> tuple[list[str], list[str]]:
    """Split source lines into two columns.

    Prefer a top-level type boundary so the first class lands entirely in the
    left column; fall back to a balanced blank-line split otherwise.

    ``prefer_decl`` selects which type declaration (by ordinal) starts the right
    column, so callers can balance the columns when there are 3+ top-level types.
    """
    n = len(lines)
    decls = [i for i, ln in enumerate(lines) if _TYPE_DECL.match(ln)]

    if len(decls) > prefer_decl:
        split = decls[prefer_decl]
        # Pull leading annotations / comments of the second type into the right column.
        j = split - 1
        while j >= 0:
            s = lines[j].strip()
            if s.startswith(("@", "//", "*", "/*")) or s.endswith("*/"):
                j -= 1
            else:
                break
        split = j + 1
    else:
        mid = n // 2
        split = mid
        for off in range(n):
            for cand in (mid - off, mid + off):
                if 0 < cand < n and lines[cand].strip() == "":
                    split = cand
                    break
            else:
                continue
            break

    left = lines[:split]
    right = lines[split:]
    while right and right[0].strip() == "":
        right.pop(0)
    while left and left[-1].strip() == "":
        left.pop()
    return left, right


def highlight_java_html_columns(code: str, module: str, prefer_decl: int = 1) -> str:
    """Carbon window with the code split across two adjacent columns."""
    left, right = _split_columns(code.split("\n"), prefer_decl)
    mod = html.escape(module)
    return (
        '<div class="carbon">'
        '<div class="carbon-titlebar">'
        '<span class="carbon-dot red"></span>'
        '<span class="carbon-dot yellow"></span>'
        '<span class="carbon-dot green"></span>'
        f'<span class="carbon-module">{mod}</span>'
        "</div>"
        '<div class="carbon-editor cols">'
        '<div class="col">'
        f'<pre><code>{_render_block(left)}</code></pre>'
        "</div>"
        '<span class="col-divider"></span>'
        '<div class="col">'
        f'<pre><code>{_render_block(right)}</code></pre>'
        "</div>"
        "</div></div>"
    )


def _pre_block(text: str) -> str:
  return text.replace("&", "&amp;").replace("<", "&lt;")


def _prefill_attr(meta: dict[str, Any]) -> str:
  raw = json.dumps(meta, ensure_ascii=False)
  return raw.replace("&", "&amp;").replace("'", "&#39;")


def _embed_height(code: str, *, carbon: bool = False, large: bool = False,
                  line_count: int | None = None) -> int:
    lines = line_count if line_count is not None else code.count("\n") + 1
    if carbon:
        floor = 720 if large else 640
        cap = 1200 if large else 1000
        return min(cap, max(floor, 240 + lines * 44))
    return min(720, max(340, 140 + lines * 22))


def _use_carbon_style(tweet: dict) -> bool:
  style = tweet.get("codepen_style", "carbon")
  return style != "plain"


def build_codepen_embed(tweet: dict, code: str) -> str:
    """HTML fragment: CodePen Prefill embed for a Java snippet."""
    tid = tweet["id"]
    module = tweet.get("module", "codingstrain")
    title = f"Tweet {tid} — {module}"
    carbon = _use_carbon_style(tweet)
    large = bool(tweet.get("codepen_large", carbon))
    columns = carbon and int(tweet.get("codepen_columns", 1)) >= 2
    split_decl = int(tweet.get("codepen_split_decl", 1))

    if columns:
        left, right = _split_columns(code.split("\n"), split_decl)
        col_lines = max(len(left), len(right))
        height = _embed_height(code, carbon=carbon, large=large, line_count=col_lines)
    else:
        height = _embed_height(code, carbon=carbon, large=large)

    meta = {
        "title": title,
        "description": (
            f"Spring Boot snippet from codingstrain ({module})"
            if str(tweet.get("category", "")).startswith("spring-boot")
            else f"Java snippet from codingstrain ({module})"
        ),
        "tags": (
            ["java", "springboot", "codingstrain"]
            if str(tweet.get("category", "")).startswith("spring-boot")
            else ["java", "codingstrain"]
        ),
    }

    wrap_class = "codepen-wrap codepen-wrap--large" if large else "codepen-wrap"

    if carbon:
        html_block = (
            highlight_java_html_columns(code, module, split_decl)
            if columns
            else highlight_java_html(code, module)
        )
        css = CARBON_CSS
    else:
        css = _LEGACY_CSS
        escaped = html.escape(code)
        html_block = (
            f'<p class="module-tag">{html.escape(module)}</p>\n'
            f'<pre class="code-snippet"><code>{escaped}</code></pre>'
        )

    return f"""      <div class="{wrap_class}">
        <div class="codepen"
             data-prefill='{_prefill_attr(meta)}'
             data-height="{height}"
             data-theme-id="dark"
             data-default-tab="html,result"
             data-editable="true">
          <pre data-lang="css">{_pre_block(css)}</pre>
          <pre data-lang="html">{html.escape(html_block)}</pre>
        </div>
        <p class="codepen-links">
          <a href="codepen/tweet-{tid:02d}.html" target="_blank" rel="noopener">Open pen preview</a>
          · <a href="https://codepen.io/pen/" target="_blank" rel="noopener">New pen on CodePen</a>
        </p>
      </div>"""


_LEGACY_CSS = """body {
  margin: 0;
  padding: 1rem 1.25rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  min-height: 100vh;
  box-sizing: border-box;
}
.code-snippet {
  margin: 0;
  padding: 1.25rem 1.5rem;
  background: #011627;
  border-radius: 8px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.35);
  overflow: auto;
}
.code-snippet code {
  font-family: "Fira Code", "JetBrains Mono", Consolas, monospace;
  font-size: 25px;
  line-height: 1.68;
  color: #d6deeb;
  white-space: pre;
}
.module-tag {
  font-family: system-ui, sans-serif;
  font-size: 11px;
  color: #94a3b8;
  margin: 0 0 0.75rem;
  letter-spacing: 0.02em;
}
"""


def build_standalone_pen_page(tweet: dict, code: str) -> str:
    """Full HTML page with one Prefill embed (works offline except CodePen script)."""
    tid = tweet["id"]
    embed = build_codepen_embed(tweet, code)
    carbon = _use_carbon_style(tweet)
    large = bool(tweet.get("codepen_large", carbon))
    if carbon and large:
        page_css = """
    html, body { margin: 0; height: 100%; }
    body { font-family: system-ui, sans-serif; background: #f7f9f9; display: flex; flex-direction: column; }
    h1 { font-size: 1rem; color: #536471; margin: 0.5rem 1rem; flex-shrink: 0; }
    .codepen-wrap { flex: 1; display: flex; flex-direction: column; max-width: none; width: 100%; min-height: 0; }
    .codepen-wrap .codepen { flex: 1; min-height: calc(100vh - 3rem); }
    .codepen-links { margin: 0.35rem 1rem 0.5rem; flex-shrink: 0; }
"""
    else:
        page_css = """
    body { font-family: system-ui, sans-serif; margin: 2rem; background: #f7f9f9; }
    h1 { font-size: 1.1rem; color: #536471; }
    .codepen-wrap { max-width: 760px; }
"""
    return f"""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>Tweet {tid} — CodePen</title>
  <style>{page_css}
  </style>
</head>
<body>
  <h1>Tweet #{tid} — <a href="https://codepen.io/">CodePen</a> prefill</h1>
{embed}
  <script async src="{CODEPEN_SCRIPT}"></script>
</body>
</html>
"""
